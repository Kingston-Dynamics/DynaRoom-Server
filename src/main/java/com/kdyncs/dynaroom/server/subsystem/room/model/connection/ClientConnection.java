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
package com.kdyncs.dynaroom.server.subsystem.room.model.connection;

import com.kdyncs.dynaroom.protocol.networking.NetworkError;
import com.kdyncs.dynaroom.protocol.networking.NetworkManager;
import com.kdyncs.dynaroom.protocol.networking.NetworkReader;
import com.kdyncs.dynaroom.protocol.networking.NetworkWriter;
import com.kdyncs.dynaroom.server.subsystem.room.protocol.Command;
import com.kdyncs.dynaroom.server.subsystem.room.protocol.Processor;
import com.kdyncs.dynaroom.server.subsystem.room.service.ConnectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.time.Instant;

/**
 * @author peter
 */

@Component
@Scope("prototype")
public class ClientConnection implements NetworkManager {

    // Logging
    private final Logger log = LogManager.getLogger();

    // Spring Components
    private final Processor processor;
    private final ConnectionService connection;
    
    // Store instance of Socket
    private Socket socket;
    private final Instant connectionTime;
    
    // Input and Output
    private NetworkWriter writer;
    private NetworkReader reader;
    
    // Connection Pool Reference
    private String connectionID;
    private String externalID;
    private String applicationKey;
    private boolean active;

    @Autowired
    public ClientConnection(Processor processor, ConnectionService connection) {
        this.processor = processor;
        this.connection = connection;

        // Client is Inactive by Default
        this.active = false;

        // Set Connection Time
        connectionTime = Instant.now();
    }
    
    @Override
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getConnectionID() {
        return connectionID;
    }
    
    public void setConnectionID(String connectionID) {
        this.connectionID = connectionID;
    }

    public NetworkWriter getWriter() {
        return writer;
    }
    
    public void setWriter(NetworkWriter writer) {
        this.writer = writer;
    }
    
    public NetworkReader getReader() {
        return reader;
    }
    
    public void setReader(NetworkReader reader) {
        this.reader = reader;
    }

    public void write(byte[] data) {
        if (active) {
            writer.write(data);
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getConnectionTime() {
        return connectionTime;
    }

    public String getApplicationKey() {
        return applicationKey;
    }
    
    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }
    
    @Override
    public void handleError(NetworkError error) {

        switch (error) {
            case END_OF_FILE:
                log.info("Client disconnected, terminating connection");
                connection.remove(this);
                return;
            case SOCKET_CLOSED:
                log.info("Socket Closed");
                return;
            case UNKNOWN:
                log.error("Unknown network error encountered, terminating connection");
                connection.remove(this);
                return;
        }

        log.error("Unhandled network error, terminating connection.");
        connection.remove(this);
    }

    @Override
    public void handleInput(byte[] data) {
        processor.queueCommand(new Command(connectionID, data));
    }

    @Override
    public String getName() {
        return getConnectionID();
    }

    public String getExternalID() {
        return externalID;
    }

    public void setExternalID(String externalID) {
        this.externalID = externalID;
    }
}
