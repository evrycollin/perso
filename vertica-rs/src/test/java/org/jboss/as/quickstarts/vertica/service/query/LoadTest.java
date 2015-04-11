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
					.querySql("select count(*) from test_load");

			System.out.println(res);

		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

}
