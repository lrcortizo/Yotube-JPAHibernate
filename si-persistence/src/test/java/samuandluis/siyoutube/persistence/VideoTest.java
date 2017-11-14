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

public class VideoTest extends SQLBasedTest{
	
	private static EntityManagerFactory emf;
	
	@BeforeClass
	public static void setUpEntityManagerFactory() {
		emf = Persistence.createEntityManagerFactory("youtube");
	}
	
	@Test
	public void testAddNewVideo() throws Exception {
		
		Video v = new Video();
		v.setName("Video1");

		EntityManager em = emf.createEntityManager();
		Videos videos = new Videos(em);
		em.getTransaction().begin();
			videos.addNewVideo(v);
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		int videoId = v.getId();
		Statement statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM Video v where v.id = "+videoId);
		rs.next();
		assertEquals(1, rs.getInt("total"));
	}
	
	@Test
	public void testFindById() throws SQLException {
		// insert a video previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Video(name) values('Video1')", Statement.RETURN_GENERATED_KEYS);
		int videoId = getLastInsertedId(statement);

		EntityManager em = emf.createEntityManager();
		Videos videos = new Videos(em);
		Video v = videos.findById(videoId);
		
		assertEquals(videoId, v.getId());
		assertEquals("Video1", v.getName());
	}
	
	@Test
	public void testUpdateVideo() throws SQLException {
		// insert a video previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Video(name) values('Video1')", Statement.RETURN_GENERATED_KEYS);
		int videoId = getLastInsertedId(statement);
		
		EntityManager em = emf.createEntityManager();
		Videos videos = new Videos(em);
		Video v = videos.findById(videoId);
		
		em.getTransaction().begin();
			v.setName("Video2");
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM Video v where v.id = "+videoId);
		rs.next();
		assertEquals("Video2", rs.getString("name"));
	}
	
	@Test
	public void testDeleteVideo() throws SQLException {
		// insert a video previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Video(name) values('Video1')", Statement.RETURN_GENERATED_KEYS);
		int videoId = getLastInsertedId(statement);
		
		EntityManager em = emf.createEntityManager();
		Videos videos = new Videos(em);
		Video v = videos.findById(videoId);
		
		em.getTransaction().begin();
			videos.deleteVideo(v);
		em.getTransaction().commit();
		
		// check in the DB using JDBC
		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM Video v where v.id = "+videoId);
		rs.next();
		assertEquals(0, rs.getInt("total"));
	}
	
	@Test
	public void testFindAllVideos() throws SQLException {
		// insert a video previously with JDBC
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("DELETE FROM Video", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Video(name) values('video-1')", Statement.RETURN_GENERATED_KEYS);
		
		statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Video(name) values('video-2')", Statement.RETURN_GENERATED_KEYS);
		
		EntityManager em = emf.createEntityManager();
		Videos vds = new Videos(em);
		List<Video> videos = vds.findAll();
		
		assertEquals(2, videos.size());
		
		Set<String> namesToTest = new HashSet(Arrays.asList("video-1", "video-2"));
		for (Video v: videos) {
			namesToTest.remove(v.getName());
		}
		
		assertTrue(namesToTest.isEmpty());
	}
}
