/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdyncs.dynaroom.server.subsystem.deployment;

import com.kdyncs.dynaroom.database.dao.ApplicationDAO;
import com.kdyncs.dynaroom.database.model.ApplicationModel;
import com.kdyncs.dynaroom.server.config.DedicatedConfiguration;
import com.kdyncs.dynaroom.server.subsystem.room.model.application.Application;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class DeploymentManager implements Runnable {
    
    public static final int L = 10000;
    // Logging
    private static final Logger log = LogManager.getLogger();
    
    // Spring Components
    private final ApplicationDAO applicationDAO;
    private final ApplicationPool applicationPool;
    private final ApplicationContext context;
    private final DeploymentService deploymentService;
    private final DedicatedConfiguration config;
    
    // Threading
    private Thread listener;
    private boolean running;
    
    @Autowired
    public DeploymentManager(ApplicationDAO applicationDAO, ApplicationPool applicationPool, ApplicationContext context, DeploymentService deploymentService, DedicatedConfiguration config) {
        this.applicationDAO = applicationDAO;
        this.applicationPool = applicationPool;
        this.context = context;
        this.deploymentService = deploymentService;
        this.config = config;
    }
    
    @PostConstruct
    public void init() {
        this.listener = new Thread(this);

        // Name Thread
        this.listener.setName("DEPLOYMENT_MANAGER");

    }
    
    @Override
    public void run() {
        
        log.info("Starting deployment manager.");
        
        // Check if configuration is in dedicated mode
        if (config.isDedicated()) {
            
            log.info("DynaRoom is running in dedicated mode");
            log.info("Deploying dedicated application");
            
            // Build Application
            Application application = context.getBean(Application.class);
            application.setApplicationKey(config.getApplicationKey());
            
            // Add Application to Application Pool
            applicationPool.add(application);
            
            // Stop Deployment Manager
            running = false;
        } else {
            log.warn("DynaRoom is running in shared mode");
            throw new UnsupportedOperationException("Unimplemented");
        }

//        while (running) {
//            
//            log.debug("Checking Deployments.");
//
//            /*
//              Find Active Applications
//             */
//            
//            List<ApplicationModel> applications = applicationDAO.getAllActiveApplications();
//            
//            log.debug("Currently Active Apps: " + applications.size());
//            log.debug("Currently Deployed Apps: " + applicationPool.deployCount());
//            
//            
//            // Deploy Valid Applications.
//            for (ApplicationModel application : applications) {
//                // If Not Deploy then Deploy
//                if (applicationPool.isDeployed(application.getKey().toString())) {
//                    log.debug("Deploying Application");
//                    
//                    // Create Instance of Application Component
//                    Application deployableApplication = context.getBean(Application.class);
//                    
//                    // Fill Out Data
//                    deployableApplication.setApplicationKey(application.getKey().toString());
//                    
//                    // Register (Accept Connections)
//                    applicationPool.add(deployableApplication);
//                }
//            }
//
//            /*
//              Undeploy Any Stopped Applications
//             */
//            ArrayList<String> undeployables = new ArrayList<>(100);
//            
//            // Find all undeployable Keys
//            for (String key : applicationPool.getKeys()) {
//                
//                // Check if we should undeploy
//                boolean found = shouldUndeploy(applications, key);
//                
//                // Undeploy if necessary
//                if (!found) {
//                    undeployables.add(key);
//                }
//            }
//            
//            // Undeployificate them
//            for (String key : undeployables) {
//                deploymentService.undeploy(key);
//            }
//            
//            // Slow it down
//            try {
//                log.debug("Finished Checking Deployments.");
//                Thread.sleep(L);
//            } catch (InterruptedException ex) {
//                log.debug("Deployment Manager Interrupted.");
//            }
//        }
        
        log.info("Stopping Deployment Manager.");
    }
    
//    /**
//     * Check if application should undeploy
//     *
//     * @param applications list of applications
//     * @param key          key of stopped application
//     * @return should application be undeployed
//     */
//    private boolean shouldUndeploy(List<ApplicationModel> applications, String key) {
//        
//        for (ApplicationModel application : applications) {
//            if (key.equals(application.getKey().toString())) {
//                return true;
//            }
//        }
//        
//        return false;
//    }
    
    /**
     * Start the Deployment Manager
     */
    public void start() {

        log.info("Attempting to start deployment manager");

        if (listener.isAlive()) {
            log.warn("Deployment Manager Already Started");
            return;
        }
        
        running = true;
        listener.start();
    }
    
    /**
     * Stop the Deployment Manager
     */
    public void stop() {
        
        log.info("Attempting to stop Deployment Manager");
        
        if (!listener.isAlive()) {
            log.warn("Deployment Manager is Already Stopped");
            return;
        }
        
        running = false;
        listener.interrupt();
    }
}
