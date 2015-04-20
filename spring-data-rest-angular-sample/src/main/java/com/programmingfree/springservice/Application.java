package com.programmingfree.springservice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fastrest.web.FastRestServlet;

@Configuration
@ComponentScan({ "com.programmingfree.springservice", "com.fastrest" })
@EnableJpaRepositories
@EnableAutoConfiguration
@PropertySource("application.properties")
public class Application {


    @Bean
    public ServletRegistrationBean fastRestServletRegistration() {
	ServletRegistrationBean registration = new ServletRegistrationBean(
		new FastRestServlet());
	Map<String, String> params = new HashMap<String, String>();
	params.put("com.fastrest.config.file", "fast-rest-config.json");
	registration.setUrlMappings(Arrays
		.asList(new String[] { "/fastrest/*" }));
	registration.setInitParameters(params);
	registration.setLoadOnStartup(2);
	return registration;
    }


    
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
	return new TomcatEmbeddedServletContainerFactory() {

	    @Override
	    protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
		    Tomcat tomcat) {
		tomcat.enableNaming();
		return super.getTomcatEmbeddedServletContainer(tomcat);
	    }
	};
    }

    public static void main(String[] args) {
	SpringApplication.run(Application.class, args);
    }

}