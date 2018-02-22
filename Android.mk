LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := app
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	D:\android_sample\myVideo\app\src\main\jniLibs\arm64-v8a\libspeex.so \
	D:\android_sample\myVideo\app\src\main\jniLibs\armeabi\libspeex.so \
	D:\android_sample\myVideo\app\src\main\jniLibs\armeabi-v7a\libspeex.so \
	D:\android_sample\myVideo\app\src\main\jniLibs\mips\libspeex.so \
	D:\android_sample\myVideo\app\src\main\jniLibs\mips64\libspeex.so \
	D:\android_sample\myVideo\app\src\main\jniLibs\x86\libspeex.so \
	D:\android_sample\myVideo\app\src\main\jniLibs\x86_64\libspeex.so \

LOCAL_C_INCLUDES += D:\android_sample\myVideo\app\src\main\jni
LOCAL_C_INCLUDES += D:\android_sample\myVideo\app\src\debug\jni
LOCAL_C_INCLUDES += D:\android_sample\myVideo\app\src\main\jniLibs

include $(BUILD_SHARED_LIBRARY)
