package com.kdyncs.dynaroom.server.subsystem.room.model.application;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author peter
 */

@Component
@Scope("prototype")
public class Room {
    
    // Channel Data
    private String code;
    private String host;
    private boolean isPublic;
        
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String name) {
        this.code = name;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
