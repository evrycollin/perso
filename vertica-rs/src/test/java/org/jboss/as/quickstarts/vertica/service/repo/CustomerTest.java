package org.jboss.as.quickstarts.vertica.service.repo;

import org.jboss.as.quickstarts.vertica.service.AbstractVerticaQueryServiceTest;
import org.junit.Test;

public class CustomerTest extends AbstractVerticaQueryServiceTest {

	@Test
	public void testCust() {
		testQuery("cust.all", null);
	}

	@Test
	public void testCustById() {
		testQuery("cust.byId", new String[] { "1" });
	}

	@Test
	public void testCustByName() {
		testQuery("cust.byName", new String[] { "Bill" });
	}

}
