package com.fastrest.core.model;

import javax.persistence.metamodel.SingularAttribute;

public class EntityAttribute extends NavigableAttribute {

	public EntityAttribute(Entity entity, SingularAttribute<?, ?> sa) {
		super(entity, sa.getName(), sa.getJavaType(), false);
	}

	public void set(Object entity, Object value) {
		try {
			String setterMethod = "set"
					+ getName().substring(0, 1).toUpperCase()
					+ getName().substring(1);
			entity.getClass().getMethod(setterMethod, getTargetType())
					.invoke(entity, value);
		} catch (Throwable th) {
			throw new RuntimeException(th);
		}
	}

}
