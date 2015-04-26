package com.fastrest.core.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.logging.Logger;

import com.fastrest.core.FastRestRequest;
import com.fastrest.core.config.ServiceLocator;
import com.fastrest.core.model.Entity;
import com.fastrest.core.model.EntityAttribute;
import com.fastrest.core.model.EntityInstance;
import com.fastrest.core.model.Field;
import com.fastrest.core.model.JpaModel;
import com.fastrest.core.model.NavigableAttribute;
import com.fastrest.core.model.SimpleAttribute;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class Json {

    public static final GsonBuilder GsonBuilder = new GsonBuilder();
    static {
	GsonBuilder.setPrettyPrinting();
	GsonBuilder.registerTypeAdapter(Class.class, new ClassGsonAdapter());
	GsonBuilder.registerTypeAdapter(ServiceLocator.class,
		new GsonObjectAdapter());
    }

    public static <T> T fromJson(InputStream in, Class<T> classOfT) {
	return GsonBuilder.create().fromJson(new InputStreamReader(in),
		classOfT);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
	return GsonBuilder.create().fromJson(json, classOfT);
    }

    public static String toJson(Object object) {
	return GsonBuilder.create().toJson(object);
    }

    static class GsonObjectAdapter implements JsonSerializer<Object>,
	    JsonDeserializer<Object> {
	public Object deserialize(JsonElement json, Type typeOfT,
		JsonDeserializationContext context) throws JsonParseException {

	    JsonObject jsonObject = json.getAsJsonObject();
	    JsonPrimitive prim = (JsonPrimitive) jsonObject.remove("_class");
	    String className = prim.getAsString();
	    Class<?> klass = null;
	    try {
		klass = Class.forName(className);
	    } catch (ClassNotFoundException e) {
		e.printStackTrace();
		throw new JsonParseException(e.getMessage());
	    }

	    return context.deserialize(jsonObject, klass);
	}

	public JsonElement serialize(Object src, Type typeOfSrc,
		JsonSerializationContext context) {
	    JsonObject res = (JsonObject) context.serialize(src);
	    res.addProperty("_class", src.getClass().getName());

	    return res;
	}

    }

    static public class EntityObjectAdapter implements JsonSerializer<Object>,
	    JsonDeserializer<Object> {

	private Logger logger = Logger.getLogger(EntityObjectAdapter.class
		.getName());

	public static ThreadLocal<FastRestRequest> threadLocal = new ThreadLocal<FastRestRequest>();

	JpaModel jpaModel;

	public EntityObjectAdapter(JpaModel jpaModel) {
	    this.jpaModel = jpaModel;

	}

	public JsonElement serialize(Object src, Type typeOfSrc,
		JsonSerializationContext context) {

	    JsonObject jo = new JsonObject();
	    Entity entity = jpaModel.getEntityByType(src.getClass());

	    for (Field f : entity.getFields().values()) {

		if (f instanceof SimpleAttribute) {
		    Object value = f.get(src);
		    if (value != null && value instanceof Number) {
			jo.add(f.getName(), new JsonPrimitive((Number) value));
		    } else if (value != null) {
			jo.add(f.getName(), context.serialize(value));
		    }
		} else if (f instanceof NavigableAttribute) {
		    JsonObject link = new JsonObject();
		    String url = threadLocal.get().getAppPath() + "/"
			    + threadLocal.get().getReqUri();
		    String id = Cypher.maskId(threadLocal.get().getPrincipal(),
			    entity.getType(),
			    entity.getId().get(src).toString()).toString();

		    if (!url.endsWith(id)) {
			url += "/" + id;
		    }
		    url += "/" + f.getName();
		    link.add("_link", new JsonPrimitive(url));
		    if (f instanceof EntityAttribute) {
			Object fieldEntity = f.get(src);
			if (fieldEntity != null) {
			    Entity fieldEntityModel = jpaModel
				    .getEntityByType(fieldEntity.getClass());
			    Object idEntity = Cypher.maskId(threadLocal.get()
				    .getPrincipal(),
				    fieldEntityModel.getType(),
				    fieldEntityModel.getId().get(fieldEntity));

			    if (idEntity instanceof Number) {
				link.add(fieldEntityModel.getId().getName(),
					new JsonPrimitive((Number) idEntity));
			    } else {
				link.add(fieldEntityModel.getId().getName(),
					new JsonPrimitive(idEntity.toString()));
			    }

			}
		    }
		    jo.add(f.getName(), link);
		}

	    }
	    if (entity.getId() != null) {
		Object value = Cypher.maskId(threadLocal.get().getPrincipal(),
			entity.getType(), entity.getId().get(src).toString());
		if (value != null && value instanceof Number) {
		    jo.add(entity.getId().getName(), new JsonPrimitive(
			    (Number) value));
		} else if (value != null) {
		    jo.add(entity.getId().getName(),
			    new JsonPrimitive(value.toString()));
		}
	    }

	    return jo;
	}

	public Object deserialize(JsonElement json, Type typeOfT,
		JsonDeserializationContext context) throws JsonParseException {

	    JsonObject jsonObject = json.getAsJsonObject();
	    try {
		EntityInstance entityInst = threadLocal.get()
			.getEntityInstance();
		Object entity = null;
		if (entityInst.getInstanceId() != null) {
		    // update
		    entity = threadLocal.get().getTargetEntity();
		} else {
		    // create
		    entity = entityInst.getEntity().getType().newInstance();
		}
		for (Field field : entityInst.getEntity().getFields().values()) {
		    if (field instanceof SimpleAttribute) {
			SimpleAttribute sa = (SimpleAttribute) field;
			JsonElement elmt = jsonObject.get(field.getName());
			if (elmt != null) {
			    if (sa.getType().equals(Integer.class)
				    || sa.getType().equals(int.class)) {
				sa.set(entity, elmt.getAsInt());
			    } else if (sa.getType().equals(Long.class)
				    || sa.getType().equals(long.class)) {
				sa.set(entity, elmt.getAsBigInteger());
			    } else if (sa.getType().equals(Boolean.class)
				    || sa.getType().equals(boolean.class)) {
				sa.set(entity, elmt.getAsBoolean());
			    } else if (sa.getType().equals(String.class)) {
				sa.set(entity, elmt.getAsString());
			    } else if (sa.getType().equals(Boolean.class)
				    || sa.getType().equals(boolean.class)) {
				sa.set(entity, elmt.getAsBoolean());
			    } else {
				logger.warning("Skiping not supported type : "
					+ sa.getType() + " for field "
					+ entityInst.getEntity().getName()
					+ "." + sa.getName());
			    }
			}

		    } else if (field instanceof EntityAttribute) {
			EntityAttribute ea = (EntityAttribute) field;
			JsonObject link = jsonObject.getAsJsonObject(field
				.getName());
			if (link != null) {
			    Entity targetEntity = jpaModel.getEntityByType(ea
				    .getTargetType());
			    SimpleAttribute targetIdField = (SimpleAttribute) targetEntity
				    .getId();

			    if (link.get(targetIdField.getName()) != null) {

				String targetId = Cypher.unmaskId(targetEntity
					.getName(),
					link.get(targetIdField.getName())
						.getAsString());

				Object targetIdValue = null;
				if (targetIdField.getType().equals(
					Integer.class)
					|| targetIdField.getType().equals(
						int.class)) {
				    targetIdValue = new Integer(targetId);
				} else if (targetIdField.getType().equals(
					String.class)) {
				    targetIdValue = targetId;
				}
				if (targetIdValue != null) {
				    Object targetEntityObj = jpaModel
					    .getEntityManager().find(
						    targetEntity.getType(),
						    targetIdValue);
				    ea.set(entity, targetEntityObj);
				}
			    }

			}
		    }
		}
		return entity;

	    } catch (Throwable th) {
		th.printStackTrace();
	    }

	    // TODO Auto-generated method stub
	    return null;
	}
    }

    static class ClassGsonAdapter implements JsonSerializer<Class<?>>,
	    JsonDeserializer<Object> {
	public Class<?> deserialize(JsonElement json, Type typeOfT,
		JsonDeserializationContext context) throws JsonParseException {
	    try {
		return Class.forName(json.getAsString());
	    } catch (ClassNotFoundException e) {
		e.printStackTrace();
	    }
	    return null;
	}

	public JsonElement serialize(Class<?> src, Type typeOfSrc,
		JsonSerializationContext context) {
		
		if( src.isArray() ) {
			return context.serialize("["+src.getComponentType().getName()+"]");
		} else if( Collection.class.isAssignableFrom( src )) {
			String type = Object.class.toString();
			if( typeOfSrc instanceof ParameterizedType ) {
				ParameterizedType pt = (ParameterizedType) typeOfSrc;
				for (Type t : pt.getActualTypeArguments()) {
	                type = t.toString();
	                break;
	            }
			}
			return context.serialize(src.getName()+"[" +type+"]");
			
		}
		else {
			return context.serialize(src.getName());
		    			
		}
		
	}

    }

}
