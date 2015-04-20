package com.fastrest.core.config.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fastrest.core.FastCoreService;
import com.fastrest.core.config.ServiceLocator;
import com.fastrest.web.FastRestServlet;

public class SpringEntityManagerProvider implements ServiceLocator {

    
    public FastCoreService getService() {
	
	ApplicationContext context = WebApplicationContextUtils
		.getWebApplicationContext(FastRestServlet.context);
	
        return (FastCoreService)context.getBean("fastRestCoreService");
    }

}
