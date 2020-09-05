package com.kdyncs.dynaroom.protocol.message.type;

import com.kdyncs.dynaroom.protocol.message.Message;
import com.kdyncs.dynaroom.protocol.message.data.MessageType;
import com.kdyncs.dynaroom.protocol.utils.Keyinator;

public class AuthenticationLogout extends Message {

    private static final MessageType type = MessageType.AUTHENTICATION_LOGOUT;
    
    public AuthenticationLogout(String auditId) {
        super(type.getType(), auditId);
    }
    
    public AuthenticationLogout() {
        this(Keyinator.generateGUID());
    }
}
