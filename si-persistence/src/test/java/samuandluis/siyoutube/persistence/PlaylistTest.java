package samuandluis.siyoutube.persistence;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

public class PlaylistTest extends SQLBasedTest {

private static EntityManagerFactory emf;
	
	@BeforeClass
	public static void setUpEntityManagerFactory() {
		emf = Persistence.createEntityManagerFactory("youtube");
	}
	
	@Test
	public void testAddNewPlaylist() throws Exception {
		

		EntityManager em = emf.createEntityManager();
		Playlists playlists = new Playlists(em);

		Playlist p = new Playlist();
		p.setName("playlist 1");
		
		em.getTransaction().begin();
			playlists.addNewPlaylist(p);
		em.getTransaction().commit();
		
		int playlistId = p.getId();

		Statement statement = jdbcConnection.createStatement();
		ResultSet res = statement.executeQuery("SELECT COUNT(*) as total from Playlist where id = "+playlistId);
		res.next();
		assertEquals(1, res.getInt("total"));
		
	}
	
	@Test
	public void testAddEmployeeToProject() throws SQLException {

		// insert a playlist previously with JDBC
		int playlistId = insertAPlaylist();

		// insert an video previously with JDBC
		int videoId = insertAVideo();

		EntityManager em = emf.createEntityManager();

		Playlists playlists = new Playlists(em);
		Playlist p = playlists.findById(playlistId);

		Videos videos = new Videos(em);
		Video v = videos.findById(videoId);

		em.getTransaction().begin();

			p.addVideo(v);

		em.getTransaction().commit();


		// ensure that a VideoPlaylist has been created
		Statement statement = jdbcConnection.createStatement();
		ResultSet res = statement.executeQuery("SELECT COUNT(*) as total from VideoPlaylist where playlist_id = " +
				""+playlistId+" and video_id = "+videoId);
		res.next();
		assertEquals(1, res.getInt("total"));
	}
	
	@Test
	public void testRemoveVideoFromPlaylist() throws SQLException {
		// insert a playlist previously with JDBC
		int playlistId = insertAPlaylist();

		// insert a video previously with JDBC
		int videoId = insertAVideo();

		// assign the video to the playlist (which should be removed due to CascadeType.REMOVE
		// in Project.projectAssignments)
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO VideoPlaylist(playlist_id, video_id) values("+playlistId+", "
				+videoId+")", Statement
				.RETURN_GENERATED_KEYS);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
			Playlist p = new Playlists(em).findById(playlistId);
			Video v = new Videos(em).findById(videoId);

			p.removeVideo(v);
		em.getTransaction().commit();


		// ensure that the videoplaylist does not exist
		statement = jdbcConnection.createStatement();
		ResultSet res = statement.executeQuery("SELECT COUNT(*) as total from VideoPlaylist where video_id = " +
				""+videoId+" and playlist_id = "+playlistId);
		res.next();
		assertEquals(0, res.getInt("total"));
	}
	
	@Test
	public void testCreateVideoPlaylist() throws SQLException {
		// insert a playlist previously with JDBC
		int playlistId = insertAPlaylist();

		// insert a video previously with JDBC
		int videoId = insertAVideo();

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

			Playlist p = new Playlists(em).findById(playlistId);
			Video v = new Videos(em).findById(videoId);

			VideoPlaylist vp = new VideoPlaylist();
			vp.setPlaylist(p); // vp is automatically added to the Playlist.videoPlaylists collection
			vp.setVideo(v);
			// the ProjectAssignment is automatically persisted due to CascadeType.PERSIST present in Project.projectAssignments

		em.getTransaction().commit();

		// ensure that a VideoPlaylist has been created using JDBC
		Statement statement = jdbcConnection.createStatement();
		ResultSet res = statement.executeQuery("SELECT COUNT(*) as total from VideoPlaylist where playlist_id = " +
				""+playlistId+" and video_id = "+videoId);
		res.next();
		assertEquals(1, res.getInt("total"));
	}
	
	@Test
	public void testDeletePlaylist() throws SQLException {
		// insert a playlist previously with JDBC
		int playlistId = insertAPlaylist();

		// insert a video previously with JDBC
		int videoId = insertAVideo();

		// assign the video to the playlist (which should be removed due to CascadeType.REMOVE
		// in Playlist.videoPlaylists)
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO VideoPlaylist(playlist_id, video_id) values("+playlistId+", "
				+videoId+")", Statement
				.RETURN_GENERATED_KEYS);

		EntityManager em = emf.createEntityManager();
		Playlists playlists = new Playlists(em);
		Playlist p = playlists.findById(playlistId);

		em.getTransaction().begin();
			playlists.deletePlaylist(p);
		em.getTransaction().commit();

		// ensure that playlist doesn't exist using JDBC
		statement = jdbcConnection.createStatement();
		ResultSet res = statement.executeQuery("SELECT COUNT(*) as total from Playlist where id = " +
				""+playlistId);
		res.next();
		assertEquals(0, res.getInt("total"));
	}
	
	@Test
	public void testGetVideos() throws SQLException {
		// insert a playlist previously with JDBC
		int playlistId = insertAPlaylist();

		// insert a video previously with JDBC
		int videoId = insertAVideo();

		// assign the video to the playlist (which should be removed due to CascadeType.REMOVE
		// in Playlist.videoPlaylists)
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO VideoPlaylist(playlist_id, video_id) values("+playlistId+", "
				+videoId+")", Statement
				.RETURN_GENERATED_KEYS);
	}
	
	
	private int insertAPlaylist() throws SQLException {
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Playlist(name) values('playlist 1')", Statement
				.RETURN_GENERATED_KEYS);
		return getLastInsertedId(statement);
	}

	private int insertAVideo() throws SQLException {
		Statement statement = jdbcConnection.createStatement();
		statement.executeUpdate("INSERT INTO Video(name) values('video 1')", Statement
				.RETURN_GENERATED_KEYS);
		return getLastInsertedId(statement);
	}
	
}
