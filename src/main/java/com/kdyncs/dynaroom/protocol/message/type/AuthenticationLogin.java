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
import com.kdyncs.dynaroom.protocol.utils.Readinator;

public class AuthenticationLogin extends Message {

    private static final MessageType type = MessageType.AUTHENTICATION_LOGIN;

    private final String applicationKey;
    private final String uniqueIdentifier;
    
    public AuthenticationLogin(String applicationKey, String auditId, String uniqueIdentifier) {
        super(type.getType(), auditId);
        this.applicationKey = applicationKey;
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public AuthenticationLogin(Readinator reader) {
        super(reader);
        this.applicationKey = reader.readIntPrefixedString();
        this.uniqueIdentifier = reader.readIntPrefixedString();
    }
    
    public AuthenticationLogin(byte[] data) {
        this(new Readinator(data));
    }
    
    public static MessageType getType() {
        return type;
    }
    
    @Override
    public byte[] build() {
        
        // Get All Bytes
        byte[] applicationKeyData = Byteinator.stringToBytesPrefixed(applicationKey);
        byte[] uniqueIdentifierData = Byteinator.stringToBytesPrefixed(uniqueIdentifier);
        
        // Construct
        return Concatinator.concatinateByteArrays(super.build(), applicationKeyData, uniqueIdentifierData);
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }
}
