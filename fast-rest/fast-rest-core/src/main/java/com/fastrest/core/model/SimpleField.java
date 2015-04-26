package com.fastrest.core.model;

import javax.persistence.metamodel.SingularAttribute;

public class SimpleField extends Field {

	private Class<?> type;

	public SimpleField(Entity entity, SingularAttribute<?, ?> sa) {
		super(entity, sa.getName());
		this.type = sa.getJavaType();
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public void set(Object entity, Object value) {
		try {
			String setterMethod = "set"
					+ getName().substring(0, 1).toUpperCase()
					+ getName().substring(1);
			entity.getClass().getMethod(setterMethod, getType())
					.invoke(entity, value);
		} catch (Throwable th) {
			throw new RuntimeException(th);
		}
	}

}
