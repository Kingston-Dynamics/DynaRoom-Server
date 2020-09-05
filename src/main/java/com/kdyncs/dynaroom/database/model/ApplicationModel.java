/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdyncs.dynaroom.database.model;

import java.util.UUID;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Table;

/**
 *
 * @author peter
 */

//@Entity(name = "ApplicationModel")
//@Table(name = "applications", schema = "ds_software")
public class ApplicationModel {
    
//    @Id
//    @GeneratedValue
//    @Column(name = "application_id")
    private Integer id;
    
//    @Column(name = "is_deployed")
    private boolean deployed;

//    @GeneratedValue
//    @Column (name = "application_key")
    private UUID key;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
        
    public boolean isDeployed() {
        return deployed;
    }
    
    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }
    
    public UUID getKey() {
        return key;
    }
    
    public void setKey(UUID apikey) {
        this.key = apikey;
    }
}
