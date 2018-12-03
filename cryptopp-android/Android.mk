## Android.mk - Android build file for Crypto++. 
##              Based on Android.mk by Jeffrey Walton,
##              https://github.com/noloader/cryptopp-android

ifeq ($(NDK_LOG),1)
    $(info Crypto++: TARGET_ARCH: $(TARGET_ARCH))
    $(info Crypto++: TARGET_PLATFORM: $(TARGET_PLATFORM))
endif

LOCAL_PATH := $(call my-dir)

#####################################################################
# Adjust CRYPTOPP_PATH
CRYPTOPP_PATH ?=

ifeq ($(NDK_LOG),1)
  ifeq ($CRYPTOPP_PATH),)
    $(info Crypto++: CRYPTOPP_PATH is empty)
  else
    $(info Crypto++: CRYPTOPP_PATH is $(CRYPTOPP_PATH))
  endif
endif

#####################################################################
# Library source files
CRYPTOPP_SRC_FILES := \
    cryptlib.cpp cpu.cpp integer.cpp 3way.cpp adler32.cpp algebra.cpp \
    algparam.cpp arc4.cpp aria-simd.cpp aria.cpp ariatab.cpp asn.cpp \
    authenc.cpp base32.cpp base64.cpp basecode.cpp bfinit.cpp blake2-simd.cpp \
    blake2.cpp blowfish.cpp blumshub.cpp camellia.cpp cast.cpp casts.cpp \
    cbcmac.cpp ccm.cpp chacha-simd.cpp chacha.cpp cham-simd.cpp cham.cpp \
    channels.cpp cmac.cpp crc-simd.cpp crc.cpp default.cpp des.cpp dessp.cpp \
    dh.cpp dh2.cpp dll.cpp dsa.cpp eax.cpp ec2n.cpp eccrypto.cpp ecp.cpp \
    elgamal.cpp emsa2.cpp eprecomp.cpp esign.cpp files.cpp filters.cpp \
    fips140.cpp fipstest.cpp gcm-simd.cpp gcm.cpp gf256.cpp gf2_32.cpp \
    gf2n.cpp gfpcrypt.cpp gost.cpp gzip.cpp hc128.cpp hc256.cpp hex.cpp \
    hight.cpp hmac.cpp hrtimer.cpp ida.cpp idea.cpp iterhash.cpp kalyna.cpp \
    kalynatab.cpp keccak.cpp keccakc.cpp lea-simd.cpp lea.cpp luc.cpp mars.cpp \
    marss.cpp md2.cpp md4.cpp md5.cpp misc.cpp modes.cpp mqueue.cpp mqv.cpp \
    nbtheory.cpp neon-simd.cpp oaep.cpp osrng.cpp padlkrng.cpp panama.cpp \
    pkcspad.cpp poly1305.cpp polynomi.cpp ppc-simd.cpp pssr.cpp pubkey.cpp \
    queue.cpp rabbit.cpp rabin.cpp randpool.cpp rc2.cpp rc5.cpp rc6.cpp \
    rdrand.cpp rdtables.cpp rijndael-simd.cpp rijndael.cpp ripemd.cpp rng.cpp \
    rsa.cpp rw.cpp safer.cpp salsa.cpp scrypt.cpp seal.cpp seed.cpp \
    serpent.cpp sha-simd.cpp sha.cpp sha3.cpp shacal2-simd.cpp shacal2.cpp \
    shark.cpp sharkbox.cpp simeck-simd.cpp simeck.cpp simon.cpp \
    simon128-simd.cpp simon64-simd.cpp skipjack.cpp sm3.cpp sm4-simd.cpp \
    sm4.cpp sosemanuk.cpp speck.cpp speck128-simd.cpp speck64-simd.cpp \
    square.cpp squaretb.cpp sse-simd.cpp strciphr.cpp tea.cpp tftables.cpp \
    threefish.cpp tiger.cpp tigertab.cpp ttmac.cpp tweetnacl.cpp twofish.cpp \
    vmac.cpp wake.cpp whrlpool.cpp xtr.cpp xtrcrypt.cpp zdeflate.cpp \
    zinflate.cpp zlib.cpp

#####################################################################
# ARM A-32 source file
ifeq ($(TARGET_ARCH),arm)
    CRYPTOPP_SRC_FILES += aes-armv4.S
    LOCAL_ARM_MODE := arm
    LOCAL_FILTER_ASM :=
endif

#####################################################################
# Shared object
include $(CLEAR_VARS)
LOCAL_MODULE := cryptopp_shared
LOCAL_SRC_FILES := $(addprefix $(CRYPTOPP_PATH),$(CRYPTOPP_SRC_FILES))
LOCAL_CPPFLAGS := -Wall
LOCAL_CPP_FEATURES := rtti exceptions
LOCAL_LDFLAGS := -Wl,--exclude-libs,ALL -Wl,--as-needed

LOCAL_EXPORT_CFLAGS := $(LOCAL_CFLAGS)
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/..

include $(BUILD_SHARED_LIBRARY)