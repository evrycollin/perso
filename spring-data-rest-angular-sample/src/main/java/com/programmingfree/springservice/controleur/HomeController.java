package com.programmingfree.springservice.controleur;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fastrest.core.util.Cypher;
import com.programmingfree.springservice.domain.User;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home() {
	return "index";
    }

    @RequestMapping("/test")
    public String test() {
	return "test";
    }

    @RequestMapping(value = "/login/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void login(HttpServletRequest request, HttpServletResponse response,
	    @PathVariable("id") String id) throws Exception {

	String redirect = request.getScheme() + "://" + request.getServerName()
		+ ":" + request.getServerPort() + "/fastrest";

	response.sendRedirect(redirect
		+ "/User/"
		+ Cypher.maskId(request.getUserPrincipal(), User.class,
			id));

    }
}
