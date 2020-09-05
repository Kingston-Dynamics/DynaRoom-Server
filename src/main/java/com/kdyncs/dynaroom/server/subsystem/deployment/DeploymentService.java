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
package com.kdyncs.dynaroom.server.subsystem.deployment;

import com.kdyncs.dynaroom.server.subsystem.room.model.application.Application;
import com.kdyncs.dynaroom.server.subsystem.room.service.ConnectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author peter
 */
@Service
public class DeploymentService {

    // Logging
    private static final Logger log = LogManager.getLogger();

    // Spring Components
    private final ApplicationPool applications;
    private final ConnectionService connections;
    private final ApplicationContext context;

    @Autowired
    public DeploymentService(ApplicationPool applications, ConnectionService connections, ApplicationContext context) {
        this.applications = applications;
        this.connections = connections;
        this.context = context;
    }

    public void deploy(String key) {

        log.info("Deploying application {}", key);
        
        // Sanity Check
        if (applications.isDeployed(key)) {
            log.error("Attempting to deploy deployed application");
            throw new DeploymentException("Already Deployed");
        }
        
        // Construct Application
        var application = context.getBean(Application.class);
        application.setApplicationKey(key);
        
        applications.add(application);
        
        log.info("Deployed application {}", key);
        
    }

    public void undeploy(String key) {

        log.info("Undeploying application {}", key);

        // Sanity Check
        if (!applications.isDeployed(key)) {
            log.error("Attempting to undeploy undeployed application");
            throw new DeploymentException("Not Deployed");
        }

        // Get Application Copy
        //var application = applications.get(key);
        
        // Remove Application from List
        applications.remove(key);
        
        // TODO: Cleanup Application Removal
//        Map<String, String> users = application.getUsers().getAll();
//
//        for (String user : users.values()) {
//            connections.remove(user);
//        }
        
        log.info("Undeployed application {}", key);
    }
}
