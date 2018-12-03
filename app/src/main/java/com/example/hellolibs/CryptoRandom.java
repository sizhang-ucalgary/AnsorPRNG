package com.example.hellolibs;

public class CryptoRandom {

    static {
        System.loadLibrary("c++_shared");
        System.loadLibrary("hello-libs");
    }

    private static Object lock = new Object();

    private static native int CryptoSetSeed(byte[] bytes);
    private static native int CryptoNextBytes(byte[] bytes);

    public static int SetSeed(byte[] seed) {
        synchronized (lock) {
            return CryptoSetSeed(seed);
        }
    }

    public static int NextBytes(byte[] bytes) {
        synchronized (lock) {
            return CryptoNextBytes(bytes);
        }
    }

    public int setSeed(byte[] seed) {
        synchronized (lock) {
            return CryptoSetSeed(seed);
        }
    }

    public int nextBytes(byte[] bytes) {
        synchronized (lock) {
            return CryptoNextBytes(bytes);
        }
    }

}
