///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.kdyncs.dynaroom.database.schema.software.repository;
//
//import com.kdyncs.dynaroom.database.schema.software.model.ApplicationModel;
//import java.util.List;
//import java.util.UUID;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
///**
// *
// * @author peter
// */
//@Repository
//public interface ApplicationRepository extends JpaRepository<ApplicationModel, UUID> {
//    
//    @Query("from ApplicationModel app where app.deployed=true")
//    List<ApplicationModel> findAllActiveDeployments();
//    
//}
