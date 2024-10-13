#include "jni/moyongxin_jslang_CompilerOptionEntry.h"
#include "jni/moyongxin_jslang_CompilerOptionValue.h"
#include "jni/moyongxin_jslang_GlobalSession.h"
#include "jni/moyongxin_jslang_TargetDesc.h"

#include "slang.h"

JNIEXPORT jlong JNICALL
Java_moyongxin_jslang_GlobalSession_createGlobalSession(JNIEnv *, jclass) {
  slang::IGlobalSession *ptr;
  slang::createGlobalSession(&ptr);
  return (jlong)ptr;
}

JNIEXPORT jint JNICALL Java_moyongxin_jslang_GlobalSession_findProfile(
    JNIEnv *, jclass, jlong ptr, jlong name) {
  slang::IGlobalSession *session = (slang::IGlobalSession *)ptr;
  return session->findProfile((const char *)name);
}

JNIEXPORT void JNICALL Java_moyongxin_jslang_TargetDesc_ctor(
    JNIEnv *, jclass, jlong ptr, jint format, jint profile, jint flags,
    jint floatingPointMode, jint lineDirectiveMode,
    jboolean forceGLSLScalarforceGLSLScalarBufferLayout,
    jlong compilerOptionEntries, jint compilerOptionEntryCount) {
  slang::TargetDesc *desc = (slang::TargetDesc *)ptr;
  desc->format = static_cast<SlangCompileTarget>(format);
  desc->profile = static_cast<SlangProfileID>(profile);
  desc->flags = static_cast<SlangTargetFlags>(flags);
  desc->floatingPointMode =
      static_cast<SlangFloatingPointMode>(floatingPointMode);
  desc->lineDirectiveMode =
      static_cast<SlangLineDirectiveMode>(lineDirectiveMode);
  desc->forceGLSLScalarBufferLayout =
      forceGLSLScalarforceGLSLScalarBufferLayout;
  desc->compilerOptionEntries =
      (slang::CompilerOptionEntry *)compilerOptionEntries;
  desc->compilerOptionEntryCount = compilerOptionEntryCount;
}

JNIEXPORT void JNICALL Java_moyongxin_jslang_CompilerOptionEntry_ctor(
    JNIEnv *, jclass, jlong ptr, jint name, jlong val) {
  slang::CompilerOptionEntry *entry = (slang::CompilerOptionEntry *)ptr;
  slang::CompilerOptionValue *value = (slang::CompilerOptionValue *)val;
  entry->name = static_cast<slang::CompilerOptionName>(name);
  entry->value = *value;
}

JNIEXPORT void JNICALL Java_moyongxin_jslang_CompilerOptionValue_ctor(
    JNIEnv *, jclass, jlong ptr, jint kind, jint intValue0, jint intValue1,
    jlong stringValue0, jlong stringValue1) {
  slang::CompilerOptionValue *value = (slang::CompilerOptionValue *)ptr;
  value->kind = static_cast<slang::CompilerOptionValueKind>(kind);
  value->intValue0 = intValue0;
  value->intValue1 = intValue1;
  value->stringValue0 = (const char *)stringValue0;
  value->stringValue1 = (const char *)stringValue1;
}
