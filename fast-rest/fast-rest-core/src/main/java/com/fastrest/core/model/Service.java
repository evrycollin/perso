package com.fastrest.core.model;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fastrest.core.config.ServiceLocator;

public class Service {

	private static final List<String> methodToSkip = new ArrayList<String>();
	static {
		methodToSkip.add("toString");
		methodToSkip.add("wait");
		methodToSkip.add("notify");
		methodToSkip.add("getClass");
		methodToSkip.add("hashCode");
		methodToSkip.add("notifyAll");
		methodToSkip.add("equals");

	}

	private String serviceName;
	private Map<String, Method> methods = new HashMap<String, Method>();

	transient private Entity entity;
	transient private Object service;

	public Service(ServiceLocator serviceLocator, Entity entity, String name,
			String methodFilter) {
		this.service = serviceLocator.getService().getService(name);
		this.entity = entity;
		this.serviceName = name;

		for (java.lang.reflect.Method method : service.getClass().getMethods()) {
			if (Modifier.isPublic(method.getModifiers())
					&& method.getName().matches(methodFilter)
					&& !methodToSkip.contains(method.getName())) {
				methods.put(method.getName(), new Method(service, this, method));
			}
		}

	}

	@Override
	public String toString() {
		return "Service [serviceName=" + serviceName + "] " + getClass();
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Object getService() {
		return service;
	}

}
