package com.fastrest.core.model;

public class EntityInstance {

	Entity entity;

	Object instanceId;

	String link;

	public EntityInstance() {
	}

	public EntityInstance(Entity entity, Object instanceId) {
		super();
		this.entity = entity;
		this.instanceId = instanceId;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Object getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Object instanceId) {
		this.instanceId = instanceId;
	}

	@Override
	public String toString() {
		return "EntityInstance [entity=" + entity + ", instanceId="
				+ instanceId + "]";
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

}
