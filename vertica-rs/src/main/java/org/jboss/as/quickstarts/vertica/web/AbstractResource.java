package org.jboss.as.quickstarts.vertica.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class AbstractResource {

	final protected Logger logger = Logger.getLogger(VerticaResource.class
			.getName());

	protected Object error(String message, Throwable th) {
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
