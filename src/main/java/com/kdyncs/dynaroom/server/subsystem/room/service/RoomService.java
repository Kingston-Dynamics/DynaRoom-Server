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

import com.kdyncs.dynaroom.protocol.message.type.RoomCreate;
import com.kdyncs.dynaroom.protocol.message.type.RoomJoin;
import com.kdyncs.dynaroom.server.subsystem.core.ConnectionPool;
import com.kdyncs.dynaroom.server.subsystem.deployment.ApplicationPool;
import com.kdyncs.dynaroom.server.subsystem.room.model.application.Application;
import com.kdyncs.dynaroom.server.subsystem.room.model.application.Room;
import com.kdyncs.dynaroom.server.subsystem.room.model.application.RoomPool;
import com.kdyncs.dynaroom.server.subsystem.room.protocol.Command;
import com.kdyncs.dynaroom.server.subsystem.room.model.connection.ClientConnection;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    // Logging
    private static final Logger log = LogManager.getLogger();

    // Spring Components
    private final ConnectionPool connections;
    private final ApplicationPool applications;
    private final ApplicationContext context;

    public RoomService(ConnectionPool connections, ApplicationPool applications, ApplicationContext context) {
        this.connections = connections;
        this.applications = applications;
        this.context = context;
    }

    public void join(Command command) {

        log.info("Joining Room");

        // Retrieve Client
        var connection = connections.get(command.getIssuer());

        // Rebuild Message
        var message = new RoomJoin(command.getData());

        // Validate Protocol State
        // TODO: 
        
        // Get copy of Application
        var application = applications.get(connection.getApplicationKey());

        // Get Rooms
        var rooms = application.getRooms();

        // Check if Room Exists
        if (!rooms.contains(message.getRoomId())) {
            Room room = new Room();
            room.setCode(message.getRoomId());
            rooms.add(room.getCode(), room);
        }

        var room = rooms.find(message.getRoomId());

        // Send 
        log.info("Room Joined");
    }

    public void create(Command command) {

        log.info("Creating Room");
        
        // Retrieve Client
        var connection = connections.get(command.getIssuer());
        
        // Rebuild Message
        //var message = new RoomCreate(command.getData());
        
        // Validate Protocol State
        // TODO:
        
        // Get copy of Application
        var application = applications.get(connection.getApplicationKey());
                
        // Get Existing Rooms
        var rooms = application.getRooms();
        
        // Generate a Room
        var room = context.getBean(Room.class);
        room.setHost(connection);
        
        // Generate a Unique Room Code
        do {
            room.setCode(generateRoomCode());
        } while (rooms.contains(room.getCode()));
        
        
        // Add Room
        rooms.add(room.getCode(), room);
        
    }

    public void list(Command command) {
        // TODO: Implement Public Room Lookup
    }
    
    // Generate a Room Code
    private static String generateRoomCode() {
        return RandomStringUtils.randomAlphabetic(4);
    }
}
