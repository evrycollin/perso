package org.jboss.as.quickstarts.vertica.service;

import java.io.IOException;

import org.junit.Test;

public class VerticaQueryUtilTest {

	@Test
	public void testCust() throws ClassNotFoundException, IOException {

		String queryId = "cust";

		System.out.println(VerticaQueryUtil.query(
				VerticaQueryUtil.getQuerySQL(queryId), new String[] {},
				VerticaQueryUtil.getQueryParamTypes(queryId)));
		
	}

	@Test
	public void testCustById() throws ClassNotFoundException, IOException {

		String queryId = "custById";

		System.out.println(VerticaQueryUtil.query(
				VerticaQueryUtil.getQuerySQL(queryId), new String[] {"1"},
				VerticaQueryUtil.getQueryParamTypes(queryId)));
		
	}

	@Test
	public void testCustByName() throws ClassNotFoundException, IOException {

		String queryId = "custByName";

		System.out.println(VerticaQueryUtil.query(
				VerticaQueryUtil.getQuerySQL(queryId), new String[] {"Dodd"},
				VerticaQueryUtil.getQueryParamTypes(queryId)));
		
	}


}
