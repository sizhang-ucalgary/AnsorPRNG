# AnsorPRNG
=========
Introduction
------------
This sample uses the [Android Studio CMake plugin](http://tools.android.com/tech-docs/external-c-builds) with external library support. It demos how to:

* include a pre-built shared library (Crypto++ 7.0) in your app

Description
-----------
The sample includes 2 modules:
*    app -- imports a shared library (libc++_shared.so) and a shared library (libcryptopp_shared.a) from the `distribution` folder
*    gen-libs -- contains the source code and CMake build script for the gmath and gperf example libraries. The resulting binaries are copied into the `distribution` folder. By default, gen-libs module is disabled in setting.gradle and app/build.gradle, so it won't show up in Android Studio IDE. If re-generating lib is desirable, follow comments inside settings.gradle and app/build.gradle to enable this module, generate libs, then disable it again to avoid unnecessary confusion.

The main goal of the sample is to demo how to use 3rd party libs, it is not to demonstrate lib package generation. Toward that goal, the pre-built libs are included in the `distribution` folder.

When importing libraries into your app, include the following in your app's `CMakeLists.txt` file (in the following order): 

*    import libraries as static or shared(using `add_library`)
*    configure each library binary location(using `set_target_properties`)
*    configure each library headers location (using `target_include_directories`)

For shared libraries, notify gradle to pack them into APK. One simple way is to include the shared lib directory into application's jniLibs directory:
*    jniLibs.srcDirs = ['../distribution/gperf/lib']

Pre-requisites
--------------
- Android Studio 3.0.0 with [NDK](https://developer.android.com/ndk/) bundle.

Getting Started
---------------
1. [Download Android Studio](http://developer.android.com/sdk/index.html)
1. Launch Android Studio.
1. Open the sample directory.
1. Open *File/Project Structure...*
  - Click *Download* or *Select NDK location*.
1. Click *Tools/Android/Sync Project with Gradle Files*.
1. Click *Run/Run 'app'*.

Support
-------
If you've found an error in these samples, please [file an issue](https://github.com/googlesamples/android-ndk/issues/new).

