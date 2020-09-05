/*
 * Copyright (C) 2020 Kingston Dynamics Inc.
 * 
 * This file is part of DynaRoom
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
