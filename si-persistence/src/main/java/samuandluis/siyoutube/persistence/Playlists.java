package samuandluis.siyoutube.persistence;

import java.util.List;

import javax.persistence.EntityManager;

public class Playlists {

	private EntityManager em;

	public Playlists(EntityManager em) {
		this.em = em;
	}
	
	public Playlist findById(int id) {
		return em.find(Playlist.class, id);
	}

	public void addNewPlaylist (Playlist p) {
		em.persist(p);
	}

	public void deletePlaylist(Playlist p) {
		em.remove(p);
	}

	public List<Playlist> findAll() {
		return em.createQuery("SELECT p FROM Playlist p").getResultList();
	}
}
