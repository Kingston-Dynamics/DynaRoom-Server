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

package com.kdyncs.dynaroom.protocol.networking;

import com.kdyncs.dynaroom.protocol.utils.Byteinator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author peter
 */
public class NetworkWriter extends NetworkThread {
    
    // Logging
    private static final Logger LOG = LoggerFactory.getLogger(NetworkWriter.class);

    // Output Queue
    private final LinkedBlockingQueue<byte[]> queue;

    // Parent
    private final NetworkManager manager;
    
    public NetworkWriter(NetworkManager manager) {
        this.manager = manager;
        this.queue = new LinkedBlockingQueue<>();
        this.thread = new Thread(this);

        thread.setName(manager.getName() + "-W");
    }
    
    @Override
    public void run() {
        
        LOG.info("Network Writer Started");
        
        while (running) {
            try {
                
                // Queue Up Writable Data
                byte[] data = queue.take();
                
                // Write Message length and Data
                DataOutputStream output = new DataOutputStream(manager.getSocket().getOutputStream());
                LOG.info("Writing Length {} Bytes", data.length);
                output.write(Byteinator.intToBytes(data.length));
                LOG.info("Writing Message {} Bytes", data.length);
                output.write(data);
                output.flush();
                
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                LOG.warn("Writing Interrupted");
            } catch (IOException ex) {
                LOG.warn("Writing Issue", ex);
            }
        }

        LOG.info("Network Writer Stopped");
    }
    
    public void write(byte[] data) {
        queue.add(data);
    }
}
