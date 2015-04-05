package org.jboss.as.quickstarts.vertica.service;

import java.io.IOException;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

public class VerticaQueryUtilTest {

	@Test
	public void testCust() throws ClassNotFoundException, IOException {
		testQuery("cust", null);
	}

	@Test
	public void testCustById() throws ClassNotFoundException, IOException {
		testQuery("custById", new String[] { "1" });
	}

	@Test
	public void testCustByName() throws ClassNotFoundException, IOException {
		testQuery("custByName", new String[] { "Dodd" });
	}

	private void testQuery(String queryId, String[] params)
			throws ClassNotFoundException, IOException {

		Collection res = (Collection) VerticaQueryUtil.query(
				VerticaQueryUtil.getQuerySQL(queryId), params,
				VerticaQueryUtil.getQueryParamTypes(queryId));

		Assert.assertNotNull("nothing found for query " + queryId, res);
		Assert.assertTrue("nothing found for query " + queryId, res.size() > 0);
	}

}
