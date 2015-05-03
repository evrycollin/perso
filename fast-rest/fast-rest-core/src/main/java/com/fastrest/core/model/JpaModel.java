package com.fastrest.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import com.fastrest.core.config.Config;
import com.fastrest.core.config.ServiceLocator;

public class JpaModel {

	private Map<String, List<Entity>> model = new HashMap<String, List<Entity>>();

	transient private Map<String, Entity> byName = new HashMap<String, Entity>();
	transient private Map<Class<?>, Entity> byType = new HashMap<Class<?>, Entity>();
	transient private ServiceLocator serviceLocator;

	public JpaModel(Config config) {
		this.serviceLocator = config.getServiceLocator();

		if (serviceLocator.getService() == null )
			return;
		
		for( String unit : serviceLocator.getService().getUnits() ) {

			EntityManager entityManager = serviceLocator.getService().getEntityManager(unit);
			model.put(unit, new ArrayList<Entity>());
			for (EntityType<?> entityType : entityManager.getEntityManagerFactory()
					.getMetamodel().getEntities()) {
				Entity entity = new Entity(entityManager, unit, config, serviceLocator, this, entityType);
				model.get(unit).add(entity);
				byName.put(unit+"."+entity.getName(), entity);
				byType.put(entity.getType(), entity);
			}
		}

	}

	public Entity getEntityByName(String entityName) {
		return byName.get(entityName);
	}

	public Entity getEntityByType(Class<?> entityType) {
		return byType.get(entityType);
	}

	public Map<String, List<Entity>> getModel() {
		return model;
	}

	public void setEntities(Map<String, List<Entity>> entities) {
		this.model = entities;
	}

	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	@Override
	public String toString() {
		String res = "JpaModel [entities=\n";
		for( String unit : model.keySet() ) {
			for (Entity entity : model.get(unit)) {
				res += "- " + entity + "\n";
			}
		}
		res += "]";
		return res;
	}

}
