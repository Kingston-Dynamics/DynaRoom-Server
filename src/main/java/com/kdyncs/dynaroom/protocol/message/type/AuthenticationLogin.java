package com.kdyncs.dynaroom.protocol.message.type;

import com.kdyncs.dynaroom.protocol.message.Message;
import com.kdyncs.dynaroom.protocol.message.data.MessageType;
import com.kdyncs.dynaroom.protocol.utils.Byteinator;
import com.kdyncs.dynaroom.protocol.utils.Concatinator;
import com.kdyncs.dynaroom.protocol.utils.Readinator;

public class AuthenticationLogin extends Message {

    private static final MessageType type = MessageType.AUTHENTICATION_LOGIN;

    private final String applicationKey;
    private final String uniqueIdentifier;
    
    public AuthenticationLogin(String applicationKey, String auditId, String uniqueIdentifier) {
        super(type.getType(), auditId);
        this.applicationKey = applicationKey;
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public AuthenticationLogin(Readinator reader) {
        super(reader);
        this.applicationKey = reader.readIntPrefixedString();
        this.uniqueIdentifier = reader.readIntPrefixedString();
    }
    
    public AuthenticationLogin(byte[] data) {
        this(new Readinator(data));
    }
    
    public static MessageType getType() {
        return type;
    }
    
    @Override
    public byte[] build() {
        
        // Get All Bytes
        byte[] applicationKeyData = Byteinator.stringToBytesPrefixed(applicationKey);
        byte[] uniqueIdentifierData = Byteinator.stringToBytesPrefixed(uniqueIdentifier);
        
        // Construct
        return Concatinator.concatinateByteArrays(super.build(), applicationKeyData, uniqueIdentifierData);
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }
}
