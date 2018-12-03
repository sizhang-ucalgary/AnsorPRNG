# AnsorPRNG

Introduction
------------
This sample uses the [Android Studio CMake plugin](http://tools.android.com/tech-docs/external-c-builds) with external library support. It demos how to:

* include a pre-built shared library (Crypto++ 7.0) in your app

Description
-----------
The main goal of the sample is to demo how to use 3rd party libs, it is not to demonstrate lib package generation. Toward that goal, the pre-built libs are included in the `distribution` folder. `cryptopp-android` folder contains mk files for porting Crypto++.

The sample includes 2 modules:
*    app -- imports a shared library (libc++_shared.so) and a shared library (libcryptopp_shared.so) from the `distribution` folder
*    gen-libs -- contains the source code and CMake build script for the cryptopp library. 

The resulting binaries are copied into the `distribution` folder. We pack shared libraries into APK.

`app/src/main/cpp/hello-libs.cpp` contains the C++ code for jni.
`app/src/main/java/com/example/hellolibs/CryptoRandom.java` contains the Java code for jni

Pre-requisites
--------------
- Android Studio 3.0.0 with [NDK](https://developer.android.com/ndk/) bundle.
- Crypto++ 7.0
