package com.fastrest.core.config.impl;

import javax.persistence.EntityManager;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fastrest.core.FastCoreService;
import com.fastrest.core.config.EntityManagerProvider;
import com.fastrest.web.FastRestServlet;

public class SpringEntityManagerProvider implements EntityManagerProvider {

    String name = "SpringEntityManager";


    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return "SpringEntityManager [name=" + name + "]";
    }

    public EntityManager getEntityManager() {

	ApplicationContext context = WebApplicationContextUtils
		.getWebApplicationContext(FastRestServlet.context);
	return context.getBean(EntityManager.class);
    }
    
    public FastCoreService getService() {
	
	ApplicationContext context = WebApplicationContextUtils
		.getWebApplicationContext(FastRestServlet.context);
	
        // TODO Auto-generated method stub
        return (FastCoreService)context.getBean("fastRestCoreService");
    }

}
