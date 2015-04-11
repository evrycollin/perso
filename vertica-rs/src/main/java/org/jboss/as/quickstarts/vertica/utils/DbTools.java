package org.jboss.as.quickstarts.vertica.utils;

import java.io.LineNumberReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DbTools {
	static final private String DEFAULT_DELIMITER = ";";

	public static Collection<Object> dumpResultSet(ResultSet rs)
			throws Exception {
		List<Object> res = new ArrayList<Object>();
		ResultSetMetaData meta = rs.getMetaData();
		List<String> colNames = new ArrayList<String>();
		for (int i = 1; i <= meta.getColumnCount(); i++) {
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
		return res;
	}

	
	public static void runScript(Connection conn, Reader reader) {
		StringBuffer command = null;
		boolean fullLineDelimiter = false;
		boolean stopOnError = true;

		try {
			LineNumberReader lineReader = new LineNumberReader(reader);
			String line = null;
			while ((line = lineReader.readLine()) != null) {
				if (command == null) {
					command = new StringBuffer();
				}
				String trimmedLine = line.trim();
				if (trimmedLine.startsWith("--")) {
					println(trimmedLine);
				} else if (trimmedLine.length() < 1
						|| trimmedLine.startsWith("//")) {
					// Do nothing
				} else if (trimmedLine.length() < 1
						|| trimmedLine.startsWith("--")) {
					// Do nothing
				} else if (!fullLineDelimiter
						&& trimmedLine.endsWith(DEFAULT_DELIMITER)
						|| fullLineDelimiter
						&& trimmedLine.equals(DEFAULT_DELIMITER)) {
					command.append(line.substring(0,
							line.lastIndexOf(DEFAULT_DELIMITER)));
					command.append(" ");
					Statement statement = conn.createStatement();

					println(command);

					boolean hasResults = false;
					if (stopOnError) {
						hasResults = statement.execute(command.toString());
					} else {
						try {
							statement.execute(command.toString());
						} catch (SQLException e) {
							e.fillInStackTrace();
							printlnError("Error executing: " + command);
							printlnError(e);
						}
					}

					ResultSet rs = statement.getResultSet();
					if (hasResults && rs != null) {
						ResultSetMetaData md = rs.getMetaData();
						int cols = md.getColumnCount();
						for (int i = 0; i < cols; i++) {
							String name = md.getColumnLabel(i);
							print(name + "\t");
						}
						println("");
						while (rs.next()) {
							for (int i = 0; i < cols; i++) {
								String value = rs.getString(i);
								print(value + "\t");
							}
							println("");
						}
					}

					command = null;
					try {
						statement.close();
					} catch (Exception e) {
					}
				} else {
					command.append(line);
					command.append(" ");
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void print(Object o) {
		System.out.print(o);
	}

	private static void println(Object o) {
		System.out.println(o);
	}

	private static void printlnError(Object o) {
		System.err.println(o);
	}
}
