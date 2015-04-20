package com.fastrest.core.config.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fastrest.core.FastRestRequest;
import com.fastrest.core.model.JpaModel;
import com.fastrest.core.service.AbstractFastRestService;
@Stateless
public class J2eeFastRestCoreBean extends AbstractFastRestService implements J2eeFastRestCoreLocal {
    
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public String doGet(EntityManager entityManager, JpaModel jpaModel,
            FastRestRequest restReq) {
        return super.doGet(entityManager, jpaModel, restReq);
    }

    @Override
    public String doPost(EntityManager entityManager, JpaModel jpaModel,
            FastRestRequest restReq) {
        return super.doPost(entityManager, jpaModel, restReq);
    }
    
    @Override
    public String doPut(EntityManager entityManager, JpaModel jpaModel,
            FastRestRequest restReq) {
        return super.doPut(entityManager, jpaModel, restReq);
    }
    
    @Override
    public String doDelete(EntityManager entityManager, JpaModel jpaModel,
            FastRestRequest restReq) {
        return super.doDelete(entityManager, jpaModel, restReq);
    }

    public EntityManager getEntityManager() {
	return entityManager;
    }
   
}
