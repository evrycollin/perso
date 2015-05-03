package com.fastrest.core.model;

import java.lang.annotation.Annotation;

import com.fastrest.core.EntityParam;

public class Method {

	private String methodName;
	private Class<?> returnType;
	private Class<?>[] parameters;
	private int entityParameterIdx = -1;
	private boolean get=false;
	private boolean putOrPost=false;
	

	transient private Service service;
	transient private Object serviceInstance;
	transient private java.lang.reflect.Method method;

	public Method(Object serviceInstance, Service service,
			java.lang.reflect.Method method) {
		this.serviceInstance = serviceInstance;
		this.service = service;
		this.methodName = method.getName();
		this.method = method;

		this.returnType = method.getReturnType();
		this.parameters = method.getParameterTypes();

		Annotation[][] annotations = method.getParameterAnnotations();
		for (int i = 0; i < parameters.length; i++) {
			for (int j = 0; j < annotations[i].length; j++) {
				if (EntityParam.class
						.equals(annotations[i][j].annotationType())) {
					entityParameterIdx = i;
					putOrPost = true;					
				}
			}
		}
		if( !putOrPost ) {
			get=true;
		}
	}

	@Override
	public String toString() {
		return "Method [methodName=" + methodName + "] " + getClass();
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Service getService() {
		return service;
	}

	public Object getServiceInstance() {
		return serviceInstance;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public int getEntityParameterIdx() {
		return entityParameterIdx;
	}

	public Class<?>[] getParameters() {
		return parameters;
	}

	public java.lang.reflect.Method getMethod() {
		return method;
	}
	
	public boolean isGet() {
		return get;
	}
	
	public boolean isPutOrPost() {
		return putOrPost;
	}
}
