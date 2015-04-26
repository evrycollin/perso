package com.fastrest.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import com.fastrest.core.config.Config;
import com.fastrest.core.config.ServiceLocator;

public class Entity {

	transient private Logger logger = Logger
			.getLogger(JpaModel.class.getName());

	transient private JpaModel jpaModel;

	private String name;
	private Map<String, Field> fields = new HashMap<String, Field>();
	private List<Service> services = new ArrayList<Service>();
	private Class<?> type;
	private Field id;

	public Entity(Config config, ServiceLocator serviceLocator,
			JpaModel jpaModel, EntityType<?> entityType) {
		this.jpaModel = jpaModel;
		this.type = entityType.getJavaType();
		this.name = entityType.getName();
		EntityManager em = serviceLocator.getService().getEntityManager();
		for (Attribute<?, ?> att : entityType.getDeclaredAttributes()) {
			if (att instanceof PluralAttribute<?, ?, ?>) {
				PluralAttribute<?, ?, ?> pa = (PluralAttribute<?, ?, ?>) att;
				fields.put(att.getName(), new CollectionAttribute(this, pa));

			} else if (att instanceof SingularAttribute<?, ?>) {
				SingularAttribute<?, ?> sa = ((SingularAttribute<?, ?>) att);
				if (sa.isId()) {
					fields.put(att.getName(), id = new IdAttribute(this, sa));
				} else {
					try {
						if (em.getEntityManagerFactory().getMetamodel()
								.managedType(sa.getJavaType()) != null) {
							fields.put(att.getName(), new EntityAttribute(this,
									sa));
						} else {
							fields.put(att.getName(), new SimpleAttribute(this,
									sa));
						}

					} catch (Throwable th) {
						fields.put(att.getName(), new SimpleAttribute(this, sa));
					}

				}
			} else {
				logger.warning("Cannot process field " + att.getName()
						+ " for entity " + entityType.getJavaType());
			}
			if (id != null)
				fields.remove(id.getName());
		}

		// load services
		if (config.getEntityBehavior().containsKey(name)) {
			for (String serviceName : config.getEntityBehavior().get(name)
					.keySet()) {

				String methodFilter = config.getEntityBehavior().get(name)
						.get(serviceName);

				services.add(new Service(serviceLocator, this, serviceName,
						methodFilter));

			}
		}

	}

	public JpaModel getJpaModel() {
		return jpaModel;
	}

	public void setJpaModel(JpaModel jpaModel) {
		this.jpaModel = jpaModel;
	}

	public Map<String, Field> getFields() {
		return fields;
	}

	public void setFields(Map<String, Field> fields) {
		this.fields = fields;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Field getId() {
		return id;
	}

	public void setId(Field id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		String res = "Entity [type=" + type + ", fields=\n";
		for (Field f : fields.values())
			res += f + "\n";
		res += "]";
		return res;
	}

	public Object getIdFromString(String idStr) {
		Class<?> type = ((SimpleAttribute) getId()).getType();
		if (Integer.class.equals(type) || "int".equals(type.getName())) {
			return new Integer(idStr);
		} else if (Long.class.equals(type) || "long".equals(type.getName())) {
			return new Long(idStr);
		} else if (String.class.equals(type)) {
			return idStr;
		}
		return null;
	}

}
