package com.fastrest.core.config;

import com.fastrest.core.model.JpaModel;
import com.fastrest.core.util.Json;

public class Config {

	Double version;

	boolean cypherIds = false;
	
	boolean enableSecurity = false;

	ServiceLocator serviceLocator;

	EntityBehavior entityBehavior;

	JpaModel jpaModel;

	public JpaModel getJpaModel() {
		return jpaModel;
	}

	public void setJpaModel(JpaModel jpaModel) {
		this.jpaModel = jpaModel;
	}

	public boolean isCypherIds() {
		return cypherIds;
	}

	public void setCypherIds(boolean cypherIds) {
		this.cypherIds = cypherIds;
	}


	public Double getVersion() {
		return version;
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	public EntityBehavior getEntityBehavior() {
		return entityBehavior;
	}

	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	@Override
	public String toString() {
		return Json.toJson(this);
	}

}
