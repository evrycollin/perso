package com.fastrest.core.config;

import com.fastrest.core.model.JpaModel;
import com.fastrest.core.util.Json;

public class Config {

	String baseUrl;

	Double version;

	boolean cypherIds = false;

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

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
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

	// public static void main(String[] args) {
	//
	// Config config = new Config();
	// config.setBaseUrl("/base");
	// config.setVersion(1.1);
	// config.setEntityManagerProvider(new InjectionEntityManagerProvider());
	//
	// String json = Json.toJson(config);
	//
	// System.out.println(json);
	//
	// Config read = Json.fromJson(json, Config.class);
	// System.out.println(read);
	//
	// }

	@Override
	public String toString() {
		return Json.toJson(this);
	}

}
