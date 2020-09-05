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
