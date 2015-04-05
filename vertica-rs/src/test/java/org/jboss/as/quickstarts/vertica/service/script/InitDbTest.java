package org.jboss.as.quickstarts.vertica.service.script;

import org.jboss.as.quickstarts.vertica.service.AbstractVerticaQueryServiceTest;
import org.junit.Test;

public class InitDbTest extends AbstractVerticaQueryServiceTest {

	@Test
	public void testInitDb() {
		testScript("init-db");
	}


}
