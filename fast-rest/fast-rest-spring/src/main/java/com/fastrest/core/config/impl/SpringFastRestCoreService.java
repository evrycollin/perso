package com.fastrest.core.config.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastrest.core.FastRestRequest;
import com.fastrest.core.config.ServiceLocator;
import com.fastrest.core.model.JpaModel;
import com.fastrest.core.service.AbstractFastRestService;
@Service("fastRestCoreService")
public class SpringFastRestCoreService extends AbstractFastRestService {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ApplicationContext applicationContext;
    

    @Override
    @Transactional
    public String doGet(ServiceLocator serviceLocator, JpaModel jpaModel,
            FastRestRequest restReq) {
        return super.doGet(serviceLocator, jpaModel, restReq);
    }

    @Override
    @Transactional
    public String doPost(ServiceLocator serviceLocator, JpaModel jpaModel,
            FastRestRequest restReq) {
        return super.doPost(serviceLocator, jpaModel, restReq);
    }
    
    @Override
    @Transactional
    public String doPut(ServiceLocator serviceLocator, JpaModel jpaModel,
            FastRestRequest restReq) {
        return super.doPut(serviceLocator, jpaModel, restReq);
    }
    
    @Override
    @Transactional
    public String doDelete(ServiceLocator serviceLocator, JpaModel jpaModel,
            FastRestRequest restReq) {
        return super.doDelete(serviceLocator, jpaModel, restReq);
    }
    
    public EntityManager getEntityManager() {
	return entityManager;
    }
    
    public Object getService(String serviceName) {
    	return applicationContext.getBean(serviceName);
    }
}
