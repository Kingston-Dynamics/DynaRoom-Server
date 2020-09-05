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
package com.kdyncs.dynaroom.server.subsystem.room.model.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peter
 */

@Component
public class UserPool {
    
    private static final Logger log = LogManager.getLogger();
    
    // Private ID (User Created) Public ID (DS Connection)
    private final Map<String, String> users;
    
    /**
     * Instantiate User Pool
     */
    public UserPool() {
        users = new ConcurrentHashMap<>(100);
    }
    
    /**
     * Adds User
     *
     * @param privateID    Private ID of User
     * @param connectionID Connection ID of User
     */
    public void add(String privateID, String connectionID) {
        log.info("Adding; Private ID {} Connection ID {}", privateID, connectionID);
        users.put(privateID, connectionID);
    }
    
    /**
     * Replaces User
     *
     * @param privateID    Private ID of User
     * @param connectionID Connection ID of User
     */
    public void replace(String privateID, String connectionID) {
        log.info("Replacing; Private ID {} Connection ID {}", privateID, connectionID);
        users.replace(privateID, connectionID);
    }
    
    /**
     * Removes User
     *
     * @param privateID Private ID of User
     */
    public void remove(String privateID) {
        log.info("Removing; Private ID {}", privateID);
        users.remove(privateID);
    }
    
    /**
     * Checks if user is present
     *
     * @param privateID Private ID of User
     * @return User Present
     */
    public boolean contains(String privateID) {
        return users.containsKey(privateID);
    }
    
    /**
     * Finds user in Pool
     *
     * @param privateID Private ID of User
     * @return Connection ID of User
     */
    public String find(String privateID) {
        log.info("Finding; Private ID {}", privateID);
        return users.get(privateID);
    }
    
    /**
     * Get All Users
     *
     * @return Map of Users
     */
    public Map<String, String> getAll() {
        return this.users;
    }
}
