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
package com.kdyncs.dynaroom.database.dao;

import com.kdyncs.dynaroom.common.SafeList;
import com.kdyncs.dynaroom.database.model.ApplicationModel;
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
