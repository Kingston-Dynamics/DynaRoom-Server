package com.kdyncs.dynaroom.protocol.utils;

import java.util.UUID;

public class Keyinator {
    
    /**
     * Private Constructor for Utility Class
     */
    private Keyinator() {
        throw new AssertionError();
    }
    
    // Generates a GUID
    public static String generateGUID() {
        return UUID.randomUUID().toString();
    }
}
