package samuandluis.siyoutube.persistence;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import org.junit.Test;

public class ChannelTest extends SQLBasedTest{
	
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
