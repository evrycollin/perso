package com.fastrest.core.config.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ejb.Stateless;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fastrest.core.service.AbstractFastRestService;

@Stateless
public class J2eeFastRestCoreBean extends AbstractFastRestService implements
		J2eeFastRestCoreLocal {

	@PersistenceContext
	EntityManager entityManager;

	@Inject
	BeanManager beanManager;

	Map<String, EntityManager> units = null;

	AtomicBoolean init = new AtomicBoolean(false);
	
	@Override
	public EntityManager getEntityManager(String unit) {
		if( !init.get() ) {
			Map<String, EntityManager> map = new HashMap<String, EntityManager>();
			units.put( "default", entityManager);
			units=map;
			init.set(true);
		}
		return units.get(unit);
	}

	@Override
	public Set<String> getUnits() {
		return units.keySet();
	}

	@Override
	public Object getService(String serviceName) {
		Bean<?> bean = beanManager.getBeans(serviceName).iterator().next();
		CreationalContext<?> ctx = beanManager.createCreationalContext(bean);
		return beanManager.getReference(bean, bean.getClass(), ctx);
	}

}
