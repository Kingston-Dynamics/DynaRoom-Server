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
package com.kdyncs.dynaroom.server.tasks;
//
//import com.kdyncs.dynaroom.server.core.ConnectionPool;
//import com.kdyncs.dynaroom.server.subsystem.room.model.connection.ClientConnection;
//import com.kdyncs.dynaroom.server.subsystem.room.service.ConnectionService;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.List;
//
//@Service
//public class HeatBeatMonitor {
//
//    // Logging
//    private final Logger log = LogManager.getLogger();
//
//    // Spring Components
//    private final ConnectionPool connectionPool;
//    private final ConnectionService connectionService;
//
//    // Values
//    private static final int MAX_PING = 10;
//
//    @Autowired
//    public HeatBeatMonitor(ConnectionPool connectionPool, ConnectionService connectionService) {
//        this.connectionService = connectionService;
//        this.connectionPool = connectionPool;
//    }
//
//    @Scheduled(fixedRate = 10000)
//    public void monitor() {
//        log.info("Checking Heartbeat");
//
//        // Value to check against
//        Instant now = Instant.now();
//
//        List<ClientConnection> connections = connectionPool.getAll();
//
//        if (connections.isEmpty()) {
//            log.info("No Users");
//            return;
//        }
//
//        for(ClientConnection connection : connections) {
//
//            Duration since = Duration.between(connection.getLastHeartBeat(), now);
//
//            // Check if more than 10 seconds has elapsed
//            if (!since.minusSeconds(MAX_PING).isNegative()) {
//                log.info("Disconnecting User");
//                connectionService.remove(connection);
//            }
//        }
//    }
//}
