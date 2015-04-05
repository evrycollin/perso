package org.jboss.as.quickstarts.vertica.service;

import java.io.IOException;
import java.util.Collection;

import org.junit.Assert;

public abstract class AbstractVerticaQueryServiceTest {

	protected void testQuery(String queryId, String[] params)
			throws ClassNotFoundException, IOException {

		Collection<Object> res = (Collection<Object>) VerticaQueryService
				.query(queryId, params);

		Assert.assertNotNull("nothing found for query " + queryId, res);
		Assert.assertTrue("nothing found for query " + queryId, res.size() > 0);
		System.out.println(res);
	}

}
