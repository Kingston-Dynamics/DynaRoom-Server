package com.kdyncs.dynaroom.protocol.message.data;

public enum NotificationType {
    
    // AUTHENTICATION
    AUTHENTICATION_SUCCESSFUL("AUTHEI0001"),
    AUTHENTICATION_FAILURE("AUTHEE0001"),
    ALREADY_AUTHENTICATED("AUTHEE0002"),
    API_KEY_INVALID("AUTHEE0003"),
    AUTHENTICATION_TIME_EXCEEDED("AUTHEE0004"),
            
    // APPLICATION SPECIFIC
    APPLICATION_NOT_DEPLOYED("APPLIE0000"),
    
    // SERVER SPECIFIC
    DEPLOYMENT_SHUT_DOWN("DISCOI0001");
    
    private final String code;
    
    NotificationType(String code) {
        this.code = code;
    }
    
    public static NotificationType fromValue(String code) {
        NotificationType[] types = NotificationType.values();
        
        for (NotificationType type : types) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
    
    public String getCode() {
        return code;
    }
}
