package moyongxin.jslang;


import moyongxin.jslang.system.CString;
import moyongxin.jslang.system.LibraryLoader;
import moyongxin.jslang.system.PointerArray;

import static moyongxin.jslang.generated.Enums.*;

import java.io.IOException;

public class JSlang {
    static {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            LibraryLoader.load("slang.dll");
            LibraryLoader.load("libjslang.dll");
        }
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            LibraryLoader.load("libslang.so");
            LibraryLoader.load("libjslang.so");
        }
    }

    public static void main(String[] args) {
        String test = "Test CString";
        CString string = new CString(test);
        System.out.println(string);
        GlobalSession globalSession = new GlobalSession();

        PointerArray searchPaths = new PointerArray(1);
        CString searchPath = new CString("shaders/");
        searchPaths.setElement(0, searchPath.getPtr());

        TargetDesc targetDesc = new TargetDesc(SLANG_SPIRV, globalSession.findProfile("sm_6_5"), SLANG_TARGET_FLAG_GENERATE_SPIRV_DIRECTLY,
                SLANG_FLOATING_POINT_MODE_DEFAULT, SLANG_LINE_DIRECTIVE_MODE_DEFAULT, false, 0, 0);

        // See https://shader-slang.com/slang/cpu-target.html#slang-llvm
        System.out.println(globalSession.checkPassThroughSupport(SLANG_PASS_THROUGH_LLVM));

        // slang-llvm provides a way to run Slang code directly on the CPU using LLVM JIT.
        // It seems that slang-llvm is default integrated in Slang binary distribution.
        // See https://shader-slang.com/slang/cpu-target.html#host-callable
        // See https://github.com/shader-slang/slang-llvm

        // And we can also expose OOP methods to Slang code.
        // See https://shader-slang.com/slang/cpu-target.html#com-interface
    }
}
