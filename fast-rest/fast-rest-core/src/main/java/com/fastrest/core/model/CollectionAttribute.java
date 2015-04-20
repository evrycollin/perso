package com.fastrest.core.model;

import javax.persistence.metamodel.PluralAttribute;

public class CollectionAttribute extends NavigableAttribute {


    public CollectionAttribute(Entity entity, PluralAttribute<?, ?, ?> pa) {
	super(entity, pa.getName(), pa.getElementType().getJavaType(), true);
    }

}
