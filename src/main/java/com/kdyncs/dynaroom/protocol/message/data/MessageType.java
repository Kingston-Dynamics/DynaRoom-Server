package com.kdyncs.dynaroom.protocol.message.data;

public enum MessageType {
    
    // Default Type
    UNKNOWN(0x0000),
        
    // Notification Commands
    NOTIFICATION(0x2000),
    
    // Authentication Commands
    AUTHENTICATION_LOGIN(0x3000),
    AUTHENTICATION_LOGOUT(0x3001),
    AUTHENTICATION_DISCONNECT(0x3003),
      
    // Channel Commands
    ROOM_LIST(0x8004),
    ROOM_CREATE(0x8006),
    ROOM_JOIN(0x8008);
    
    private final int type;
    
    MessageType(int type) {
        this.type = type;
    }
    
    public static MessageType fromValue(int type) {
        
        MessageType[] types = MessageType.values();
        
        for (MessageType t : types) {
            
            if (t.type == type) {
                return t;
            }
        }
        return UNKNOWN;
    }
    
    public int getType() {
        return type;
    }
}
