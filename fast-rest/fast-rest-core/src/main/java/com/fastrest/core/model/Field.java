package com.fastrest.core.model;

public abstract class Field {

	private String name;

	transient private Entity entity;

	public Field(Entity entity, String name) {
		this.entity = entity;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Field [name=" + name + "] " + getClass();
	}

	public String getName() {
		return name;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object get(Object entity) {
		try {
			String getterMethod = "get" + name.substring(0, 1).toUpperCase()
					+ name.substring(1);
			return entity.getClass().getMethod(getterMethod).invoke(entity);
		} catch (Throwable th) {
			throw new RuntimeException(th);
		}
	}

}
