package com.fastrest.core.model;

import javax.persistence.metamodel.SingularAttribute;

public class IdAttribute extends SimpleField {

	public IdAttribute(Entity entity, SingularAttribute<?, ?> sa) {
		super(entity, sa);
	}

}
