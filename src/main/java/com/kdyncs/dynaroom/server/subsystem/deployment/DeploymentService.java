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
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public DeploymentService(ApplicationPool applications, ConnectionService connections) {
        this.applications = applications;
        this.connections = connections;
    }

    public void deploy() {

    }

    public void undeploy(String key) {

        log.info("Undeploying application {}", key);

        Application application = applications.get(key);

        // Sanity Checking
        if (application == null) {
            throw new DeploymentException("Application ");
        }

        Map<String, String> users = application.getUsers().getAll();

        for (String user : users.values()) {
            connections.remove(user);
        }

        log.info("undeployed application {}", key);
    }
}
