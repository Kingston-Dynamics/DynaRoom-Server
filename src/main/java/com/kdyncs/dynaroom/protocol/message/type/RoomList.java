package com.kdyncs.dynaroom.protocol.message.type;

import com.kdyncs.dynaroom.protocol.message.Message;
import com.kdyncs.dynaroom.protocol.message.data.MessageType;
import com.kdyncs.dynaroom.protocol.utils.Keyinator;

public class RoomList extends Message {

    private static final MessageType type = MessageType.ROOM_LIST;
    
    public RoomList(String auditId) {
        super(type.getType(), auditId);
    }
    
    public RoomList() {
        this(Keyinator.generateGUID());
    }
}
