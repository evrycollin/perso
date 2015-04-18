package com.programmingfree.springservice.service;

import java.lang.reflect.InvocationTargetException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class GenericService {

    boolean crypt = false;

    @Inject
    EntityManager em;

    public Object get(String entityName, String id)
	    throws IllegalAccessException, InvocationTargetException,
	    NoSuchMethodException {
	return em.find(getEntityType(entityName).getJavaType(),
		getPrimaryKey(entityName, id));
    }

    public Object getPrimaryKey(String entityName, String idStr) {
	EntityType<?> entity = getEntityType(entityName);
	Class<?> type = entity.getIdType().getJavaType();
	if (String.class.getName().equals(type.getName())) {
	    return idStr;
	} else if ("int".equals(type.getName())) {
	    return new Integer(idStr);
	} else if ("long".equals(type.getName())) {
	    return new Long(idStr);
	} else {
	    throw new RuntimeException("type note supported for id search : "
		    + type);
	}
    }

    public EntityType<?> getEntityType(String entityName) {
	Metamodel jpa = em.getEntityManagerFactory().getMetamodel();
	// build map type <> entityMeta
	for (EntityType<?> entity : jpa.getEntities()) {
	    if (entityName.toLowerCase().equals(entity.getName().toLowerCase())) {
		return entity;
	    }
	}
	return null;

    }

    public String getEntityIdString(Object entity) {
	PersistenceUnitUtil h = em.getEntityManagerFactory()
		.getPersistenceUnitUtil();
	return h.getIdentifier(entity).toString();
    }

    public Map<String, Object> describeEntity(Principal principal,
	    Object entity, String currentPath) throws Exception {
	return describeEntity(principal, entity, currentPath, true);
    }

    public Map<String, Object> describeEntity(Principal principal,
	    Object entity, String currentPath, boolean fromField)
	    throws Exception {
	Map<String, Object> res = new TreeMap<String, Object>(
		BeanUtils.describe(entity));
	res.remove("class");

	// manage collection links
	EntityType<?> entityType = getEntityType(entity.getClass()
		.getSimpleName());

	// manage oneToOne relationships
	for (Attribute<?, ?> att : entityType.getDeclaredAttributes()) {
	    if (att instanceof PluralAttribute<?, ?, ?>) {
		PluralAttribute<?, ?, ?> pa = (PluralAttribute<?, ?, ?>) att;
		String entityName = pa.getName();
		String id = getEntityIdString(entity);
		Map<String, Map<String,String>> self = new TreeMap<>();
		Map<String, String> link = new TreeMap<>();
		self.put("_self", link);
		link.put(
			"link",
			currentPath
				+ "/"
				+ (fromField ? "" : maskId(principal,
					entity.getClass(), id)
					+ "/") + entityName.toLowerCase());
		res.put(pa.getName(), self);
	    } else if (att instanceof SingularAttribute<?, ?>) {

		SingularAttribute<?, ?> sa = ((SingularAttribute<?, ?>) att);

		if (sa.isId()) {
		    String id = getEntityIdString(entity);		    
		    if( crypt ) {
			id = maskId(principal,entity.getClass(),id ); 
			res.put(att.getName(), id );
		    }
		    Map<String, String> self = new TreeMap<>();
		    res.put( "_self", self);
		    self.put("link",
			    currentPath
				    + (fromField ? "" : "/"
					    + id));

		} else {

		    EntityType<?> fieldEntityType = getEntityType(att
			    .getJavaType().getSimpleName());
		    if (fieldEntityType != null) {
			String id = getEntityIdString(entity);
			Map<String, Map<String,String>> self = new TreeMap<>();
			Map<String, String> link = new TreeMap<>();
			self.put("_self", link);			
			link.put(
				"link",
				currentPath
					+ "/"
					+ (fromField ? "" : maskId(principal,
						entity.getClass(), id) + "/")
					+ att.getName());
			res.put(att.getName(), self);
		    }
		}
	    }
	}

	return res;
    }

    public String maskId(Principal principal, Class<?> type, String id)
	    throws Exception {
	return crypt ? encrypt((principal != null ? principal.getName()
		: "ANONYMOUS") + ":" + type.getSimpleName() + ":" + id) : id;
    }

    public String unmaskId(String type, String encryptedId) throws Exception {
	if (!crypt) {
	    return encryptedId;
	}
	String decrypter = decrypt(encryptedId);
	return decrypter.substring(decrypter.lastIndexOf(':') + 1);

    }

    public String encrypt(String valueToEnc) throws Exception {
	Cipher c = Cipher.getInstance(ALGORITHM);
	c.init(Cipher.ENCRYPT_MODE, key);
	byte[] encValue = c.doFinal(valueToEnc.getBytes());
	String encryptedValue = Base64.encodeBase64String(encValue);
	encryptedValue = encryptedValue.replace('/', '_').trim();
	return encryptedValue;
    }

    public String decrypt(String encryptedValue) throws Exception {
	Cipher c = Cipher.getInstance(ALGORITHM);
	c.init(Cipher.DECRYPT_MODE, key);
	byte[] decordedValue = Base64.decodeBase64(encryptedValue.replace('_',
		'/'));
	byte[] decValue = c.doFinal(decordedValue);
	String decryptedValue = new String(decValue);
	return decryptedValue;
    }

    private static final String ALGORITHM = "DES";
    static Key key;
    static {
	try {
	    KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
	    keyGen.init(new SecureRandom());
	    key = keyGen.generateKey();
	} catch (NoSuchAlgorithmException e) {
	    throw new RuntimeException(e);
	}
    }

}
