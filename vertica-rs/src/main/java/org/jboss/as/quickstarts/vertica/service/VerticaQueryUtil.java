package org.jboss.as.quickstarts.vertica.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import com.vertica.jdbc.DataSource;

public class VerticaQueryUtil {
	static final Logger logger = Logger.getLogger(VerticaQueryUtil.class
			.getName());
	static final DataSource ds;
	static final String queryRepository;
	static {
		ds = new DataSource();
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
			throw new RuntimeException("cannot load properties from config", th);
		}
		ds.setURL(prop.getProperty("vertica.url"));
		ds.setUserID(prop.getProperty("vertica.user"));
		ds.setPassword(prop.getProperty("vertica.password"));
		queryRepository = prop.getProperty("queries-repository",
				"vertica-queries");

	}

	public static final Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	public synchronized static String getQuerySQL(String queryId) {

		if (cacheQuery.containsKey(queryId)) {
			return cacheQuery.get(queryId);
		}

		String fileName = queryRepository + "/" + queryId + ".sql";
		InputStream in = VerticaQueryUtil.class.getClassLoader()
				.getResourceAsStream(fileName);
		if (in == null)
			throw new RuntimeException(fileName + " not found");

		StringWriter buf = new StringWriter();
		int c = 0;
		byte[] tmp = new byte[1024];
		try {
			while ((c = in.read(tmp)) > 0) {
				String data = new String(tmp, 0, c);
				buf.write(data);
			}
		} catch (Throwable th) {
			throw new RuntimeException("Error when reading " + fileName, th);
		}
		String sqlquery = buf.getBuffer().toString();
		cacheQuery.put(queryId, sqlquery);
		return sqlquery;
	}

	public synchronized static Class[] getQueryParamTypes(String queryId)
			throws ClassNotFoundException, IOException {

		if (cacheQueryParamType.containsKey(queryId)) {
			return cacheQueryParamType.get(queryId);
		}

		List<Class> res = new ArrayList<Class>();
		String fileName = queryRepository + "/" + queryId + ".sql.params";
		InputStream in = VerticaQueryUtil.class.getClassLoader()
				.getResourceAsStream(fileName);
		if (in != null) {

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String s = null;
			while ((s = reader.readLine()) != null) {
				res.add(Class.forName(s));
			}

		}
		Class[] toReturn = res.toArray(new Class[res.size()]);
		cacheQueryParamType.put(queryId, toReturn);
		return toReturn;
	}

	static final Map<String, Class[]> cacheQueryParamType = new HashMap<String, Class[]>();
	static final Map<String, String> cacheQuery = new HashMap<String, String>();

	public static Object query(String queryId, String[] params) throws ClassNotFoundException, IOException {
		return query( getQuerySQL(queryId), params, getQueryParamTypes(queryId) );
	}

	
	public static Object query(String query, String[] params, Class[] paramsType) {
		Connection con = null;
		List res = new ArrayList();
		try {
			con = VerticaQueryUtil.getConnection();
			System.out.println("Sql > " + query);
			PreparedStatement ps = con.prepareStatement(query);
			for (int i = 0; params!=null && i < params.length; i++) {

				if (paramsType[i].equals(Integer.class)) {
					ps.setInt(i + 1, Integer.parseInt(params[i]));
				} else if (paramsType[i].equals(Double.class)) {
					ps.setDouble(i + 1, Double.parseDouble(params[i]));
				} else if (paramsType[i].equals(Long.class)) {
					ps.setLong(i + 1, Long.parseLong(params[i]));
				} else if (paramsType[i].equals(BigDecimal.class)) {
					ps.setBigDecimal(i + 1, new BigDecimal(params[i]));
				} else if (paramsType[i].equals(String.class)) {
					ps.setString(i + 1, params[i]);
				} else {
					throw new RuntimeException("Type not supported : "
							+ paramsType[i].getName());
				}
			}
			dumpResultSet(ps.executeQuery(), res);
			ps.close();
		} catch (Throwable th) {
			return new RuntimeException(th);

		} finally {
			if (con != null)
				try {
					con.close();
				} catch (Throwable ttt) {
				}
		}
		return res;

	}

	private static void dumpResultSet(ResultSet rs, List res) throws Exception {
		ResultSetMetaData meta = rs.getMetaData();
		List<String> colNames = new ArrayList<String>();
		for (int i = 1; i < meta.getColumnCount(); i++) {
			colNames.add(meta.getColumnName(i));
		}
		while (rs.next()) {
			Map<String, Object> row = new LinkedHashMap<String, Object>();
			for (String col : colNames) {
				Object o = rs.getObject(col);
				if (o != null && o instanceof String) {
					row.put(col, o.toString().trim());
				} else {
					row.put(col, o);
				}
			}
			res.add(row);
		}
	}

	

}
