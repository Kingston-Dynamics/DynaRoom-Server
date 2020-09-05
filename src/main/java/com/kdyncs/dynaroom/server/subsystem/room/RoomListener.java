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
