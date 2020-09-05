package com.kdyncs.dynaroom.server;

import com.kdyncs.dynaroom.server.subsystem.deployment.DeploymentManager;
import com.kdyncs.dynaroom.server.subsystem.room.RoomListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @author peter
 */

@Component
@SpringBootApplication
@ComponentScan(basePackages = "com.kdyncs.dynaroom")
// @EnableJpaRepositories("com.kdyncs.dynaroom")
@EntityScan("com.kdyncs.dynaroom")
@EnableScheduling
public class Server implements CommandLineRunner {

    // Static Configuration
    public static final int CAPACITY = 2048;
    
    // Logging
    private final Logger log = LogManager.getLogger();
    
    // Spring Components
    private final RoomListener room;
    private final DeploymentManager deployment;
    
    public Server(RoomListener room, DeploymentManager deployment) {
        this.room = room;
        this.deployment = deployment;
    }
    
    public static void main(String[] argv) {
        
        // Create a Spring Application
        SpringApplication application = new SpringApplication(Server.class);
        
        // Run Application
        application.run(argv);
    }
    
    @Override
    public void run(String[] argv) {
        log.info("Starting DynaRoom");
        this.start();
    }

    ////////////////////////
    // SERVER START LOGIC //
    ////////////////////////
    public void start() {
        
        // Startup Room Service
        room.start();
        
        // Startup Deployment Service
        deployment.start();
    }
    
    ////////////////////////////
    // SERVER SHUTDOWN LOGIC  //
    ////////////////////////////
    public void stop() {
        
        // Todo: Implement the Admin CLI to this can be invoked
        
        // Startup Deployment Service
        deployment.stop();
        
        // Startup Room Service
        room.stop();
        
    }
}
