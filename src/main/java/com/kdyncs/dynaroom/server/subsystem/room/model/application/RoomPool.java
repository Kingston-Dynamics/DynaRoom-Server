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
