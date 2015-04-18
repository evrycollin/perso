package com.programmingfree.springservice.rest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.PluralAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.programmingfree.springservice.domain.User;
import com.programmingfree.springservice.service.GenericService;

@RestController
public class GenericRestControleur {


    @Inject
    GenericService genericService;

    static final String BASE = "/generic";

    @RequestMapping(value = BASE + "/login/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void login(HttpServletRequest request, HttpServletResponse response,
	    @PathVariable("id") String id) throws Exception {

	String redirect = request.getScheme() + "://" + request.getServerName()
		+ ":" + request.getServerPort() + BASE;

	response.sendRedirect(redirect + "/user/"
		+ genericService.maskId(request.getUserPrincipal(), User.class, id));

    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = BASE + "/**", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object get(HttpServletRequest request) throws Exception,
	    SecurityException, IllegalAccessException,
	    IllegalArgumentException, InvocationTargetException {
	String requestUri = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ request.getRequestURI();

	List<String> pathElmts = new LinkedList<>(Arrays.asList(request
		.getRequestURI().split("\\/")));
	pathElmts.remove(0);
	pathElmts.remove(0);

	String name = null;
	String id = null;
	Object lastEntity = null;
	while (pathElmts.size() >= 2) {
	    name = pathElmts.remove(0).toLowerCase();
	    id = pathElmts.remove(0);
	    if (lastEntity != null) {
		EntityType<?> previousEntity = genericService
			.getEntityType(lastEntity.getClass().getSimpleName());

		for (PluralAttribute<?, ?, ?> pa : previousEntity
			.getDeclaredPluralAttributes()) {
		    if (pa.getName().toLowerCase().equals(name)) {
			String entityName = pa.getBindableJavaType()
				.getSimpleName();
			lastEntity = genericService.get(entityName,
				genericService.unmaskId(entityName, id));
			break;
		    }
		}

	    } else {
		lastEntity = genericService.get(name, genericService.unmaskId(name, id));
	    }
	}
	if (pathElmts.size() > 0) {
	    // return attribute
	    String fieldName = pathElmts.remove(0);
	    String methodeName = "get"
		    + fieldName.substring(0, 1).toUpperCase()
		    + fieldName.substring(1);
	    Method method = lastEntity.getClass().getMethod(methodeName);

	    Object methodRes = method.invoke(lastEntity);
	    if (methodRes != null && methodRes instanceof Collection) {
		Collection<Object> resEntities = (Collection<Object>) methodRes;
		List<Object> res = new ArrayList<>();
		for (Object o : resEntities) {
		    res.add(genericService.describeEntity(request.getUserPrincipal(), o,
			    requestUri, false));
		}
		return res;
	    } else {
		return genericService.describeEntity(request.getUserPrincipal(), methodRes,
			requestUri);
	    }
	}

	return genericService.describeEntity(request.getUserPrincipal(), lastEntity,
		requestUri);
    }

 


 

}
