package com.kdyncs.dynaroom.server.subsystem.room.service;

import com.kdyncs.dynaroom.server.core.ConnectionPool;
import com.kdyncs.dynaroom.server.subsystem.deployment.ApplicationPool;
import com.kdyncs.dynaroom.server.subsystem.room.model.application.Application;
import com.kdyncs.dynaroom.server.subsystem.room.protocol.Command;
import com.kdyncs.dynaroom.server.subsystem.room.model.connection.ClientConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    
    // Logging
    private static final Logger log = LogManager.getLogger();
    
    // Spring Components
    private final ConnectionPool connections;
    private final ApplicationPool applications;
    private final AuthenticationService authentication;

    @Autowired
    public ApplicationService(ConnectionPool connections, ApplicationPool applications, AuthenticationService authentication) {
        this.connections = connections;
        this.applications = applications;
        this.authentication = authentication;
    }
    
    // Get All Channels
    public void getChannels(Command command) {
        
        log.trace("Getting All Channels");
        
        // Retrieve Client
        ClientConnection connection = connections.get(command.getIssuer());
        
        // Validate protocol state
        if (!authentication.isAuthenticated(connection)) {
            //TODO: Complain about unauthenticated
            return;
        }

        // Lookup Application Key
        Application application = applications.get(connection.getApplicationKey());
        
        // TODO: Return channels to User
        application.getRooms();
    }
}
