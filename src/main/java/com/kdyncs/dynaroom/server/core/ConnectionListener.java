/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdyncs.dynaroom.server.core;

/**
 *
 * @author peter
 */
public interface ConnectionListener extends Runnable {
    
    @Override
    void run();
    
    void start();
    
    void stop();
    
}
