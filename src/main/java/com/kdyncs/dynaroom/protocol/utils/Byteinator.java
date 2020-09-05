package com.kdyncs.dynaroom.protocol.utils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class Byteinator {
    
    // Uninstantiable
    private Byteinator() {
        throw new AssertionError("Instantiating utility class.");
    }
    
    public static byte[] doubleToBytes(double value) {
        return ByteBuffer.allocate(8).putDouble(value).array();
    }
    
    public static double bytesToDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).asDoubleBuffer().get();
    }
    
    public static byte[] floatToBytes(float value) {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }
    
    public static float bytesToFloat(byte[] bytes) {
        return ByteBuffer.wrap(bytes).asFloatBuffer().get();
    }
    
    public static byte[] longToBytes(long value) {
        return ByteBuffer.allocate(8).putLong(value).array();
    }
    
    public static long bytesToLong(byte[] bytes) {
        return ByteBuffer.wrap(bytes).asLongBuffer().get();
    }
    
    public static byte[] intToBytes(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }
    
    public static int bytesToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).asIntBuffer().get();
    }
    
    public static byte[] shortToBytes(short value) {
        return ByteBuffer.allocate(2).putShort(value).array();
    }
    
    public static short bytesToShort(byte[] bytes) {
        return ByteBuffer.wrap(bytes).asShortBuffer().get();
    }
    
    public static byte[] stringToBytes(String value) {
        return value.getBytes(StandardCharsets.UTF_8);
    }
    
    public static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
    
    public static byte[] stringToBytesPrefixed(String value) {
        return Prefixinator.prefix(stringToBytes(value));
    }
}
