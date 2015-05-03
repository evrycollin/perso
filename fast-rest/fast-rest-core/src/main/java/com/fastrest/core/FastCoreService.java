package com.fastrest.core;

import java.util.Set;

import javax.persistence.EntityManager;

import com.fastrest.core.config.ServiceLocator;
import com.fastrest.core.model.JpaModel;

public interface FastCoreService {

	String doGet(ServiceLocator serviceLocator, JpaModel jpaModel,
			FastRestRequest restReq);

	String doPut(ServiceLocator serviceLocator, JpaModel jpaModel,
			FastRestRequest restReq);

	String doPost(ServiceLocator serviceLocator, JpaModel jpaModel,
			FastRestRequest restReq);

	String doDelete(ServiceLocator serviceLocator, JpaModel jpaModel,
			FastRestRequest restReq);

	EntityManager getEntityManager( String unit );
	
	Set<String> getUnits();

	Object getService(String serviceName);

}
