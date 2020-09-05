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

import com.kdyncs.dynaroom.protocol.message.type.RoomJoin;
import com.kdyncs.dynaroom.server.subsystem.core.ConnectionPool;
import com.kdyncs.dynaroom.server.subsystem.deployment.ApplicationPool;
import com.kdyncs.dynaroom.server.subsystem.room.model.application.Application;
import com.kdyncs.dynaroom.server.subsystem.room.model.application.Room;
import com.kdyncs.dynaroom.server.subsystem.room.model.application.RoomPool;
import com.kdyncs.dynaroom.server.subsystem.room.protocol.Command;
import com.kdyncs.dynaroom.server.subsystem.room.model.connection.ClientConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    // Logging
    private static final Logger log = LogManager.getLogger();

    // Spring Components
    private final ConnectionPool connections;
    private final ApplicationPool applications;

    public RoomService(ConnectionPool connections, ApplicationPool applications) {
        this.connections = connections;
        this.applications = applications;
    }

    public void join(Command command) {

        log.info("Joining Channel");

        // Retrieve Client
        ClientConnection connection = connections.get(command.getIssuer());

        // Rebuild Message
        RoomJoin message = new RoomJoin(command.getData());

        // TODO: Validate Protocol State
        //
        Application application = applications.get(connection.getApplicationKey());

        // Get Channels
        RoomPool rooms = application.getRooms();

        // Check if Room Exists
        if (!rooms.contains(message.getRoomId())) {
            Room room = new Room();
            room.setCode(message.getRoomId());
            rooms.add(room.getCode(), room);
        }

        Room room = rooms.find(message.getRoomId());

        // Send 
        log.info("Channel Joined");
    }

    public void create() {

        //Room room = new Room();
        //rooms.add(room.getCode(), room);
    }

    public void list() {

    }
}
