/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdyncs.dynaroom.server.subsystem.room.model.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author peter
 */

@Component
@Scope("prototype")
public class Application {
    
    // Components
    private final UserPool users;
    private final RoomPool rooms;
    
    @Autowired
    public Application(UserPool users, RoomPool rooms) {
        this.users = users;
        this.rooms = rooms;
    }

    //////////////////////
    // Application Data //
    //////////////////////
    
    private String applicationKey;

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }

    public RoomPool getRooms() {
        return rooms;
    }
    
    public UserPool getUsers() {
        return users;
    }
}
