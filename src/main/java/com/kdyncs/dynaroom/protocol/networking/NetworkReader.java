package com.kdyncs.dynaroom.protocol.networking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;

/**
 * @author peter
 */
public class NetworkReader extends NetworkThread {
    
    // Logging
    private static final Logger LOG = LoggerFactory.getLogger(NetworkReader.class);

    // Parent
    private final NetworkManager manager;
    private boolean running;
    
    public NetworkReader(NetworkManager manager) {
        this.manager = manager;
        this.thread = new Thread(this);

        thread.setName(manager.getName() + "-R");
    }
    
    @Override
    public void run() {

        LOG.info("Network Reader Started");
        
        while (running) {
            try {
                
                DataInputStream input = new DataInputStream(manager.getSocket().getInputStream());
                
                // Get Data Length
                // TODO: This throws an EOF Excepetion on socket close. Not very clean.
                int length = input.readInt();
                LOG.info("Received Length {} Bytes", length);
                
                // Prepare Data Buffer
                byte[] data = new byte[length];
                
                // Fill Data buffer
                input.readFully(data, 0, data.length);
                LOG.info("Received Data {} Bytes", length);
                
                // Process Input
                manager.handleInput(data);
                
            } catch (EOFException ex) {
                LOG.info("Received End of File. Stopping Reader.");
                manager.handleError(NetworkError.END_OF_FILE);
            } catch (SocketException ex) {
                LOG.trace("Issue Reading From Socket", ex);
                manager.handleError(NetworkError.SOCKET_CLOSED);
            } catch (IOException ex) {
                LOG.info("Issue Reading From Socket", ex);
                manager.handleError(NetworkError.UNKNOWN);
            }
        }

        LOG.info("Network Reader Stopped");
    }
}
