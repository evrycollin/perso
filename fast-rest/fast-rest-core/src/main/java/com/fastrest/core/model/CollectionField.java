package com.fastrest.core.model;

import javax.persistence.metamodel.PluralAttribute;

public class CollectionField extends NavigableField {

	public CollectionField(Entity entity, PluralAttribute<?, ?, ?> pa) {
		super(entity, pa.getName(), pa.getElementType().getJavaType(), true);
	}

}
