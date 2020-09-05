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
