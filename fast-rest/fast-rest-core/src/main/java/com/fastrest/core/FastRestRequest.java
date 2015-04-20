package com.fastrest.core;

import static com.fastrest.core.util.Json.fromJson;
import static com.fastrest.core.util.Json.toJson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fastrest.core.model.Entity;
import com.fastrest.core.model.EntityInstance;

@SuppressWarnings("unchecked")
public class FastRestRequest {

    EntityInstance entityInstance;
    Object targetEntity;
    String contextPath;
    String appPath;
    List<String> path;
    String reqUri;
    Principal principal;
    String content;

    Map<String, List<String>> reqParams;
    Map<String, List<String>> headParams;

    public FastRestRequest(HttpServletRequest request) throws IOException {

	contextPath = request.getContextPath() + request.getServletPath();
	appPath = request.getScheme() + "://" + request.getServerName() + ":"
		+ request.getServerPort() + contextPath;
	reqUri = request.getRequestURI().substring(
		contextPath.length() + 1);
	path = Arrays.asList(reqUri.split("\\/"));
	principal = request.getUserPrincipal();
	reqParams = getRequestParameters(request);
	headParams = getRequestHeader(request);
	content = getRequestContent(request);
    }

    public <T> T getContent(Class<T> classOfT) {
	return fromJson(this.content, classOfT);
    }

    private String getRequestContent(HttpServletRequest request) throws IOException {
	InputStream in = request.getInputStream();
	if( in!=null ) {
	    ByteArrayOutputStream buff = new ByteArrayOutputStream();
	    byte[] tmp = new byte[1024];
	    int c=0;
	    while((c=in.read(tmp))>0)
		buff.write(tmp,0,c);
	    in.close();
		return buff.toString("UTF-8");
	}
	return null;
    }

    private Map<String, List<String>> getRequestHeader(
	    HttpServletRequest request) {
	Map<String, List<String>> headParams = new LinkedHashMap<String, List<String>>();
	Enumeration<String> en = request.getHeaderNames();
	while (en.hasMoreElements()) {
	    String pName = en.nextElement();
	    List<String> vals = new ArrayList<String>();
	    Enumeration<String> ee = request.getHeaders(pName);
	    while (ee.hasMoreElements())
		vals.add(ee.nextElement());
	    headParams.put(pName, vals);
	}
	return headParams;
    }

    private Map<String, List<String>> getRequestParameters(
	    HttpServletRequest request) {
	Map<String, List<String>> reqParams = new LinkedHashMap<String, List<String>>();
	Enumeration<String> en = request.getParameterNames();
	while (en.hasMoreElements()) {
	    String pName = en.nextElement();
	    reqParams.put(pName,
		    Arrays.asList(request.getParameterValues(pName)));
	}
	return reqParams;
    }

    public String getContextPath() {
	return contextPath;
    }

    public void setContextPath(String contextPath) {
	this.contextPath = contextPath;
    }

    public String getAppPath() {
	return appPath;
    }

    public void setAppPath(String appPath) {
	this.appPath = appPath;
    }

    public List<String> getPath() {
	return path;
    }

    public void setPath(List<String> path) {
	this.path = path;
    }

    public Principal getPrincipal() {
	return principal;
    }

    public void setPrincipal(Principal principal) {
	this.principal = principal;
    }

    public Map<String, List<String>> getReqParams() {
	return reqParams;
    }

    public void setReqParams(Map<String, List<String>> reqParams) {
	this.reqParams = reqParams;
    }

    public Map<String, List<String>> getHeadParams() {
	return headParams;
    }

    public void setHeadParams(Map<String, List<String>> headParams) {
	this.headParams = headParams;
    }
    public String getReqUri() {
	return reqUri;
    }
    public void setReqUri(String reqUri) {
	this.reqUri = reqUri;
    }

    public EntityInstance getEntityInstance() {
	return entityInstance;
    }
    public void setEntityInstance(EntityInstance entityInstance) {
	this.entityInstance = entityInstance;
    }

    @Override
    public String toString() {
        return toJson(this);
    }
    
    public Object getTargetEntity() {
	return targetEntity;
    }
    
    public void setTargetEntity(Object targetEntity) {
	this.targetEntity = targetEntity;
    }
}
