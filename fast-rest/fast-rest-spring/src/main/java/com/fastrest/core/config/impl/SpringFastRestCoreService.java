package com.fastrest.core.config.impl;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastrest.core.FastRestRequest;
import com.fastrest.core.model.JpaModel;
import com.fastrest.core.service.AbstractFastRestService;
@Service("fastRestCoreService")
public class SpringFastRestCoreService extends AbstractFastRestService {

    @Override
    @Transactional
    public String doGet(EntityManager entityManager, JpaModel jpaModel,
            FastRestRequest restReq) {
        return super.doGet(entityManager, jpaModel, restReq);
    }

    @Override
    @Transactional
    public String doPost(EntityManager entityManager, JpaModel jpaModel,
            FastRestRequest restReq) {
        // TODO Auto-generated method stub
        return super.doPost(entityManager, jpaModel, restReq);
    }
    
    @Override
    @Transactional
    public String doPut(EntityManager entityManager, JpaModel jpaModel,
            FastRestRequest restReq) {
        // TODO Auto-generated method stub
        return super.doPut(entityManager, jpaModel, restReq);
    }
    
    @Override
    @Transactional
    public String doDelete(EntityManager entityManager, JpaModel jpaModel,
            FastRestRequest restReq) {
        // TODO Auto-generated method stub
        return super.doDelete(entityManager, jpaModel, restReq);
    }
}
