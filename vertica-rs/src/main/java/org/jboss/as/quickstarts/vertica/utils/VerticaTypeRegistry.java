package org.jboss.as.quickstarts.vertica.utils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class VerticaTypeRegistry {

	public interface VerticaTypeConverter {
		void setValue(PreparedStatement ps, int idx, String val) throws Exception;
	}
	
	static final private Map<Class<?>, VerticaTypeConverter> cached;
	static {
		cached = new HashMap<Class<?>, VerticaTypeConverter>();
		registerVerticaTypeConverters();
	}

	private static void registerVerticaTypeConverters() {
		cached.put(Integer.class,
				new VerticaTypeConverter() {
					@Override
					public void setValue(PreparedStatement ps, int idx,
							String val) throws Exception {
						ps.setInt(idx, new Integer(val));
					}
				});
		cached.put(Double.class, new VerticaTypeConverter() {
			@Override
			public void setValue(PreparedStatement ps, int idx, String val)
					throws Exception {
				ps.setDouble(idx, new Double(val));
			}
		});
		cached.put(Long.class, new VerticaTypeConverter() {
			@Override
			public void setValue(PreparedStatement ps, int idx, String val)
					throws Exception {
				ps.setLong(idx, new Long(val));
			}
		});
		cached.put(BigDecimal.class,
				new VerticaTypeConverter() {
					@Override
					public void setValue(PreparedStatement ps, int idx,
							String val) throws Exception {
						ps.setBigDecimal(idx, new BigDecimal(val));
					}
				});
		cached.put(String.class, new VerticaTypeConverter() {
			@Override
			public void setValue(PreparedStatement ps, int idx, String val)
					throws Exception {
				ps.setString(idx, val);
			}
		});

	}

	public static VerticaTypeConverter getConverter(Class<?> type) {
		return cached.get(type);
	}

}
