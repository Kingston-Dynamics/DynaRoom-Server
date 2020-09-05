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

package com.kdyncs.dynaroom.server.subsystem.core;

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
