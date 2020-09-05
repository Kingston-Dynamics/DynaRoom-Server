package com.kdyncs.dynaroom.protocol.utils;

public class Prefixinator {
    
    /**
     * Private Constructor for Utility Class
     */
    private Prefixinator() {
        throw new AssertionError();
    }
    
    public static byte[] prefix(byte[] data) {
        return Concatinator.concatinateByteArrays(Byteinator.intToBytes(data.length), data);
    }
}
