import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionExample {

	public static void main(String[] args) {
		// Load JDBC driver
		try {
			Class.forName("com.vertica.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// Could not find the driver class. Likely an issue
			// with finding the .jar file.
			System.err.println("Could not find the JDBC driver class.");
			e.printStackTrace();
			return; // Bail out. We cannot do anything further.
		}

		Properties myProp = new Properties();
		myProp.put("user", "dbadmin");
		myProp.put("password", "tst1");
		Connection conn;
		try {
			conn = DriverManager.getConnection(
					"jdbc:vertica://192.168.1.19:5433/tst1", myProp);

			Statement stmt = conn.createStatement();
			stmt.execute("CREATE TABLE customers (CustID int, Last_Name"
					+ " char(50), First_Name char(50),Email char(50), "
					+ "Phone_Number char(12))");

			// Some dummy data to insert.
			String[] firstNames = new String[] { "Anna", "Bill", "Cindy",
					"Don", "Eric" };
			String[] lastNames = new String[] { "Allen", "Brown", "Chu",
					"Dodd", "Estavez" };
			String[] emails = new String[] { "aang@example.com",
					"b.brown@example.com", "cindy@example.com",
					"d.d@example.com", "e.estavez@example.com" };
			String[] phoneNumbers = new String[] { "123-456-789",
					"555-444-3333", "555-867-5309", "555-555-1212",
					"781-555-0000" };

			// Create the prepared statement
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO customers (CustID, Last_Name, First_Name, Email, "
							+ "Phone_Number) VALUES(?,?,?,?,?)");
			// Add rows to a batch in a loop. Each iteration adds a
			// new row.
			for (int i = 0; i < firstNames.length; i++) {
				// Add each parameter to the row.
				pstmt.setInt(1, i + 1);
				pstmt.setString(2, lastNames[i]);
				pstmt.setString(3, firstNames[i]);
				pstmt.setString(4, emails[i]);
				pstmt.setString(5, phoneNumbers[i]);
				// Add row to the batch.
				pstmt.addBatch();
			}
			// Batch is ready, execute it to insert the data
			pstmt.executeBatch();

			// Print the resulting table.
			ResultSet rs = null;
			rs = stmt.executeQuery("SELECT CustID, First_Name, "
					+ "Last_Name FROM customers");
			while (rs.next()) {
				System.out
						.println(rs.getInt(1) + " - " + rs.getString(2).trim()
								+ " " + rs.getString(3).trim());
			}
			// Cleanup
			//stmt.execute("DROP TABLE customers CASCADE");
			conn.close();

		} catch (SQLException e) {
			// Could not connect to database.
			System.err.println("Could not connect to database.");
			e.printStackTrace();
			return;
		}

	}

}