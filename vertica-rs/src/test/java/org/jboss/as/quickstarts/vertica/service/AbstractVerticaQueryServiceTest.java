package org.jboss.as.quickstarts.vertica.service;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;

public abstract class AbstractVerticaQueryServiceTest {

	protected void testQuery(String queryId, String[] params) {

		try {
			Collection<Object> res = (Collection<Object>) VerticaQueryService
					.query(queryId, params);

			Assert.assertNotNull("nothing found for query " + queryId, res);
			Assert.assertTrue("nothing found for query " + queryId,
					res.size() > 0);
			System.out.println(res);

		} catch (Throwable th) {
			th.printStackTrace();
			Assert.fail("error when executing query " + queryId
					+ " with parameters "
					+ (params != null ? Arrays.asList(params) : "") + " : "
					+ th.getMessage());
		}
	}

	protected void testScript(String scriptId) {

		Assert.assertTrue("script with error",
				VerticaQueryService.script(scriptId));
	}

}
