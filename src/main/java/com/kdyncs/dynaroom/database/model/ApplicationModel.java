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
