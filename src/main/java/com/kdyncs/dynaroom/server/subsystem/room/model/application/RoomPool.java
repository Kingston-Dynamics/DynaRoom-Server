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
    
    private final Map<String, Room> rooms;
    
    public RoomPool() {
        rooms = new ConcurrentHashMap<>(1000);
    }
    
    public void add(String roomId, Room room) {
        rooms.put(roomId, room);
    }
    
    public void remove(String roomId) {
        rooms.remove(roomId);
    }
    
    public boolean contains(String roomId) {
        return rooms.containsKey(roomId);
    }
    
    public Room find(String roomId) {
        return rooms.get(roomId);
    }
}
