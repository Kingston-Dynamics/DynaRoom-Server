package com.kdyncs.dynaroom.protocol.message.type;

import com.kdyncs.dynaroom.protocol.message.Message;
import com.kdyncs.dynaroom.protocol.message.data.MessageType;
import com.kdyncs.dynaroom.protocol.utils.Keyinator;
import com.kdyncs.dynaroom.protocol.utils.Readinator;

public class AuthenticationDisconnect extends Message {

    private static final MessageType type = MessageType.AUTHENTICATION_DISCONNECT;
    
    public AuthenticationDisconnect(String auditId) {
        super(type.getType(), auditId);
    }
    
    public AuthenticationDisconnect() {
        this(Keyinator.generateGUID());
    }
    
    public AuthenticationDisconnect(Readinator reader) {
        super(reader);
    }
}
