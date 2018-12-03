## Application.mk - Android build file for Crypto++. 
##                  Based on Application.mk by Jeffrey Walton,
##                  https://github.com/noloader/cryptopp-android

APP_ABI := all
APP_PLATFORM := android-24
APP_STL := c++_shared

CRYPTOPP_ROOT := $(call my-dir)
NDK_PROJECT_PATH := $(CRYPTOPP_ROOT)
APP_BUILD_SCRIPT := $(CRYPTOPP_ROOT)/Android.mk

GREP ?= grep
NDK_r16_OR_LATER := $(shell $(GREP) -i -c -E "Pkg.Revision = (1[6-9]|[2-9][0-9]\.)" "$$ANDROID_NDK_ROOT/source.properties")
ifneq ($(NDK_r16_OR_LATER),0)
  ifneq ($(APP_STL),c++_shared)
    $(info Crypto++: NDK r16 or later. Use c++_shared instead of $(APP_STL))
  endif
endif

ifeq ($(NDK_LOG),1)
    $(info Crypto++: ANDROID_NDK_ROOT is $(ANDROID_NDK_ROOT))
    $(info Crypto++: NDK_PROJECT_PATH is $(NDK_PROJECT_PATH))
    $(info Crypto++: APP_BUILD_SCRIPT is $(APP_BUILD_SCRIPT))
endif