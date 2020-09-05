/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdyncs.dynaroom.server.subsystem.deployment;

import com.kdyncs.dynaroom.server.subsystem.room.model.application.Application;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author peter
 */

@Component
public class ApplicationPool {
    
    private final Map<String, Application> applications;
    private final Logger log = LogManager.getLogger();
    
    @Autowired
    public ApplicationPool() {
        log.info("Creating new Application Pool");
        applications = new ConcurrentHashMap<>(100);
    }

    public void add(Application application) {
        log.info("Adding Application to Pool");
        applications.put(application.getApplicationKey(), application);
        log.info("Deployments: " + deployCount());
    }
    
    public void remove(String applicationKey) {
        log.debug("Removing Application {} from Pool", applicationKey);
        applications.remove(applicationKey);
        log.info("Deployments: " + deployCount());
    }
    
    public Application get(String applicationKey) {
        return applications.get(applicationKey);
    }
    
    public boolean isDeployed(String applicationKey) {
        return applications.containsKey(applicationKey);
    }
    
    public int deployCount() {
        return applications.size();
    }
    
    public Set<String> getKeys() {
        return applications.keySet();
    }
}
