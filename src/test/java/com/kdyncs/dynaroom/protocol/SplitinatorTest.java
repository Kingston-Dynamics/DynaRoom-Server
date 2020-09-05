package com.kdyncs.dynaroom.protocol;

import com.kdyncs.dynaroom.protocol.utils.Byteinator;
import com.kdyncs.dynaroom.protocol.utils.Concatinator;
import com.kdyncs.dynaroom.protocol.utils.Splitinator;
import org.junit.Assert;
import org.junit.Test;

public class SplitinatorTest {
    
    @Test
    public void testSplitinator() {
        
        String v1 = "Down with the system";
        
        byte[] b1 = Byteinator.stringToBytes(v1);
        
        byte[][] d1 = Splitinator.splitByteArray(b1, 3);
        
        byte[] c2 = Concatinator.concatinateByteArrays(d1);
        
        String r1 = Byteinator.bytesToString(c2);
        
        Assert.assertEquals(v1, r1);
    }
}
