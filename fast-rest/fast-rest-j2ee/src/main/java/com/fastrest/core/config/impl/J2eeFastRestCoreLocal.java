package com.fastrest.core.config.impl;

import javax.persistence.EntityManager;

import com.fastrest.core.FastCoreService;
import com.fastrest.core.FastRestRequest;
import com.fastrest.core.config.ServiceLocator;
import com.fastrest.core.model.JpaModel;

public interface J2eeFastRestCoreLocal  extends FastCoreService {

    String doGet(ServiceLocator serviceLocator, JpaModel jpaModel,
            FastRestRequest restReq) ;

    String doPost(ServiceLocator serviceLocator, JpaModel jpaModel,
            FastRestRequest restReq);
    
    String doPut(ServiceLocator serviceLocator, JpaModel jpaModel,
            FastRestRequest restReq);
    
    String doDelete(ServiceLocator serviceLocator, JpaModel jpaModel,
            FastRestRequest restReq);

    EntityManager getEntityManager();
   

}
