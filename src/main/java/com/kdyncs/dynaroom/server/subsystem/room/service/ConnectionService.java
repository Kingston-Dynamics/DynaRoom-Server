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
package com.kdyncs.dynaroom.server.subsystem.room.service;

import com.kdyncs.dynaroom.protocol.networking.NetworkReader;
import com.kdyncs.dynaroom.protocol.networking.NetworkWriter;
import com.kdyncs.dynaroom.protocol.utils.Keyinator;
import com.kdyncs.dynaroom.server.subsystem.core.ConnectionPool;
import com.kdyncs.dynaroom.server.subsystem.room.model.connection.ClientConnection;
import com.kdyncs.dynaroom.server.subsystem.room.protocol.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;

@Service
public class ConnectionService {

    // Logging
    private static final Logger log = LogManager.getLogger();

    // Spring Components
    private final ConnectionPool connections;
    private final ApplicationContext context;
    private final AuthenticationService authentication;

    @Autowired
    public ConnectionService(ConnectionPool connections, AuthenticationService authentication, ApplicationContext context) {
        this.connections = connections;
        this.authentication = authentication;
        this.context = context;
    }

    public void create(Socket socket) {

        // Create Connection Instance
        ClientConnection connection = context.getBean(ClientConnection.class);

        // Add Socket to Connection
        connection.setSocket(socket);

        // Generate a new Connection ID
        String connectionID = Keyinator.generateGUID();
        connection.setConnectionID(connectionID);

        // Register Connection
        connections.add(connectionID, connection);

        // Open Reader and Writer Threads
        NetworkReader reader = new NetworkReader(connection);
        NetworkWriter writer = new NetworkWriter(connection);

        // Attach Reader and Writer threads
        connection.setReader(reader);
        connection.setWriter(writer);

        // Start Networking
        reader.start();
        writer.start();

        // Activate Writing
        connection.setActive(true);
    }

    public void remove(Command command) {
        remove(command.getIssuer());
    }

    public void remove(String connectionId) {
        remove(connections.get(connectionId));
    }

    public void remove(ClientConnection connection) {

        log.trace("Disconnecting User {}", connection.getConnectionID());

        // Disable Writing
        connection.setActive(false);

        // Logout User if Necessary
        if (authentication.isAuthenticated(connection)) {
            authentication.logout(connection);
        }

        try {
            // Stop Networking
            connection.getReader().stop();
            connection.getWriter().stop();
            connection.getSocket().close();
        } catch (IOException e) {
            log.info("Issue Closing Socket {}", e.getLocalizedMessage());
        }

        // Remove connection from Connection Pool
        connections.remove(connection.getConnectionID());
    }
}
