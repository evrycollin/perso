package org.jboss.as.quickstarts.vertica.service;

import java.io.IOException;

import org.junit.Test;

public class CustomerTest extends AbstractVerticaQueryServiceTest {

	@Test
	public void testCust() throws ClassNotFoundException, IOException {
		testQuery("cust.all", null);
	}

	@Test
	public void testCustById() throws ClassNotFoundException, IOException {
		testQuery("cust.byId", new String[] { "1" });
	}

	@Test
	public void testCustByName() throws ClassNotFoundException, IOException {
		testQuery("cust.byName", new String[] { "Dodd" });
	}

}
