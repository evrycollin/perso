package org.jboss.as.quickstarts.vertica.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import com.vertica.jdbc.DataSource;

public class VerticaQueryService {
	static final Logger logger = Logger.getLogger(VerticaQueryService.class
			.getName());
	static final DataSource ds;
	static final String queryRepository;
	static final Map<String, Class<?>[]> cacheQueryParamType = new HashMap<String, Class<?>[]>();
	static final Map<Class<?>, VerticaTypeConverter> cacheVerticaTypeConverter = new HashMap<Class<?>, VerticaQueryService.VerticaTypeConverter>();
	static final Map<String, String> cacheQuery = new HashMap<String, String>();

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
        ds.setConnSettings("SET LOCALE TO en_GB");
		queryRepository = prop.getProperty("queries-repository",
				"vertica-queries");

		registerVerticaTypeConverters();
	}

	public static final Connection getConnection() throws SQLException {
		return ds.getConnection();
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
				res.add(Class.forName(s));
			}

		}
		Class<?>[] toReturn = res.toArray(new Class<?>[res.size()]);
		cacheQueryParamType.put(queryId, toReturn);
		return toReturn;
	}

	public static Collection<Object> query(String queryId, String[] params)
			throws ClassNotFoundException, IOException {
		return query(getQuerySQL(queryId), params, getQueryParamTypes(queryId));
	}

	private static void dumpResultSet(ResultSet rs, List<Object> res)
			throws Exception {
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

	public static Collection<Object> query(String query, String[] params,
			Class<?>[] paramsType) {
		Connection con = null;
		List<Object> res = new ArrayList<Object>();
		try {
			con = VerticaQueryService.getConnection();
			System.out.println("Sql > " + query);
			PreparedStatement ps = con.prepareStatement(query);
			for (int i = 0; params != null && i < params.length; i++) {

				VerticaTypeConverter converter = cacheVerticaTypeConverter
						.get(paramsType[i]);
				if (converter != null) {
					converter.setValue(ps, i + 1, params[i]);
				} else {
					throw new RuntimeException("Type not supported : "
							+ paramsType[i].getName());
				}
			}
			dumpResultSet(ps.executeQuery(), res);
			ps.close();
		} catch (Throwable th) {
			throw new RuntimeException(th);

		} finally {
			if (con != null)
				try {
					con.close();
				} catch (Throwable ttt) {
				}
		}
		return res;

	}

	interface VerticaTypeConverter {
		void setValue(PreparedStatement ps, int idx, String val)
				throws Exception;
	}

	private static void registerVerticaTypeConverters() {
		cacheVerticaTypeConverter.put(Integer.class, new VerticaTypeConverter() {
			@Override
			public void setValue(PreparedStatement ps, int idx, String val)
					throws Exception {
				ps.setInt(idx, new Integer(val));
			}
		});
		cacheVerticaTypeConverter.put(Double.class, new VerticaTypeConverter() {
			@Override
			public void setValue(PreparedStatement ps, int idx, String val)
					throws Exception {
				ps.setDouble(idx, new Double(val));
			}
		});
		cacheVerticaTypeConverter.put(Long.class, new VerticaTypeConverter() {
			@Override
			public void setValue(PreparedStatement ps, int idx, String val)
					throws Exception {
				ps.setLong(idx, new Long(val));
			}
		});
		cacheVerticaTypeConverter.put(BigDecimal.class, new VerticaTypeConverter() {
			@Override
			public void setValue(PreparedStatement ps, int idx, String val)
					throws Exception {
				ps.setBigDecimal(idx, new BigDecimal(val));
			}
		});
		cacheVerticaTypeConverter.put(String.class, new VerticaTypeConverter() {
			@Override
			public void setValue(PreparedStatement ps, int idx, String val)
					throws Exception {
				ps.setString(idx, val);
			}
		});

	}
}
