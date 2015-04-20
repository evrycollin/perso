package com.fastrest.core.model;

public abstract class NavigableAttribute extends Field {

    private Class<?> targetType;
    private boolean isMultiple;

    public NavigableAttribute(Entity entity, String name, Class<?> targetType,
	    boolean isMultiple) {
	super(entity, name);
	this.targetType = targetType;
	this.isMultiple = isMultiple;
    }

    public Class<?> getTargetType() {
	return targetType;
    }

    public void setTargetType(Class<?> targetType) {
	this.targetType = targetType;
    }

    public boolean isMultiple() {
	return isMultiple;
    }

    public void setMultiple(boolean isMultiple) {
	this.isMultiple = isMultiple;
    }

    @Override
    public String toString() {
	return "NavigableAttribute [targetType=" + targetType + ", isMultiple="
		+ isMultiple + "]";
    }

}
