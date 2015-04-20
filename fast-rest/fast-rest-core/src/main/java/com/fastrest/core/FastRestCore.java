package com.fastrest.core;

import static com.fastrest.core.util.Json.fromJson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import com.fastrest.core.config.Config;
import com.fastrest.core.model.Entity;
import com.fastrest.core.model.JpaModel;
import com.fastrest.core.util.Json;

public class FastRestCore {

    private Logger logger = Logger.getLogger(FastRestCore.class.getName());

    public static final FastRestCore Instance = new FastRestCore();

    private Config currentConfig;
    EntityManager entityManager;

    JpaModel jpaModel;

    private FastRestCore() {

    }

    private void loadConfig(Config config) {
	this.currentConfig = config;

	this.entityManager = currentConfig.getEntityManagerProvider()
		.getEntityManager();
	// load JpaModel
	this.jpaModel = new JpaModel(this.entityManager);
	
	for( Entity entity : jpaModel.getEntities() ) {
	    Json.GsonBuilder.registerTypeAdapter(entity.getType(), new Json.EntityObjectAdapter(jpaModel) );
	}

	logger.info("Loaded configuration : \n" + currentConfig);
	logger.info("Loaded JpaModel : \n" + Json.toJson(jpaModel));
	logger.info("Entity Manager : "
		+ (this.entityManager != null ? entityManager.toString()
			: "NULL"));

    }
    
    FastCoreService getService() {
	return currentConfig.getEntityManagerProvider().getService();
    }

    public void initializeFromJson(String configJson) {
	loadConfig(fromJson(configJson, Config.class));
    }

    public void initializeFromFile(String file) {
	loadConfig(fromJson(
		getClass().getClassLoader().getResourceAsStream(file),
		Config.class));
    }

    public void initializeFromUrl(String configLocationUrl)
	    throws MalformedURLException, IOException {
	loadConfig(fromJson(new URL(configLocationUrl).openStream(),
		Config.class));
    }
    public String doGet(FastRestRequest restReq) {
	return getService().doGet(entityManager, jpaModel, restReq);
    }

    public String doPut(FastRestRequest restReq) {
	return getService().doPut(entityManager, jpaModel, restReq);
    }

    public String doPost(FastRestRequest restReq) {
	return getService().doPost(entityManager, jpaModel, restReq);
    }

    public String doDelete(FastRestRequest restReq) {
	return getService().doDelete(entityManager, jpaModel, restReq);
    }



}
