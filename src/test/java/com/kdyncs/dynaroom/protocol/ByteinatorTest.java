package com.kdyncs.dynaroom.protocol;

import com.kdyncs.dynaroom.protocol.utils.Byteinator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("MagicNumber")
public class ByteinatorTest {
    
    @Test
    public void TestDouble() {
        double initial = 999.99;
        byte[] encoded = Byteinator.doubleToBytes(initial);
        double decoded = Byteinator.bytesToDouble(encoded);
        assertEquals(initial, decoded, 0.0);
    }
    
    @Test
    public void TestFloat() {
        float initial = 999.99f;
        byte[] encoded = Byteinator.floatToBytes(initial);
        float decoded = Byteinator.bytesToFloat(encoded);
        assertEquals(initial, decoded, 0.0);
    }
    
    @Test
    public void TestLong() {
        long initial = 999;
        byte[] encoded = Byteinator.longToBytes(initial);
        long decoded = Byteinator.bytesToLong(encoded);
        assertEquals(initial, decoded);
    }
    
    @Test
    public void TestInt() {
        int initial = 800;
        byte[] encoded = Byteinator.intToBytes(initial);
        int decoded = Byteinator.bytesToInt(encoded);
        assertEquals(initial, decoded);
        
    }
    
    @Test
    public void TestShort() {
        short initial = 500;
        byte[] encoded = Byteinator.shortToBytes(initial);
        short decoded = Byteinator.bytesToShort(encoded);
        assertEquals(initial, decoded);
    }
    
    @Test
    public void TestString() {
        String initial = "Testing String Please Ignore";
        byte[] encoded = Byteinator.stringToBytes(initial);
        String decoded = Byteinator.bytesToString(encoded);
        assertEquals(initial, decoded);
    }
    
}
