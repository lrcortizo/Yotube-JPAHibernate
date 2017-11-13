package samuandluis.siyoutube.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest extends SQLBasedTest {

	private static EntityManagerFactory emf;
	
	@BeforeClass
	public static void setUpEntityManagerFactory() {
		emf = Persistence.createEntityManagerFactory("youtube");
	}
	

	@Test
	public void testAddNewUser() throws Exception {
		
		User u = new User();
		u.setName("Stelan");

		EntityManager em = emf.createEntityManager();
		Users users = new Users(em);
		em.getTransaction().begin();
			users.addNewUser(u);
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		int userId = u.getId();
		Statement statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM User u where u.id = "+userId);
		rs.next();
		assertEquals(1, rs.getInt("total"));
	}
	
	@Test
	public void testFindById() throws SQLException {
		// insert a department previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO User(name) values('Stelan')", Statement.RETURN_GENERATED_KEYS);
		int userId = getLastInsertedId(statement);

		EntityManager em = emf.createEntityManager();
		Users users = new Users(em);
		User d = users.findById(userId);
		
		assertEquals(userId, d.getId());
		assertEquals("Stelan", d.getName());
		
	}
	
	@Test
	public void testUpdateUser() throws SQLException {
		// insert a department previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO User(name) values('Stelan')", Statement.RETURN_GENERATED_KEYS);
		int userId = getLastInsertedId(statement);
		
		EntityManager em = emf.createEntityManager();
		Users users = new Users(em);
		User u = users.findById(userId);
		
		em.getTransaction().begin();
			u.setName("Esteban");
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM User u where u.id = "+userId);
		rs.next();
		assertEquals("Esteban", rs.getString("name"));

	}
	
	@Test
	public void testDeleteUser() throws SQLException {
		// insert a department previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO User(name) values('Stelan')", Statement.RETURN_GENERATED_KEYS);
		int userId = getLastInsertedId(statement);
		
		EntityManager em = emf.createEntityManager();
		Users users = new Users(em);
		User u = users.findById(userId);
		
		em.getTransaction().begin();
			users.deleteUser(u);
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM User u where u.id = "+userId);
		rs.next();
		assertEquals(0, rs.getInt("total"));
	}
	
	@Test
	public void testFindAllUsers() throws SQLException {
		// insert a user previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("DELETE FROM User", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO User(name) values('usr-1')", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO User(name) values('usr-2')", Statement.RETURN_GENERATED_KEYS);
		
		EntityManager em = emf.createEntityManager();
		Users usrs = new Users(em);
		List<User> users = usrs.findAll();
		
		assertEquals(2, users.size());
		
		Set<String> namesToTest = new HashSet(Arrays.asList("usr-1", "usr-2"));
		for (User u: users) {
			namesToTest.remove(u.getName());
		}
		
		assertTrue(namesToTest.isEmpty());
	}
	
}