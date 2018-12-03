#include <jni.h>
#include <cstring>
#include <cinttypes>
#include <android/log.h>
#include <string>
#include <iostream>
#include <iomanip>
#include <osrng.h>



class ReadByteBuffer {
public:
    explicit ReadByteBuffer(JNIEnv*& env, jbyteArray& barr)
            : m_env(env), m_arr(barr), m_ptr(NULL), m_len(0) {
        if(m_env && m_arr) {
            m_ptr = m_env->GetByteArrayElements(m_arr, NULL);
            m_len = m_env->GetArrayLength(m_arr);
        }
    }

    ~ReadByteBuffer() {
        if(m_env && m_arr) {
            m_env->ReleaseByteArrayElements(m_arr, m_ptr, JNI_ABORT);
        }
    }

    const jbyte* GetByteArray() const {
        return (const jbyte*) m_ptr;
    }

    size_t GetArrayLen() const {
        if(m_len < 0)
            return 0;
        return (size_t) m_len;
    }

private:
    JNIEnv*& m_env;
    jbyteArray& m_arr;

    jbyte* m_ptr;
    jint m_len;
};


class WriteByteBuffer {
public:
    explicit WriteByteBuffer(JNIEnv*& env, jbyteArray& barr)
            : m_env(env), m_arr(barr), m_ptr(NULL), m_len(0) {
        if(m_env && m_arr) {
            m_ptr = m_env->GetByteArrayElements(m_arr, NULL);
            m_len = m_env->GetArrayLength(m_arr);
        }
    }

    ~WriteByteBuffer() {
        if(m_env && m_arr) {
            m_env->ReleaseByteArrayElements(m_arr, m_ptr, 0);
        }
    }

    jbyte* GetByteArray() const {
        return (jbyte*) m_ptr;
    }

    size_t GetArrayLen() const {
        if(m_len < 0)
            return 0;
        return (size_t) m_len;
    }

private:
    JNIEnv*& m_env;
    jbyteArray& m_arr;

    jbyte* m_ptr;
    jint m_len;
};





#define LOG_TAG "hello-libs"
#define LOG_DEBUG(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))
#define LOG_INFO(...) ((void)__android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__))
#define LOG_WARN(...) ((void)__android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__))
#define LOG_ERROR(...) ((void)__android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__))


static CryptoPP::AutoSeededRandomPool& GetPRNG() {
    static CryptoPP::AutoSeededRandomPool prng;
    return prng;
}


extern "C" JNIEXPORT jint JNICALL
Java_com_example_hellolibs_CryptoRandom_CryptoSetSeed(JNIEnv* env, jclass, jbyteArray seed) {
//    LOG_DEBUG("Entered CryptoSetSeed");
    int consumed = 0;

    try {
        if (!env) {
            LOG_ERROR("CryptoSetSeed: environment is NULL");
        }

        if (!seed) {
            // OK if the caller passed NULL for the array
            LOG_WARN("CryptoSetSeed: byte array is NULL");
        }

        if (env && seed) {
            ReadByteBuffer buffer(env, seed);
            const jbyte* _arr = buffer.GetByteArray();
            size_t _len = buffer.GetArrayLen();

            if ((_arr == NULL)) {
                LOG_ERROR("CryptoSetSeed: array pointer is not valid");
            } else if ((_len == 0)) {
                LOG_ERROR("CryptoSetSeed: array size is not valid");
            } else {
                CryptoPP::AutoSeededRandomPool& prng = GetPRNG();
                prng.IncorporateEntropy(reinterpret_cast<const CryptoPP::byte*>(_arr), _len);
                LOG_INFO("CryptoSetSeed: seeded with %d bytes", (int )_len);
                consumed += (int) _len;
            }
        }
    } catch (const CryptoPP::Exception& ex) {
        LOG_ERROR("CryptoSetSeed: Crypto++ exception: \"%s\"", ex.what());
        return 0;
    }

    return consumed;
}


extern "C" JNIEXPORT  jint JNICALL
Java_com_example_hellolibs_CryptoRandom_CryptoNextBytes(JNIEnv* env, jclass, jbyteArray bytes) {
//    LOG_DEBUG("Entered CryptoNextBytes");
    int retrieved = 0;

    try {

        if (!env) {
            LOG_ERROR("CryptoNextBytes: environment is NULL");
        }

        if (!bytes) {
            // OK if the caller passed NULL for the array
            LOG_WARN("CryptoNextBytes: byte array is NULL");
        }

        if (env && bytes) {
            WriteByteBuffer buffer(env, bytes);
            jbyte* _arr = buffer.GetByteArray();
            size_t _len = buffer.GetArrayLen();

            if ((_arr == NULL)) {
                LOG_ERROR("CryptoNextBytes: array pointer is not valid");
            } else if ((_len == 0)) {
                LOG_ERROR("CryptoNextBytes: array size is not valid");
            } else {
                CryptoPP::AutoSeededRandomPool& prng = GetPRNG();
                prng.GenerateBlock(reinterpret_cast<CryptoPP::byte*>(_arr), _len);
//                LOG_INFO("CryptoNextBytes: generated %d bytes", (int )_len);
                retrieved += (int) _len;
            }
        }
    } catch (const CryptoPP::Exception& ex) {
        LOG_ERROR("CryptoNextBytes: Crypto++ exception: \"%s\"", ex.what());
        return 0;
    }

    return retrieved;
}



//extern "C" JNIEXPORT jlong JNICALL
//Java_com_example_hellolibs_CryptoRandom_createInstance(JNIEnv *env, jobject thiz) {
//    return (jlong) new CryptoPP::AutoSeededRandomPool();
//}
//
//extern "C" JNIEXPORT void JNICALL
//Java_com_example_hellolibs_CryptoRandom_destroyInstance(JNIEnv *env, jobject thiz, jlong p_native_ptr) {
//    if( p_native_ptr )
//        delete reinterpret_cast<CryptoPP::AutoSeededRandomPool*> (p_native_ptr);
//}
//
//extern "C" JNIEXPORT void JNICALL
//Java_com_example_hellolibs_CryptoRandom_setSeed(JNIEnv *env, jobject thiz, jlong p_native_ptr, jbyteArray arr) {
//    jbyte* m_ptr = env->GetByteArrayElements(arr, NULL);
//    jint m_len = env->GetArrayLength(arr);
//    CryptoPP::AutoSeededRandomPool* prng = reinterpret_cast<CryptoPP::AutoSeededRandomPool*> (p_native_ptr);
//    prng->IncorporateEntropy(reinterpret_cast<const CryptoPP::byte*>(m_ptr), (size_t) m_len);
//    env->ReleaseByteArrayElements(arr, m_ptr, JNI_ABORT);
//}
//
//extern "C" JNIEXPORT void JNICALL
//Java_com_example_hellolibs_CryptoRandom_nextBytes(JNIEnv *env, jobject thiz, jlong p_native_ptr, jbyteArray arr) {
//    jbyte* m_ptr = env->GetByteArrayElements(arr, NULL);
//    jint m_len = env->GetArrayLength(arr);
//    CryptoPP::AutoSeededRandomPool* prng = reinterpret_cast<CryptoPP::AutoSeededRandomPool*> (p_native_ptr);
//    prng->GenerateBlock(reinterpret_cast<CryptoPP::byte*>(m_ptr), (size_t) m_len);
//    env->ReleaseByteArrayElements(arr, m_ptr, 0);
//}
