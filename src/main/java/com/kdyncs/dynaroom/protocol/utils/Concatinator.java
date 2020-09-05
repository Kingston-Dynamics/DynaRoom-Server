package com.kdyncs.dynaroom.protocol.utils;

public class Concatinator {
    
    /**
     * Private Constructor for Utility Class
     */
    private Concatinator() {
        throw new AssertionError();
    }
    
    public static byte[] concatinateByteArrays(byte[]... arrays) {
        
        int length = 0;
        
        for (byte[] array : arrays) {
            length += array.length;
        }
        
        byte[] combined = new byte[length];
        
        int position = 0;
        
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, combined, position, array.length);
            position += array.length;
        }
        
        return combined;
    }
}
