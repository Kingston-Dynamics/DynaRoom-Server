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
