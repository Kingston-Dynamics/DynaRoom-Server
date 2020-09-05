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
    private final ApplicationService applications;
    private final RoomService rooms;
    
    @Autowired
    public Processor(ConnectionService connection, AuthenticationService authentication, ApplicationService applications, RoomService room) {

        this.connection = connection;
        this.authentication = authentication;
        this.applications = applications;
        this.rooms = room;
        
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
                rooms.join(command);
                break;
            case ROOM_CREATE:
                rooms.join(command);
                break;
            case ROOM_LIST:
                rooms.list(command);
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
