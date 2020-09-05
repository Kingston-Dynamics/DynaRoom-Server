/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kdyncs.dynaroom.database.schema.dao;

import com.kdyncs.dynaroom.common.SafeList;
import com.kdyncs.dynaroom.database.schema.model.ApplicationModel;
//import com.kdyncs.dynaroom.database.schema.software.repository.ApplicationRepository;
import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author peter
 */

@Component
public class ApplicationDAO {
    
//    private final ApplicationRepository repository;
    
//    @Autowired
//    public ApplicationDAO(ApplicationRepository repository) {
//        this.repository = repository;
//    }
    
    public List<ApplicationModel> getAllActiveApplications() {
//        return SafeList.get(repository.findAllActiveDeployments());
        return SafeList.get(null);
    }
}
