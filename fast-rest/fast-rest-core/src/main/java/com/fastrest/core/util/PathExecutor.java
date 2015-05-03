package com.fastrest.core.util;

import java.util.ArrayList;
import java.util.List;

import com.fastrest.core.model.CollectionField;
import com.fastrest.core.model.Entity;
import com.fastrest.core.model.EntityInstance;
import com.fastrest.core.model.Field;
import com.fastrest.core.model.JpaModel;
import com.fastrest.core.model.NavigableField;

public class PathExecutor {

	JpaModel jpaModel;
	List<String> path;

	public PathExecutor(JpaModel jpaModel, List<String> path) {
		this.jpaModel = jpaModel;
		this.path = path;
	}

	public EntityInstance getInstance() {

		List<String> tokens = new ArrayList<String>(path);

		String entityName = tokens.remove(0);
		Entity rootEntity = jpaModel
				.getEntityByName((entityName.indexOf('.') > 0 ? "" : "default.")
						+ entityName);
		if (rootEntity == null) {
			throw new RuntimeException("entity not found : " + entityName);
		}
		String idStr = Cypher.unmaskId(rootEntity.getType().getSimpleName(),
				tokens.remove(0));
		Object instanceId = rootEntity.getIdFromString(idStr);

		EntityInstance root = new EntityInstance(rootEntity, instanceId);

		return tokens.size() > 0 ? getNextInstance(root, tokens) : root;
	}

	private EntityInstance getNextInstance(EntityInstance parentInstance,
			List<String> tokens) {

		String link = tokens.remove(0);
		NavigableField nav = (NavigableField) parentInstance.getEntity()
				.getFields().get(link);
		if (tokens.size() == 0) {
			parentInstance.setLink(link);
			return parentInstance;
		}

		Entity targetEntity = jpaModel.getEntityByType(nav.getTargetType());

		EntityInstance entityInstance = new EntityInstance(targetEntity, null);
		if (nav instanceof CollectionField) {
			String idStr = Cypher.unmaskId(targetEntity.getType()
					.getSimpleName(), tokens.remove(0));

			Object targetId = targetEntity.getIdFromString(idStr);
			entityInstance.setInstanceId(targetId);
		}

		return tokens.size() > 0 ? getNextInstance(entityInstance, tokens)
				: entityInstance;
	}

	public Object navigate() {
		EntityInstance entity = getInstance();

		EntityInstance entityInstance = getInstance();
		if (entityInstance.getLink() != null) {
			Field field = entityInstance.getEntity().getFields()
					.get(entityInstance.getLink());
			return field.get(entity);
		} else {
			return entity;
		}
	}

	public Object getTarget() {
		EntityInstance entityInstance = getInstance();
		if (entityInstance.getLink() == null) {
			return entityInstance.getEntity();
		} else {
			return entityInstance.getEntity().getFields()
					.get(entityInstance.getLink());
		}
	}
}
