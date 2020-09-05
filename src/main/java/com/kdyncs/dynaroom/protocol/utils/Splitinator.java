package com.kdyncs.dynaroom.protocol.utils;

import java.util.Arrays;

public class Splitinator {
    
    /**
     * Private Constructor for Utility Class
     */
    private Splitinator() {
        throw new AssertionError();
    }
    
    public static byte[][] splitByteArray(byte[] data, int splits) {
        
        // This is Illegal
        if (splits < 2) {
            throw new IllegalArgumentException();
        }
        
        // This is also Illegal
        if (data.length < splits) {
            throw new IllegalArgumentException();
        }
        
        // Prepare Our Data
        byte[][] splitData = new byte[splits][];
        
        // Determine Chunk Sizes
        int size = (int) Math.ceil(data.length / (float) splits);
        
        // Split Data
        for (int i = 0; i < splits; i++) {
            
            // Find start of Range
            int start = i * size;
            
            // Find End of Range inclusive to the Data Size
            int end = Math.min(data.length, i * size + size);
            
            // Split off that Data
            splitData[i] = Arrays.copyOfRange(data, start, end);
        }
        
        return splitData;
    }
}
