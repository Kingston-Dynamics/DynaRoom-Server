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
