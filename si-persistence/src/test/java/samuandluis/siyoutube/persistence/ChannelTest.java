package samuandluis.siyoutube.persistence;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import org.junit.Test;

public class ChannelTest extends SQLBasedTest{
	
	@Test
	public void testAddNewChannel() throws Exception {
		
		User u = new User();
		u.setName("User1");

		EntityManager em = emf.createEntityManager();
		Users users = new Users(em);
		Channels channels = new Channels(em);
		Channel c = new Channel();
		c.setUser(u);
		em.getTransaction().begin();
			users.addNewUser(u);
			c.setDescription("prueba 1");
			channels.addNewChannel(c);
			u.setChannel(c);
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		int userId = u.getId();
		Statement statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM Channel c where c.id = "+userId);
		rs.next();
		assertEquals(1, rs.getInt("total"));
	}
	
	@Test
	public void testFindById() throws SQLException {
		// insert a video previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Channel(description) values('Channel1')", Statement.RETURN_GENERATED_KEYS);
		int channelId = getLastInsertedId(statement);

		EntityManager em = emf.createEntityManager();
		Channels channel = new Channels(em);
		Channel c = channel.findById(channelId);
		
		assertEquals(channelId, c.getId());
		assertEquals("Channel1", c.getDescription());
	}
	
	@Test
	public void testUpdateChannel() throws SQLException {
		// insert a user previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Channel(description) values('Channel1')", Statement.RETURN_GENERATED_KEYS);
		int channelId = getLastInsertedId(statement);
		
		EntityManager em = emf.createEntityManager();
		Channels channels = new Channels(em);
		Channel c = channels.findById(channelId);
		
		em.getTransaction().begin();
			c.setDescription("Channel2");
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM Channel c where c.id = "+channelId);
		rs.next();
		assertEquals("Channel2", rs.getString("description"));

	}
	
}
