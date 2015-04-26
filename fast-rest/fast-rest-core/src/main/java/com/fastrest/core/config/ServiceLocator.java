package com.fastrest.core.config;

import com.fastrest.core.FastCoreService;

public interface ServiceLocator {

	FastCoreService getService();
}
