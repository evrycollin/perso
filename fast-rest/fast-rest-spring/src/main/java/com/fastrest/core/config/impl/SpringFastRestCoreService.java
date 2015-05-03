package com.fastrest.core.config.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastrest.core.FastRestRequest;
import com.fastrest.core.config.ServiceLocator;
import com.fastrest.core.model.JpaModel;
import com.fastrest.core.service.AbstractFastRestService;

@Service("fastRestCoreService")
public class SpringFastRestCoreService extends AbstractFastRestService
		implements InitializingBean {

	@PersistenceContext
	private EntityManager entityManager;

	protected Map<String, EntityManager> units = new HashMap<String, EntityManager>();

	public void afterPropertiesSet() throws Exception {
		units.put("default", entityManager);
	}

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

	@Override
	public EntityManager getEntityManager(String name) {
		return units.get(name);
	}

	@Override
	public Set<String> getUnits() {
		return units.keySet();
	}

	@Override
	public Object getService(String serviceName) {
		return applicationContext.getBean(serviceName);
	}
}
