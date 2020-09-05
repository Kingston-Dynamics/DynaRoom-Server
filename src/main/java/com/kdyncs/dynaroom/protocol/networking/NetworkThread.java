package com.kdyncs.dynaroom.protocol.networking;

public abstract class NetworkThread implements Runnable{

    protected Thread thread;
    protected boolean running;

    public void start() {

        if (thread.isAlive()) {
            return;
        }

        running = true;
        thread.start();
    }

    public void stop() {

        if (!thread.isAlive()) {
            return;
        }

        running = false;
        thread.interrupt();
    }
}
