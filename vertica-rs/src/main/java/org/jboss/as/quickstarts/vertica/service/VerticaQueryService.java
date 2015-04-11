package org.jboss.as.quickstarts.vertica.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jboss.as.quickstarts.vertica.utils.VerticaQueryTools;
import org.jboss.as.quickstarts.vertica.utils.VerticaTypeRegistry;
import org.jboss.as.quickstarts.vertica.web.VerticaResource;

import com.vertica.jdbc.DataSource;

public class VerticaQueryService {

	// connection pool
	static private DataSource ds;
	// base folder in classpath to retrieve sql files
	static private String queryRepository;
	// cache for queries and parameters from file def
	static final private Map<String, Class<?>[]> cacheQueryParamType = new HashMap<String, Class<?>[]>();
	static final private Map<String, String> cacheQuery = new HashMap<String, String>();

	static {
		init();
	}

	private static void init() {
		InputStream configIn = VerticaResource.class.getClassLoader()
				.getResourceAsStream("vertica.config");
		if (configIn == null) {
			throw new RuntimeException(
					"cannot find vertica.config in classpath");
		}
		Properties prop = new Properties();
		try {
			prop.load(configIn);
		} catch (Throwable th) {
			throw new RuntimeException(
					"cannot load properties from vertica.config", th);
		}
		// init datasource from loaded properties
		ds = new DataSource();
		ds.setURL(prop.getProperty("vertica.url"));
		ds.setUserID(prop.getProperty("vertica.user"));
		ds.setPassword(prop.getProperty("vertica.password"));
		ds.setConnSettings("SET LOCALE TO en_GB");
		ds.setAutoCommitOnByDefault(true);
		// init query repository base folder from loaded properties
		queryRepository = prop.getProperty("queries-repository",
				"vertica-queries");

	}

	public static Collection<Object> querySql(String sqlquery)
			throws ClassNotFoundException, IOException {
		return query(sqlquery, null, null);
	}

	public static Collection<Object> query(String queryId, String[] params)
			throws ClassNotFoundException, IOException {
		return query(getQuerySQL(queryId), params, getQueryParamTypes(queryId));
	}

	public static Collection<Object> query(String query, String[] params,
			Class<?>[] paramsType) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ds.getConnection();
			System.out.println("Sql > " + query);
			ps = con.prepareStatement(query);
			for (int i = 0; params != null && i < params.length; i++) {
				VerticaTypeRegistry.setParameter(ps, i + 1, paramsType[i],
						params[i]);
			}
			return VerticaQueryTools.dumpResultSet(ps.executeQuery());
		} catch (Throwable th) {
			throw new RuntimeException(th);

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Throwable ignore) {
				}
			}
			if (con != null)
				try {
					con.close();
				} catch (Throwable ttt) {
				}
		}
	}

	public static boolean script(String scriptId) {
		Connection con = null;
		try {
			VerticaQueryTools.runScript(con = ds.getConnection(),
					new StringReader(getQuerySQL(scriptId)));
			return true;
		} catch (Throwable th) {
			throw new RuntimeException(th);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (Throwable ignore) {
				}
		}
	}

	public synchronized static String getQuerySQL(String queryId) {

		if (cacheQuery.containsKey(queryId)) {
			return cacheQuery.get(queryId);
		}

		String fileName = queryRepository + "/"
				+ queryId.replaceAll("\\.", "/") + ".sql";
		InputStream in = VerticaQueryService.class.getClassLoader()
				.getResourceAsStream(fileName);
		if (in == null)
			throw new RuntimeException(fileName + " not found");

		StringWriter buf = new StringWriter();
		int c = 0;
		byte[] tmp = new byte[1024];
		try {
			while ((c = in.read(tmp)) > 0) {
				String data = new String(tmp, 0, c, "UTF-8");
				buf.write(data);
			}
		} catch (Throwable th) {
			throw new RuntimeException("Error when reading " + fileName, th);
		}
		String sqlquery = buf.getBuffer().toString();
		cacheQuery.put(queryId, sqlquery);
		return sqlquery;
	}

	public synchronized static Class<?>[] getQueryParamTypes(String queryId)
			throws ClassNotFoundException, IOException {

		if (cacheQueryParamType.containsKey(queryId)) {
			return cacheQueryParamType.get(queryId);
		}

		List<Class<?>> res = new ArrayList<Class<?>>();
		String fileName = queryRepository + "/"
				+ queryId.replaceAll("\\.", "/") + ".sql.params";
		InputStream in = VerticaQueryService.class.getClassLoader()
				.getResourceAsStream(fileName);
		if (in != null) {

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String s = null;
			while ((s = reader.readLine()) != null) {
				s = s.trim();
				if( s.length()>0 )
					res.add(Class.forName(s));
			}

		}
		Class<?>[] toReturn = res.toArray(new Class<?>[res.size()]);
		cacheQueryParamType.put(queryId, toReturn);
		return toReturn;
	}

}
