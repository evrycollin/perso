package com.fastrest.core.config.impl;

import javax.persistence.EntityManager;

import com.fastrest.core.FastCoreService;
import com.fastrest.core.FastRestRequest;
import com.fastrest.core.model.JpaModel;

public interface J2eeFastRestCoreLocal  extends FastCoreService {

    String doGet(EntityManager entityManager, JpaModel jpaModel,
            FastRestRequest restReq) ;

    String doPost(EntityManager entityManager, JpaModel jpaModel,
            FastRestRequest restReq);
    
    String doPut(EntityManager entityManager, JpaModel jpaModel,
            FastRestRequest restReq);
    
    String doDelete(EntityManager entityManager, JpaModel jpaModel,
            FastRestRequest restReq);

    EntityManager getEntityManager();
   

}
