/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.vertica.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.jboss.as.quickstarts.vertica.service.VerticaQueryService;

/**
 * A JAX-RS resource for exposing REST endpoints for Vertica
 */
@Path("/")
public class VerticaResource {

	static final private Logger logger = Logger.getLogger(VerticaResource.class
			.getName());

	@GET
	@Path("query")
	@Produces({ "application/json" })
	public Object query(@Context SecurityContext context,
			@QueryParam("query") String query) {
		try {
			return VerticaQueryService.query(query);
		} catch (Throwable th) {
			return error("error", th);
		}
	}

	
	@GET
	@Path("query/{path: .*}")
	@Produces({ "application/json" })
	public Object queryRepo(@PathParam("path") String path) {
		try {
			String[] params = path.split("\\/");
			String queryId = params[0];
			String[] qParams = new String [params.length-1];
			if( params.length>1 ){
				System.arraycopy(params, 1, qParams, 0, params.length-1);
			}
			return VerticaQueryService.query(queryId, qParams);
		} catch (Throwable th) {
			return error("error", th);
		}
	}	

	@GET
	@Path("script/{script}")
	@Produces({ "application/json" })
	public Object scriptRepo(@PathParam("script") String script) {
		try {
			return VerticaQueryService.script(script);
		} catch (Throwable th) {
			return error("error", th);
		}
	}
	
	private static Object error(String message, Throwable th) {
		logger.severe(message);
		Map<String, String> res = new LinkedHashMap<String, String>();
		res.put("error", message + " " + (th != null ? th.getMessage() : ""));
		if (th != null) {
			th.printStackTrace();
			StringWriter buf = new StringWriter();
			th.printStackTrace(new PrintWriter(buf));
			res.put("stacktrace", buf.toString());
		}
		return res;
	}

}
