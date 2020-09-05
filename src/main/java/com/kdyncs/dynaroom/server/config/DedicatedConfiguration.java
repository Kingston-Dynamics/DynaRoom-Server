/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdyncs.dynaroom.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author peter
 */

@Component
@ConfigurationProperties("dynaroom.dedicated")
public class DedicatedConfiguration {
    
    private boolean isDedicated;
    private String applicationKey;
    
    public boolean isDedicated() {
        return isDedicated;
    }
    
    public void setIsDedicated(boolean isDedicated) {
        this.isDedicated = isDedicated;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }
}
