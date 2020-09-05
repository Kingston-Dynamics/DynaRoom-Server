package com.kdyncs.dynaroom.protocol;

import com.kdyncs.dynaroom.protocol.utils.Byteinator;
import com.kdyncs.dynaroom.protocol.utils.Concatinator;
import com.kdyncs.dynaroom.protocol.utils.Readinator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("MagicNumber")
public class ReadinatorTest {
    
    @Test
    public void testData() {
        
        // Define Values
        int v1 = 800;
        short v2 = 200;
        String v3 = "Fuck that noise";
        String v4 = "To the moon and beyond";
        int v5 = 150;
        float v6 = 650.650f;
        double v7 = 950.0000f;
        long v8 = 18000L;
        
        // Byteinate Values
        byte[] d1 = Byteinator.intToBytes(v1);
        byte[] d2 = Byteinator.shortToBytes(v2);
        byte[] d3 = Byteinator.stringToBytes(v3);
        byte[] d4 = Byteinator.stringToBytes(v4);
        byte[] d5 = Byteinator.intToBytes(v5);
        byte[] d6 = Byteinator.floatToBytes(v6);
        byte[] d7 = Byteinator.doubleToBytes(v7);
        byte[] d8 = Byteinator.longToBytes(v8);
        
        // Concatinate Values
        byte[] c1 = Concatinator.concatinateByteArrays(d1, d2, d3, d4, d5, d6, d7, d8);
        
        // Read Values
        Readinator STR = new Readinator(c1);
        
        int r1 = STR.readInt();
        int r2 = STR.readShort();
        String r3 = STR.readString(v3.length());
        String r4 = STR.readString(v4.length());
        int r5 = STR.readInt();
        float r6 = STR.readFloat();
        double r7 = STR.readDouble();
        long r8 = STR.readLong();
        
        assertEquals(v1, r1);
        assertEquals(v2, r2);
        assertEquals(v3, r3);
        assertEquals(v4, r4);
        assertEquals(v5, r5);
        assertEquals(v6, r6, 0.00);
        assertEquals(v7, r7, 0.00);
        assertEquals(v8, r8);
    }
}
