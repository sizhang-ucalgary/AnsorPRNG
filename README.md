# AnsorPRNG

Introduction
------------
This sample uses the [Android Studio CMake plugin](http://tools.android.com/tech-docs/external-c-builds) with external library support. It demos how to:

* include a pre-built shared library (Crypto++ 7.0) in your app

Description
-----------
The main goal of the sample is to demo how to use 3rd party libs, it is not to demonstrate lib package generation. Toward that goal, the pre-built libs are included in the `distribution` folder.
The sample includes 2 modules:
*    app -- imports a shared library (libc++_shared.so) and a shared library (libcryptopp_shared.so) from the `distribution` folder
*    gen-libs -- contains the source code and CMake build script for the gmath and gperf example libraries. The resulting binaries are copied into the `distribution` folder. By default, gen-libs module is disabled in setting.gradle and app/build.gradle, so it won't show up in Android Studio IDE. If re-generating lib is desirable, follow comments inside settings.gradle and app/build.gradle to enable this module, generate libs, then disable it again to avoid unnecessary confusion.
For shared libraries, notify gradle to pack them into APK.

`cryptopp-android' folder contains mk files for porting Crypto++.

Pre-requisites
--------------
- Android Studio 3.0.0 with [NDK](https://developer.android.com/ndk/) bundle.
- Crypto++ 7.0
