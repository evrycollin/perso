package com.fastrest.core;

import javax.persistence.EntityManager;

import com.fastrest.core.model.JpaModel;

public interface FastCoreService {

    String doGet(EntityManager entityManager, JpaModel jpaModel, FastRestRequest restReq);

    String doPut(EntityManager entityManager, JpaModel jpaModel, FastRestRequest restReq);

    String doPost(EntityManager entityManager, JpaModel jpaModel, FastRestRequest restReq);

    String doDelete(EntityManager entityManager, JpaModel jpaModel, FastRestRequest restReq);

}
