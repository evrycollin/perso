import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.as.quickstarts.vertica.service.VerticaQueryService;

public class Example1 {

	public static void main(String[] args) throws Exception {
		// Load JDBC driver
		
		Connection conn;
		try {
			conn = VerticaQueryService.getConnection();

			PreparedStatement stmt = conn.prepareStatement("select count(*) from test_load");

			// Create the prepared statement
			ResultSet rs = stmt.executeQuery();
			
			List<Object> res = new ArrayList();
			VerticaQueryService.dumpResultSet(rs, res);
			System.out.println(res);
			
			
			while (rs.next()) {
				System.out
						.println(rs.getInt(1) );
			}
			conn.close();

		} catch (SQLException e) {
			// Could not connect to database.
			System.err.println("Could not connect to database.");
			e.printStackTrace();
			return;
		}

	}

}