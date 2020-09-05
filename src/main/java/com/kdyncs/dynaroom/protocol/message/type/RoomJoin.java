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

package com.kdyncs.dynaroom.protocol.message.type;

import com.kdyncs.dynaroom.protocol.message.Message;
import com.kdyncs.dynaroom.protocol.message.data.MessageType;
import com.kdyncs.dynaroom.protocol.utils.Byteinator;
import com.kdyncs.dynaroom.protocol.utils.Concatinator;
import com.kdyncs.dynaroom.protocol.utils.Keyinator;
import com.kdyncs.dynaroom.protocol.utils.Readinator;

public class RoomJoin extends Message {

    private static final MessageType type = MessageType.ROOM_JOIN;
    
    private final String roomId;
    
    public RoomJoin(String id, String auditId) {
        super(type.getType(), auditId);
        
        this.roomId = id;
    }
    
    public RoomJoin(String id) {
        this(id, Keyinator.generateGUID());
    }
    
    public RoomJoin(Readinator reader) {
        super(reader);
        
        this.roomId = reader.readIntPrefixedString();
    }
    
    public RoomJoin(byte[] data) {
        this(new Readinator(data));
    }
    
    public static MessageType getType() {
        return type;
    }
    
    @Override
    public byte[] build() {
        // Build Channel Connect
        byte[] channelData = Byteinator.stringToBytesPrefixed(roomId);
        return Concatinator.concatinateByteArrays(super.build(), channelData);
    }
    
    public String getRoomId() {
        return roomId;
    }
}
