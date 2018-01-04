package samuandluis.siyoutube.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class SQLBasedTest {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/youtube";
	private static final String DB_USER = "siuser";
	private static final String DB_PASS = "sipass";
	protected static Connection jdbcConnection = createConnection();
	
	private static Connection createConnection() {
		try {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected int getLastInsertedId(Statement statement) throws SQLException {
		ResultSet rs = statement.getGeneratedKeys();
		rs.next();
		
		return rs.getInt(1);
	}
	
	// EntityManagerFactory management
		protected static EntityManagerFactory emf;

		@BeforeClass
		public static void setUpEntityManagerFactory() {
			emf = Persistence.createEntityManagerFactory("youtube-database");
		}

		@AfterClass
		public static void closeEntityManagerFactory() throws SQLException {
			if (emf != null && emf.isOpen()) {
				emf.close();
			}
		}
}
