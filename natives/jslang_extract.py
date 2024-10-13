import argparse
import os
from clang.cindex import *


def get_parser():
    parser = argparse.ArgumentParser(description="Extract Java defs from slang.h")
    parser.add_argument("slang_h", type=str, help="Path to slang.h")
    parser.add_argument("-o", type=str, help="Path to output directory")
    return parser


enums = {}
scoped_enums = {}
pod_structs = {}

enums["_Anonymous"] = []
scoped_enums_fixes = ["ParameterCategory", "ScalarType"]


def traverse_ast(cursor):
    for c in cursor.get_children():
        traverse_ast(c)

    if cursor.kind == CursorKind.ENUM_DECL:
        if cursor.is_scoped_enum() or cursor.spelling in scoped_enums_fixes:
            scoped_enums[cursor.spelling] = []
            for c in cursor.get_children():
                scoped_enums[cursor.spelling].append((c.spelling, c.enum_value))
        else:
            enums[cursor.spelling] = []
            for c in cursor.get_children():
                if c.spelling.startswith("_"):
                    continue
                enums[cursor.spelling].append((c.spelling, c.enum_value))

    # if cursor.kind == CursorKind.STRUCT_DECL:
    #     if cursor.is_anonymous():
    #         return
    #     if cursor.is_definition() and cursor.type.is_pod():
    #         if cursor.spelling.startswith("_"):
    #             return
    #         pod_structs[cursor.spelling] = []
    #         for c in cursor.get_children():
    #             pod_structs[cursor.spelling].append({c.type.spelling, c.spelling})


parser = get_parser()
args = parser.parse_args()

slang_h = TranslationUnit.from_source(args.slang_h, args=["-x", "c++", "-std=c++17"])
cursor = slang_h.cursor

traverse_ast(cursor)

enums_java = """
package moyongxin.jslang.generated;

public class Enums {
"""

for _, enum_tuples in enums.items():
    for enum in enum_tuples:
        enums_java += f"\tpublic static final int {enum[0]} = {enum[1]};\n"

for scoped_enum_type, scoped_enum_tuples in scoped_enums.items():
    enums_java += f"\tpublic class {scoped_enum_type} {{\n"
    for scoped_enum in scoped_enum_tuples:
        enums_java += (
            f"\t\tpublic static final int {scoped_enum[0]} = {scoped_enum[1]};\n"
        )
    enums_java += "\t}\n"

enums_java += "}\n"

os.makedirs(args.o, exist_ok=True)

with open(f"{args.o}/Enums.java", "w") as f:
    f.write(enums_java)
