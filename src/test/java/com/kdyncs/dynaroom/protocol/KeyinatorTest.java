package com.kdyncs.dynaroom.protocol;

import com.kdyncs.dynaroom.protocol.utils.Keyinator;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("MagicNumber")
public class KeyinatorTest {
    
    @Test
    public void test() {
        Assert.assertEquals(36, Keyinator.generateGUID().length());
    }
}
