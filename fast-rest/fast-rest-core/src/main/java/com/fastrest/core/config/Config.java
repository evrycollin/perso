package com.fastrest.core.config;

import com.fastrest.core.util.Json;

public class Config {

    String baseUrl;

    Double version;

    EntityManagerProvider entityManagerProvider;

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

    public EntityManagerProvider getEntityManagerProvider() {
	return entityManagerProvider;
    }

    public void setEntityManagerProvider(
	    EntityManagerProvider entityManagerProvider) {
	this.entityManagerProvider = entityManagerProvider;
    }

//    public static void main(String[] args) {
//
//	Config config = new Config();
//	config.setBaseUrl("/base");
//	config.setVersion(1.1);
//	config.setEntityManagerProvider(new InjectionEntityManagerProvider());
//
//	String json = Json.toJson(config);
//
//	System.out.println(json);
//
//	Config read = Json.fromJson(json, Config.class);
//	System.out.println(read);
//
//    }

    @Override
    public String toString() {
	return Json.toJson(this);
    }

}
