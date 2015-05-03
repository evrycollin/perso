package com.fastrest.core.service;

import static com.fastrest.core.util.Json.toJson;

import java.util.Set;

import javax.persistence.EntityManager;

import com.fastrest.core.FastCoreService;
import com.fastrest.core.FastRestRequest;
import com.fastrest.core.config.ServiceLocator;
import com.fastrest.core.model.CollectionField;
import com.fastrest.core.model.Entity;
import com.fastrest.core.model.EntityField;
import com.fastrest.core.model.EntityInstance;
import com.fastrest.core.model.JpaModel;
import com.fastrest.core.model.NavigableField;
import com.fastrest.core.util.Json;
import com.fastrest.core.util.PathExecutor;

public abstract class AbstractFastRestService implements FastCoreService {

	public String doGet(ServiceLocator serviceLocator, JpaModel jpaModel,
			FastRestRequest restReq) {

		PathExecutor pe = new PathExecutor(jpaModel, restReq.getPath());

		Json.EntityObjectAdapter.threadLocal.set(restReq);
		try {
			return toJson(pe.navigate());
		} finally {
			Json.EntityObjectAdapter.threadLocal.set(null);
		}
	}

	public Object getById(ServiceLocator serviceLocator, EntityInstance instance) {
		return instance.getEntity().getEntityManager().find(instance.getEntity().getType(),
				instance.getInstanceId());
	}

	public String doDelete(ServiceLocator serviceLocator, JpaModel jpaModel,
			FastRestRequest restReq) {

		EntityInstance instance = new PathExecutor(jpaModel, restReq.getPath())
				.getInstance();
		Object entity = getById(serviceLocator, instance);
		instance.getEntity().getEntityManager().remove(entity);
		instance.getEntity().getEntityManager().flush();
		return Boolean.TRUE.toString();
	}

	public String doPost(ServiceLocator serviceLocator, JpaModel jpaModel,
			FastRestRequest restReq) {
		Json.EntityObjectAdapter.threadLocal.set(restReq);
		try {
			PathExecutor pe = new PathExecutor(jpaModel, restReq.getPath());
			Object targetObject = pe.getTarget();
			if (targetObject instanceof Entity) {
				Entity entity = (Entity) targetObject;
				restReq.setEntityInstance(new EntityInstance(entity, null));

				Object toCreate = restReq.getContent(entity.getType());
				entity.getEntityManager().persist(toCreate);
				entity.getEntityManager().flush();
				return toJson(toCreate);
			} else if (targetObject instanceof NavigableField) {
				EntityInstance instance = pe.getInstance();
				Object entity = getById(serviceLocator, instance);
				restReq.setTargetEntity(entity);
				NavigableField nav = (NavigableField) targetObject;
				restReq.setEntityInstance(new EntityInstance(jpaModel
						.getEntityByType(nav.getTargetType()), null));
				Object toCreate = restReq.getContent(nav.getTargetType());
				instance.getEntity().getEntityManager().persist(toCreate);
				if (nav instanceof EntityField) {
					// TODO : maj relations

				} else if (nav instanceof CollectionField) {
					// TODO : maj relations
				}
				instance.getEntity().getEntityManager().flush();
				return toJson(toCreate);
			}
		} finally {
			Json.EntityObjectAdapter.threadLocal.set(null);
		}
		return null;
	}

	public String doPut(ServiceLocator serviceLocator, JpaModel jpaModel,
			FastRestRequest restReq) {

		EntityInstance instance = new PathExecutor(jpaModel, restReq.getPath())
				.getInstance();
		restReq.setEntityInstance(instance);

		Json.EntityObjectAdapter.threadLocal.set(restReq);
		try {

			restReq.setTargetEntity(getById(serviceLocator, instance));

			Object toUpdate = restReq
					.getContent(instance.getEntity().getType());
			instance.getEntity().getEntityManager().merge(toUpdate);
			instance.getEntity().getEntityManager().flush();

			return toJson(toUpdate);
		} finally {
			Json.EntityObjectAdapter.threadLocal.set(null);
		}

	}
	
	abstract public EntityManager getEntityManager(String unit);
	
	abstract public Object getService(String serviceName);
	
	abstract public Set<String> getUnits();
}
