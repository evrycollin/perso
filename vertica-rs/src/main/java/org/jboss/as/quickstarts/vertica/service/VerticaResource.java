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
package org.jboss.as.quickstarts.vertica.service;

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
import javax.ws.rs.core.UriInfo;

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
			return VerticaQueryUtil.query(query, null, null);
		} catch (Throwable th) {
			return error("error", th);
		}
	}

	@GET
	@Path("repo/{queryId}")
	@Produces({ "application/json" })
	public Object query1p(@Context UriInfo ui,
			@PathParam("queryId") String queryId) {
		try {
			return VerticaQueryUtil.query(queryId, new String[] {});
		} catch (Throwable th) {
			return error("error", th);
		}
	}

	@GET
	@Path("repo/{queryId}/{param1}")
	@Produces({ "application/json" })
	public Object query1p(@Context UriInfo ui,
			@PathParam("queryId") String queryId,
			@PathParam("param1") String param1) {
		try {
			return VerticaQueryUtil.query(queryId, new String[] { param1 });
		} catch (Throwable th) {
			return error("error", th);
		}
	}

	@GET
	@Path("repo/{queryId}/{param1}}/{param2}")
	@Produces({ "application/json" })
	public Object query2p(@Context UriInfo ui,
			@PathParam("queryId") String queryId,
			@PathParam("param1") String param1,
			@PathParam("param2") String param2) {
		try {
			return VerticaQueryUtil.query(queryId, new String[] { param1,
					param2 });
		} catch (Throwable th) {
			return error("error", th);
		}
	}

	@GET
	@Path("repo/{queryId}/{param1}}/{param2}/{param3}")
	@Produces({ "application/json" })
	public Object query3p(@Context UriInfo ui,
			@PathParam("queryId") String queryId,
			@PathParam("param1") String param1,
			@PathParam("param2") String param2,
			@PathParam("param3") String param3) {
		try {
			return VerticaQueryUtil.query(queryId, new String[] { param1,
					param2, param3 });
		} catch (Throwable th) {
			return error("error", th);
		}
	}

	private static Object error(String message, Throwable th) {
		logger.severe(message);
		Map res = new LinkedHashMap();
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
