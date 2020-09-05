//package com.kdyncs.dynaroom.server.tasks;
//
//import com.kdyncs.dynaroom.server.core.ConnectionPool;
//import com.kdyncs.dynaroom.server.subsystem.room.model.connection.ClientConnection;
//import com.kdyncs.dynaroom.server.subsystem.room.service.AuthenticationService;
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
//public class AuthenticationMonitor {
//
//    // Logging
//    private final Logger log = LogManager.getLogger();
//
//    // Spring Components
//    private final ConnectionPool connectionPool;
//    private final AuthenticationService authenticationService;
//    private final ConnectionService connectionService;
//
//    // Values
//    private static final int AUTHENTICATION_TIMEOUT = 10;
//
//    @Autowired
//    public AuthenticationMonitor(ConnectionPool connectionPool, AuthenticationService authenticationService, ConnectionService connectionService) {
//        this.connectionService = connectionService;
//        this.authenticationService = authenticationService;
//        this.connectionPool = connectionPool;
//    }
//
//    @Scheduled(fixedRate = 10000)
//    public void monitor() {
//        log.info("Checking Authentication");
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
//            if (!authenticationService.isAuthenticated(connection)) {
//
//                Duration since = Duration.between(connection.getConnectionTime(), now);
//
//                // Check if more than 10 seconds has elapsed
//                if (!since.minusSeconds(AUTHENTICATION_TIMEOUT).isNegative()) {
//                    log.info("Disconnecting User");
//                    connectionService.remove(connection);
//                }
//            }
//        }
//    }
//}
