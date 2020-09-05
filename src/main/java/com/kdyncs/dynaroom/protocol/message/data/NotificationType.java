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
