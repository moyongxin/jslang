#include "jni/moyongxin_jslang_system_CString.h"
#include "jni/moyongxin_jslang_system_InteropUtils.h"
#include "jni/moyongxin_jslang_system_PointerArray.h"

#include <stdlib.h>
#include <string.h>

JNIEXPORT jlong JNICALL Java_moyongxin_jslang_system_InteropUtils_calloc(
    JNIEnv *, jclass, jlong cnt, jlong size) {
  return (jlong)calloc(cnt, size);
}

JNIEXPORT jlong JNICALL
Java_moyongxin_jslang_system_InteropUtils_malloc(JNIEnv *, jclass, jlong size) {
  return (jlong)malloc(size);
}

JNIEXPORT void JNICALL
Java_moyongxin_jslang_system_InteropUtils_free(JNIEnv *, jclass, jlong ptr) {
  free((void *)ptr);
}

JNIEXPORT jlong JNICALL
Java_moyongxin_jslang_system_CString_from(JNIEnv *env, jclass, jstring jstr) {
  const char *cstr = env->GetStringUTFChars(jstr, 0);
  void *ptr = malloc(strlen(cstr) + 1);
  memcpy(ptr, cstr, strlen(cstr) + 1);
  env->ReleaseStringUTFChars(jstr, cstr);
  return (jlong)ptr;
}

JNIEXPORT jstring JNICALL
Java_moyongxin_jslang_system_CString_toString(JNIEnv *env, jclass, jlong ptr) {
  const char *cstr = (const char *)ptr;
  return env->NewStringUTF(cstr);
}

JNIEXPORT jlong JNICALL Java_moyongxin_jslang_system_PointerArray_getElement(
    JNIEnv *, jclass, jlong ptr, jlong idx) {
  jlong *arr = (jlong *)ptr;
  return arr[idx];
}

JNIEXPORT void JNICALL Java_moyongxin_jslang_system_PointerArray_setElement(
    JNIEnv *, jclass, jlong ptr, jlong idx, jlong val) {
  jlong *arr = (jlong *)ptr;
  arr[idx] = val;
}
