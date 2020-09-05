package com.kdyncs.dynaroom.protocol.utils;

import java.util.Base64;

public class Encodeinator {
    
    /**
     * Private Constructor for Utility Class
     */
    private Encodeinator() {
        throw new AssertionError();
    }
    
    public static byte[] encodeinate(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }
    
    public static byte[] decodeinate(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }
}
