package com.fastrest.core.config.impl;

import javax.ejb.Stateless;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fastrest.core.FastCoreService;
import com.fastrest.core.service.AbstractFastRestService;

@Stateless
public class J2eeFastRestCoreBean extends AbstractFastRestService implements
		FastCoreService {

	@PersistenceContext
	EntityManager entityManager;

	@Inject
	BeanManager beanManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Object getService(String serviceName) {
		Bean<?> bean = beanManager.getBeans(serviceName).iterator().next();
		CreationalContext<?> ctx = beanManager.createCreationalContext(bean);
		return beanManager.getReference(bean, bean.getClass(), ctx);
	}

}
