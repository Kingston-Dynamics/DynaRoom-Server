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

import com.kdyncs.dynaroom.protocol.message.data.NotificationType;
import com.kdyncs.dynaroom.protocol.message.type.Notification;
import com.kdyncs.dynaroom.server.core.ConnectionPool;
import com.kdyncs.dynaroom.server.subsystem.room.model.connection.ClientConnection;
import com.kdyncs.dynaroom.server.subsystem.room.protocol.Command;
import com.kdyncs.dynaroom.protocol.message.type.AuthenticationLogin;
import com.kdyncs.dynaroom.server.subsystem.deployment.ApplicationPool;
import com.kdyncs.dynaroom.server.subsystem.room.model.application.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    
    // Logging
    private static final Logger log = LogManager.getLogger();
    
    // Spring Components
    private final ConnectionPool connections;
    private final ApplicationPool applications;

    @Autowired
    public AuthenticationService(ConnectionPool connections, ApplicationPool applications) {
        this.connections = connections;
        this.applications = applications;
    }

    public void login(Command command) {
        
        log.info("Attempting Login");
        
        // Retrieve Client
        ClientConnection connection = connections.get(command.getIssuer());
        
        // User Can't Login Twice
        if (isAuthenticated(connection)) {
            log.info("Already Authenticated");
            Notification notification = new Notification(NotificationType.ALREADY_AUTHENTICATED);
            connection.getWriter().write(notification.build());
            return;
        }
        
        // Extract Message from Command
        AuthenticationLogin message = new AuthenticationLogin(command.getData());
        
        // Application must be deployed
        if (!applications.isDeployed(message.getApplicationKey())) {
            log.info("Not Deployed");
            Notification notification = new Notification(NotificationType.APPLICATION_NOT_DEPLOYED);
            connection.getWriter().write(notification.build());
            return;
        }
        
        // Get Application
        Application application = applications.get(message.getApplicationKey());
        
        // Update User Information
        connection.setApplicationKey(application.getApplicationKey());
        connection.setExternalID(message.getUniqueIdentifier());
        
        log.info("Private ID: {}", message.getUniqueIdentifier());
        
        // Add User to Application
        application.getUsers().add(connection.getExternalID(), connection.getConnectionID());
        
        // Notify User
        Notification notification = new Notification(NotificationType.AUTHENTICATION_SUCCESSFUL);
        connection.getWriter().write(notification.build());
        
        log.info("Login Successful");
        
//        log.info("Attempting Login");
//        
//        // Retrieve Client
//        ClientConnection connection = connections.get(command.getIssuer());
//        
//        // User Can't Login Twice
//        if (isAuthenticated(connection)) {
//            log.info("Already Authenticated");
//            Notification notification = new Notification(NotificationType.ALREADY_AUTHENTICATED);
//            connection.write(notification.build());
//            return;
//        }
//        
//        // Extract Message from Command
//        AuthenticationLogin msg = new AuthenticationLogin(command.getData());
//
//        // Log Info
//        log.info("Private ID: {}", msg.getPlayerId());
//        
//        // Add User to Application
//        applications.getUsers().add(connection.getData().getPlayerId(), connection.getConnectionID());
//        
//        // Notify User
//        Notification notification = new Notification(NotificationType.AUTHENTICATION_SUCCESSFUL);
//        connection.write(notification.build());
//        
//        log.info("Login Successful");
    }
    
    public void logout(Command command) {
        logout(command.getIssuer());
    }

    public void logout(String connectionId) {
        logout(connections.get(connectionId));
    }

    public void logout(ClientConnection connection) {
        log.info("Attempting Logout");

        if (!isAuthenticated(connection)) {
            log.info("Not logged in");
            return;
        }

        // Get Owned Application
        var application = applications.get(connection.getApplicationKey());
        
        // Remove from Application User Pool
        // TODO: This wont release room names
        application.getUsers().remove(connection.getExternalID());

        log.info("Logout Successful");
    }
    
    public void reconnect(Command command) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    public boolean isAuthenticated(ClientConnection connection) {
        return connection.getApplicationKey() != null;
    }
}