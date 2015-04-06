package org.jboss.as.quickstarts.vertica.service.query;

import java.util.Collection;

import org.jboss.as.quickstarts.vertica.service.AbstractVerticaQueryServiceTest;
import org.jboss.as.quickstarts.vertica.service.VerticaQueryService;
import org.junit.Test;

public class LoadTest extends AbstractVerticaQueryServiceTest {

	@Test
	public void testCust() {
		try {
			Collection<Object> res = (Collection<Object>) VerticaQueryService
					.query("select count(*) from test_load", null, null);

			System.out.println(res);

		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

}
