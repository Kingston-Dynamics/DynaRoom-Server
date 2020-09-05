package com.kdyncs.dynaroom.server.subsystem.room.model.application;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author peter
 */

@Component
public class RoomPool {
    
    private final Map<String, Room> channels;
    
    public RoomPool() {
        channels = new ConcurrentHashMap<>(1000);
    }
    
    public void add(String roomId, Room room) {
        channels.put(roomId, room);
    }
    
    public void remove(String channelID) {
        channels.remove(channelID);
    }
    
    public boolean contains(String channelID) {
        return channels.containsKey(channelID);
    }
    
    public Room find(String channelID) {
        return channels.get(channelID);
    }
}
