package com.fastrest.core.config.impl;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.fastrest.core.FastCoreService;
import com.fastrest.core.config.ServiceLocator;

public class J2eeEntityManagerProvider implements ServiceLocator {

    String fastRestServiceJndi = "java:module/J2eeFastRestCoreBean";

    public FastCoreService getService() {

	try {
	    return (FastCoreService) new InitialContext()
		    .lookup(fastRestServiceJndi);
	} catch (NamingException e) {
	    throw new RuntimeException(e);
	}

    }

    public String getFastRestServiceJndi() {
	return fastRestServiceJndi;
    }

    public void setFastRestServiceJndi(String fastRestServiceJndi) {
	this.fastRestServiceJndi = fastRestServiceJndi;
    }

}
