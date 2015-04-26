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

	private List<Entity> entities = new ArrayList<Entity>();

	transient private Map<String, Entity> byName = new HashMap<String, Entity>();
	transient private Map<Class<?>, Entity> byType = new HashMap<Class<?>, Entity>();
	transient private ServiceLocator serviceLocator;
	transient private EntityManager entityManager;

	public JpaModel(Config config) {
		this.serviceLocator = config.getServiceLocator();

		if (serviceLocator.getService() == null
				|| serviceLocator.getService().getEntityManager() == null)
			return;

		this.entityManager = serviceLocator.getService().getEntityManager();

		for (EntityType<?> entityType : entityManager.getEntityManagerFactory()
				.getMetamodel().getEntities()) {
			Entity entity = new Entity(config, serviceLocator, this, entityType);
			entities.add(entity);
			byName.put(entity.getName(), entity);
			byType.put(entity.getType(), entity);
		}

	}

	public Entity getEntityByName(String entityName) {
		return byName.get(entityName);
	}

	public Entity getEntityByType(Class<?> entityType) {
		return byType.get(entityType);
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	@Override
	public String toString() {
		String res = "JpaModel [entities=\n";
		for (Entity entity : entities) {
			res += "- " + entity + "\n";
		}
		res += "]";
		return res;
	}

}
