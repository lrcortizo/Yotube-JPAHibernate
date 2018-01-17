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

public class CommentTest extends SQLBasedTest {
	
	@Test
	public void testAddNewComment() throws Exception {
		
		Comment c = new Comment();
		c.setText("Comment 1");

		EntityManager em = emf.createEntityManager();
		Comments comments = new Comments(em);
		em.getTransaction().begin();
			comments.addNewComment(c);
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		int commentId = c.getId();
		Statement statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM Comment c where c.id = "+commentId);
		rs.next();
		assertEquals(1, rs.getInt("total"));
	}
	
	@Test
	public void testFindById() throws SQLException {
		
		// insert a comment previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Comment(text) values('Comment 1')", Statement.RETURN_GENERATED_KEYS);
		int commentId = getLastInsertedId(statement);

		EntityManager em = emf.createEntityManager();
		Comments comments = new Comments(em);
		Comment c = comments.findById(commentId);
		
		assertEquals(commentId, c.getId());
		assertEquals("Comment 1", c.getText());
	}
	
	@Test
	public void testUpdateComment() throws SQLException {
		// insert a comment previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Comment(text) values('Comment 1')", Statement.RETURN_GENERATED_KEYS);
		int commentId = getLastInsertedId(statement);
		
		EntityManager em = emf.createEntityManager();
		Comments comments = new Comments(em);
		Comment c = comments.findById(commentId);
		
		em.getTransaction().begin();
			c.setText("Comment 2");
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM Comment c where c.id = "+commentId);
		rs.next();
		assertEquals("Comment 2", rs.getString("text"));
	}
	
	@Test
	public void testDeleteComment() throws SQLException {
		// insert a comment previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Comment(text) values('Comment 1')", Statement.RETURN_GENERATED_KEYS);
		int commentId = getLastInsertedId(statement);
		
		EntityManager em = emf.createEntityManager();
		Comments comments = new Comments(em);
		Comment c = comments.findById(commentId);
		
		em.getTransaction().begin();
			comments.deleteComment(c);
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM Comment c where c.id = "+commentId);
		rs.next();
		assertEquals(0, rs.getInt("total"));
	}
	
	@Test
	public void testFindAllComments() throws SQLException {
		// insert 2 comments previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("DELETE FROM Comment", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Comment(text) values('comment-1')", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Comment(text) values('comment-2')", Statement.RETURN_GENERATED_KEYS);
		
		EntityManager em = emf.createEntityManager();
		Comments comnts = new Comments(em);
		List<Comment> comments = comnts.findAll();
		
		assertEquals(2, comments.size());
		
		Set<String> namesToTest = new HashSet(Arrays.asList("comment-1", "comment-2"));
		for (Comment c: comments) {
			namesToTest.remove(c.getText());
		}
		
		assertTrue(namesToTest.isEmpty());
	}
	
	//@Test
	public void testFindAllCommentsOfOneVideo() throws SQLException {
		
		// insert 1 video previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("DELETE FROM Video", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Video(name) values('video-1')", Statement.RETURN_GENERATED_KEYS);
		int videoId = getLastInsertedId(statement);			
		// insert 2 comments previously with JDBC
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("DELETE FROM Comment", Statement.RETURN_GENERATED_KEYS);
		
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Comment(text, video_id) values('comment-1', "+videoId+")", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Comment(text, video_id) values('comment-2', "+videoId+")", Statement.RETURN_GENERATED_KEYS);
		
		EntityManager em = emf.createEntityManager();
		Comments comnts = new Comments(em);
		List<Comment> comments = comnts.findAll(videoId);
		
		assertEquals(2, comments.size());
		
		Set<String> namesToTest = new HashSet(Arrays.asList("comment-1", "comment-2"));
		for (Comment c: comments) {
			namesToTest.remove(c.getText());
		}
		
		assertTrue(namesToTest.isEmpty());
	}
}
