/* Copyright (C) KINGSTON DYNAMICS INC. - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.kdyncs.dynaroom.server.subsystem.room.protocol;

import com.kdyncs.dynaroom.protocol.message.data.MessageType;
import com.kdyncs.dynaroom.protocol.utils.Determinator;
import com.kdyncs.dynaroom.server.subsystem.room.service.ApplicationService;
import com.kdyncs.dynaroom.server.subsystem.room.service.AuthenticationService;
import com.kdyncs.dynaroom.server.subsystem.room.service.RoomService;
import com.kdyncs.dynaroom.server.subsystem.room.service.ConnectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author peter
 */

@Component
public class Processor implements Runnable {
    
    // Logging
    private final Logger log = LogManager.getLogger();
    
    // Command Queue
    private final LinkedBlockingQueue<Command> queue;
    
    // Threading
    private final Thread thread;
    private boolean isRunning;
    
    // Spring Services
    private final ConnectionService connection;
    private final AuthenticationService authentication;
    private final ApplicationService application;
    private final RoomService channel;
    
    @Autowired
    public Processor(ConnectionService connection, AuthenticationService authentication, ApplicationService application, RoomService channel) {

        this.connection = connection;
        this.authentication = authentication;
        this.application = application;
        this.channel = channel;
        
        this.queue = new LinkedBlockingQueue<>();
        this.thread = new Thread(this);
        this.thread.setName("COMMAND_PROCESSOR");

        this.isRunning = false;
    }
    
    public void queueCommand(Command command) {
        queue.add(command);
    }
    
    @PostConstruct
    public void init() {
        start();
    }
    
    public void start() {
        this.isRunning = true;
        this.thread.start();
    }
    
    public void stop() {
        this.isRunning = false;
        this.thread.interrupt();
    }
    
    @Override
    public void run() {
        
        log.debug("Thread Starting");
        
        while (isRunning) {
            
            try {
                
                // Wait for a command to be queued
                Command command = queue.take();
                
                // Handle the Command
                processCommand(command);
                
            } catch (InterruptedException e) {
                // Thread interrupted, likely for shutdown
                log.debug("Thread Interrupted");
                thread.interrupt();
            }
        }
        log.debug("Thread Stopping");
    }
    
    private void processCommand(Command command) {
        
        log.debug("processing command");
        
        MessageType type = Determinator.determinate(command.getData());
        
        switch (type) {
            case ROOM_LIST:
                application.getChannels(command);
                break;
            case AUTHENTICATION_LOGIN:
                authentication.login(command);
                break;
            case AUTHENTICATION_LOGOUT:
                authentication.logout(command);
                break;
            case AUTHENTICATION_DISCONNECT:
                authentication.logout(command);
                connection.remove(command);
                break;
            case ROOM_JOIN:
                channel.join(command);
                break;
            case ROOM_CREATE:
                channel.join(command);
                break;
            default:
                unknownCommand();
                break;
        }
    }
    
    private void unknownCommand() {
        //TODO: Yell at calling user
    }
}
