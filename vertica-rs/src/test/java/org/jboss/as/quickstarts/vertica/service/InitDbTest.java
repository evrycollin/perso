package org.jboss.as.quickstarts.vertica.service;

import java.io.IOException;

import org.junit.Test;

public class InitDbTest extends AbstractVerticaQueryServiceTest {

	@Test
	public void testInitDb() throws ClassNotFoundException, IOException {
		testScript("init-db");
	}


}
