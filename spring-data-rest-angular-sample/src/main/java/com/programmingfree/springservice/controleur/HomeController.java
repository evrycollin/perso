package com.programmingfree.springservice.controleur;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
