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

import org.junit.Test;

public class LikeTest extends SQLBasedTest {

	@Test
	public void testAddNewLike() throws Exception {
		
		Like l = new Like();
		l.setLike(true);

		EntityManager em = emf.createEntityManager();
		Likes likes = new Likes(em);
		em.getTransaction().begin();
			likes.addNewLike(l);
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		int likeId = l.getId();
		Statement statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM UserLikesVideo l where l.id = "+likeId);
		rs.next();
		assertEquals(1, rs.getInt("total"));
	}
	
	@Test
	public void testFindById() throws SQLException {
		
		// insert a like previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO UserLikesVideo(likes) values(TRUE)", Statement.RETURN_GENERATED_KEYS);
		int likeId = getLastInsertedId(statement);

		EntityManager em = emf.createEntityManager();
		Likes likes = new Likes(em);
		Like l = likes.findById(likeId);
		
		assertEquals(likeId, l.getId());
		assertEquals(true, l.getLike());
	}
	
	@Test
	public void testUpdateLike() throws SQLException {
		// insert a like previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO UserLikesVideo(likes) values(TRUE)", Statement.RETURN_GENERATED_KEYS);
		int likeId = getLastInsertedId(statement);
		
		EntityManager em = emf.createEntityManager();
		Likes likes = new Likes(em);
		Like l = likes.findById(likeId);
		
		em.getTransaction().begin();
			l.setLike(false);
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM UserLikesVideo l where l.id = "+likeId);
		rs.next();
		assertEquals(false, rs.getBoolean("likes"));
	}
	
	@Test
	public void testDeleteLike() throws SQLException {
		// insert a like previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO UserLikesVideo(likes) values(TRUE)", Statement.RETURN_GENERATED_KEYS);
		int likeId = getLastInsertedId(statement);
		
		EntityManager em = emf.createEntityManager();
		Likes likes = new Likes(em);
		Like l = likes.findById(likeId);
		
		em.getTransaction().begin();
			likes.deleteLike(l);
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM UserLikesVideo l where l.id = "+likeId);
		rs.next();
		assertEquals(0, rs.getInt("total"));
	}
	
	@Test
	public void testFindAllLikes() throws SQLException {
		// insert 2 likes previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("DELETE FROM UserLikesVideo", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO UserLikesVideo(likes) values(TRUE)", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO UserLikesVideo(likes) values(FALSE)", Statement.RETURN_GENERATED_KEYS);
		
		EntityManager em = emf.createEntityManager();
		Likes lks = new Likes(em);
		List<Like> likes = lks.findAll();
		
		assertEquals(2, likes.size());
		
		Set<String> namesToTest = new HashSet(Arrays.asList(true, false));
		for (Like l: likes) {
			namesToTest.remove(l.getLike());
		}
		
		assertTrue(namesToTest.isEmpty());
	}
	
	@Test
	public void testFindAllLikesOfOneVideo() throws SQLException {
		
		// insert 1 video previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("DELETE FROM Video", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Video(name) values('video-1')", Statement.RETURN_GENERATED_KEYS);
		int videoId = getLastInsertedId(statement);		
		
		// insert 2 likes previously with JDBC
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("DELETE FROM UserLikesVideo", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO UserLikesVideo(likes, video_id) values(TRUE, "+videoId+")", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO UserLikesVideo(likes, video_id) values(FALSE, "+videoId+")", Statement.RETURN_GENERATED_KEYS);
		
		EntityManager em = emf.createEntityManager();
		Likes lks = new Likes(em);
		List<Like> likes = lks.findAll(videoId);
		
		assertEquals(2, likes.size());
		
		Set<String> namesToTest = new HashSet(Arrays.asList(true, false));
		for (Like l: likes) {
			namesToTest.remove(l.getLike());
		}
		
		assertTrue(namesToTest.isEmpty());
	}
	
}
