package com.kdyncs.dynaroom.server.core;

import com.kdyncs.dynaroom.server.subsystem.room.model.connection.ClientConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peter
 */

@Component
public class ConnectionPool {
    
    private static final Logger log = LogManager.getLogger();
    
    private final Map<String, ClientConnection> connections;
    
    private ConnectionPool() {
        connections = new ConcurrentHashMap<>(1000);
    }
    
    public void add(String connectionID, ClientConnection connection) {
        log.info("Adding {}", connectionID);
        connections.put(connectionID, connection);
    }
    
    public void remove(String connectionID) {
        log.info("Removing {}", connectionID);
        connections.remove(connectionID);
    }
    
    public List<ClientConnection> getAll() {
        return new ArrayList<>(connections.values());
    }
    
    public ClientConnection get(String connectionID) {
        log.info("Getting {}", connectionID);
        return connections.get(connectionID);
    }
    
    public int size() {
        return connections.size();
    }
}
