/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdyncs.dynaroom.server.subsystem.deployment;

import com.kdyncs.dynaroom.server.subsystem.room.model.application.Application;
import com.kdyncs.dynaroom.server.subsystem.room.service.AuthenticationService;
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
