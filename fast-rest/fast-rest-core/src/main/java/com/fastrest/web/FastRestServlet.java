package com.fastrest.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fastrest.core.FastRestCore;
import com.fastrest.core.FastRestRequest;

/**
 * Servlet implementation class FastRestServlet
 */
public class FastRestServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    public static ServletContext context;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FastRestServlet() {
	super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	context = config.getServletContext();
	if (config.getInitParameter("com.fastrest.config") != null) {
	    FastRestCore.Instance.initializeFromJson(config
		    .getInitParameter("com.fastrest.config"));
	} else if (config.getInitParameter("com.fastrest.config.file") != null) {
	    FastRestCore.Instance.initializeFromFile(config
		    .getInitParameter("com.fastrest.config.file"));
	}

    }

    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	FastRestRequest restReq = new FastRestRequest(request);
	response.getWriter().write(FastRestCore.Instance.doGet(restReq));
	response.getWriter().close();
    }

    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	FastRestRequest restReq = new FastRestRequest(request);
	response.getWriter().write(FastRestCore.Instance.doPost(restReq));
	response.getWriter().close();
    }

    protected void doPut(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	FastRestRequest restReq = new FastRestRequest(request);
	response.getWriter().write(FastRestCore.Instance.doPut(restReq));
	response.getWriter().close();
    }

    protected void doDelete(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	FastRestRequest restReq = new FastRestRequest(request);
	response.getWriter().write(FastRestCore.Instance.doDelete(restReq));
	response.getWriter().close();
    }
}
