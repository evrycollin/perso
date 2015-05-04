package com.fastrest.core.config.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fastrest.core.config.impl.SpringFastRestCoreService;

public class MyAppServiceLocator extends SpringFastRestCoreService {
	
	@PersistenceContext(unitName="unit1")
	private EntityManager emUnit1;

	@PersistenceContext(unitName="unit2")
	private EntityManager emUnit2;

	@Override
	public void afterPropertiesSet() throws Exception {
		units.put("unit1", emUnit1);
		units.put("unit2", emUnit2);
	}

}
