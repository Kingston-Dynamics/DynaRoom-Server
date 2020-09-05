package com.kdyncs.dynaroom.protocol.utils;

import java.nio.ByteBuffer;

public class Readinator {
    
    private static final int GUID_LENGTH = 36;
    private final byte[] data;
    private int position;
    
    public Readinator(byte[] data) {
        this.data = data;
        this.position = 0;
    }
    
    private void advance() {
        position++;
    }
    
    private void advance(int cnt) {
        position += cnt;
    }
    
    public void reset() {
        position = 0;
    }
    
    public byte readByte() {
        byte b1 = data[position];
        advance();
        return b1;
    }
    
    public byte[] readBytes(int length) {
        
        // Pull String Data together
        byte[] temp = new byte[length];
        System.arraycopy(data, position, temp, 0, length);
        
        // Advance past String data
        advance(length);
        
        // Gib back
        return temp;
    }
    
    public short readShort() {
        
        // Get Data
        byte[] bytes = readBytes(2);
        
        // Return Value
        return ByteBuffer.wrap(bytes).asShortBuffer().get();
    }
    
    public int readInt() {
        
        // Get Data
        byte[] bytes = readBytes(4);
        
        // Return Value
        return ByteBuffer.wrap(bytes).asIntBuffer().get();
    }
    
    public long readLong() {
        
        // Get Data
        byte[] bytes = readBytes(8);
        
        // Return Value
        return ByteBuffer.wrap(bytes).asLongBuffer().get();
    }
    
    public float readFloat() {
        
        // Get Data
        byte[] bytes = readBytes(4);
        
        // Return Value
        return ByteBuffer.wrap(bytes).asFloatBuffer().get();
    }
    
    public double readDouble() {
        
        // Get Data
        byte[] bytes = readBytes(8);
        
        // Return Value
        return ByteBuffer.wrap(bytes).asDoubleBuffer().get();
    }
    
    public String readString(int length) {
        
        // Pull String Data together
        byte[] bytes = readBytes(length);
        
        // Gib Back
        return new String(bytes);
    }
    
    public byte[] readIntPrefixedBytes() {
        
        // Length is set using a byte array
        int length = readInt();
        
        // Gib Back
        return readBytes(length);
    }
    
    public String readIntPrefixedString() {
        
        // Length is set using a byte array
        int length = readInt();
        
        // Gib Back
        return readString(length);
    }
    
    public String readAuditId() {
        return readString(GUID_LENGTH);
    }
}
