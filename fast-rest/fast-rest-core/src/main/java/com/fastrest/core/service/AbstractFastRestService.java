package com.fastrest.core.service;

import static com.fastrest.core.util.Json.toJson;

import javax.persistence.EntityManager;

import com.fastrest.core.FastCoreService;
import com.fastrest.core.FastRestRequest;
import com.fastrest.core.model.CollectionAttribute;
import com.fastrest.core.model.Entity;
import com.fastrest.core.model.EntityAttribute;
import com.fastrest.core.model.EntityInstance;
import com.fastrest.core.model.JpaModel;
import com.fastrest.core.model.NavigableAttribute;
import com.fastrest.core.util.Json;
import com.fastrest.core.util.PathExecutor;

public abstract class AbstractFastRestService implements FastCoreService {

    public String doGet(EntityManager entityManager, JpaModel jpaModel,
	    FastRestRequest restReq) {

	PathExecutor pe = new PathExecutor(jpaModel, restReq.getPath());
	EntityInstance instance = pe.getInstance();
	Object entity = getById(entityManager, instance);

	Json.EntityObjectAdapter.threadLocal.set(restReq);
	try {
	    return toJson(pe.navigate(entity));
	} finally {
	    Json.EntityObjectAdapter.threadLocal.set(null);
	}
    }

    public Object getById(EntityManager entityManager, EntityInstance instance) {
	return entityManager.find(instance.getEntity().getType(),
		instance.getInstanceId());
    }

    public String doDelete(EntityManager entityManager, JpaModel jpaModel,
	    FastRestRequest restReq) {
	EntityInstance instance = new PathExecutor(jpaModel, restReq.getPath())
		.getInstance();
	Object entity = getById(entityManager, instance);
	entityManager.remove(entity);
	entityManager.flush();
	return Boolean.TRUE.toString();
    }

    public String doPost(EntityManager entityManager, JpaModel jpaModel,
	    FastRestRequest restReq) {
	Json.EntityObjectAdapter.threadLocal.set(restReq);
	try {

	    PathExecutor pe = new PathExecutor(jpaModel, restReq.getPath());
	    Object targetObject = pe.getTarget();
	    if (targetObject instanceof Entity) {
		Entity entity = (Entity) targetObject;
		restReq.setEntityInstance(new EntityInstance(entity,null));

		Object toCreate = restReq.getContent(entity.getType());
		entityManager.persist(toCreate);
		entityManager.flush();
		return toJson(toCreate);
	    } else if (targetObject instanceof NavigableAttribute) {
		EntityInstance instance = pe.getInstance();
		Object entity = getById(entityManager, instance);
		restReq.setTargetEntity(entity);
		NavigableAttribute nav = (NavigableAttribute) targetObject;
		restReq.setEntityInstance(new EntityInstance( jpaModel.getEntityByType( nav.getTargetType() ),null) );
		Object toCreate = restReq.getContent(nav.getTargetType());
		entityManager.persist(toCreate);
		if (nav instanceof EntityAttribute) {
		    // TODO : maj relations

		} else if (nav instanceof CollectionAttribute) {
		    // TODO : maj relations
		}
		entityManager.flush();
		return toJson(toCreate);
	    }
	} finally {
	    Json.EntityObjectAdapter.threadLocal.set(null);
	}
	return null;
    }

    public String doPut(EntityManager entityManager, JpaModel jpaModel,
	    FastRestRequest restReq) {

	EntityInstance instance = new PathExecutor(jpaModel, restReq.getPath())
		.getInstance();
	restReq.setEntityInstance(instance);

	Json.EntityObjectAdapter.threadLocal.set(restReq);
	try {

	    restReq.setTargetEntity(getById(entityManager, instance));

	    Object toUpdate = restReq
		    .getContent(instance.getEntity().getType());
	    entityManager.merge(toUpdate);
	    entityManager.flush();

	    return toJson(toUpdate);
	} finally {
	    Json.EntityObjectAdapter.threadLocal.set(null);
	}

    }

}
