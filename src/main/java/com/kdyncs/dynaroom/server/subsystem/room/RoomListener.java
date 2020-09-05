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
package com.kdyncs.dynaroom.server.subsystem.room;

import com.kdyncs.dynaroom.server.core.ConnectionListener;
import com.kdyncs.dynaroom.server.subsystem.room.service.ConnectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author peter
 */
@Service
public class RoomListener implements ConnectionListener {

    private static final int PORT = 1234;

    // Logging
    private static final Logger log = LogManager.getLogger();

    // Spring Components
    private final ConnectionService connection;

    // Listening Thread
    private Thread listener;
    private boolean running;

    @Autowired
    public RoomListener(ConnectionService connection) {
        this.connection = connection;
    }

    @PostConstruct
    public void init() {

        // Create a Thread
        this.listener = new Thread(this);

        // Name Thread
        this.listener.setName("ROOM_LISTENER");
    }

    @Override
    public void run() {

        log.info("Attempting to Open Listen Port: " + PORT);

        // Open Listen Socket
        try (ServerSocket socket = new ServerSocket(PORT)) {

            // Wait and See
            while (running) {

                // Wait for Connection
                Socket client = socket.accept();

                // Logging
                log.info("Client Connecting On: {}", socket.getLocalPort());
                log.info("Client Routed to: {}", client.getPort());

                // Create a new Connection Object
                connection.create(client);
            }

        } catch (IOException e) {
            log.error("Exception caught when trying to listen on port {} or listening for a connection", PORT);
            log.error(e.getMessage());
        }
    }

    @Override
    public void start() {

        log.info("Attempting to start Room Listener");

        if (listener.isAlive()) {
            log.warn("Room Listener Already Started");
            return;
        }

        running = true;
        listener.start();
    }

    @Override
    public void stop() {
        //TODO: DO
    }
}
