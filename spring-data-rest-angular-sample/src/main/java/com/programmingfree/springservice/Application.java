package com.programmingfree.springservice;

import java.net.URI;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@Configuration
@ComponentScan
@EnableJpaRepositories
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
@PropertySource("application.properties")
public class Application {
    
    @Configuration
    @Import(RepositoryRestMvcConfiguration.class)
    public static class RestConfiguration extends RepositoryRestMvcConfiguration {
	
	private Logger logger = Logger.getLogger(RestConfiguration.class.getName());
	
	@Value("${rest.version:}")
	private String version;
	
	@Value("${rest.baseurl:}")
	private String basePath;

	@Override
	protected void configureRepositoryRestConfiguration(
		RepositoryRestConfiguration config) {
	    super.configureRepositoryRestConfiguration(config);
	    URI baseUri = URI.create(basePath+"/"+version);
	    logger.info("Set base uri for rest to : "+baseUri);
	    config.setBaseUri(baseUri);
	}
    }

    public static void main(String[] args) {
	SpringApplication.run(Application.class, args);
    }

}