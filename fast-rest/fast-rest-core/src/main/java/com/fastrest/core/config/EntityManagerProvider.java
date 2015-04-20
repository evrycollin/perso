package com.fastrest.core.config;

import javax.persistence.EntityManager;

import com.fastrest.core.FastCoreService;

public interface EntityManagerProvider {
    String getName();
    
    EntityManager getEntityManager();
    
    FastCoreService getService();

  

}
