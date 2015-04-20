package com.fastrest.core;

import static com.fastrest.core.util.Json.fromJson;
import static com.fastrest.core.util.Json.toJson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import com.fastrest.core.config.Config;
import com.fastrest.core.model.Entity;
import com.fastrest.core.model.JpaModel;
import com.fastrest.core.util.Cypher;
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

	Cypher.setIdCyphering(config.isCypherIds());

	this.entityManager = currentConfig.getServiceLocator().getService().getEntityManager();
	
	// load JpaModel
	this.jpaModel = new JpaModel(this.entityManager);
	
	currentConfig.setJpaModel(jpaModel);

	for (Entity entity : jpaModel.getEntities()) {
	    Json.GsonBuilder.registerTypeAdapter(entity.getType(),
		    new Json.EntityObjectAdapter(jpaModel));
	}

	logger.info("Entity Manager : "
		+ (this.entityManager != null ? entityManager.toString()
			: "NULL"));
	logger.info("Loaded configuration : \n" + currentConfig);

    }

    FastCoreService getService() {
	return currentConfig.getServiceLocator().getService();
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

    public String getConfig() {	
	return toJson(currentConfig);
    }

}
