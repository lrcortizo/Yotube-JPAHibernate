package samuandluis.siyoutube.persistence;

import java.util.List;

import javax.persistence.EntityManager;

public class Videos {
	private EntityManager em;

	public Videos(EntityManager em) {
		this.em = em;
	}

	public void addNewVideo (Video v) {
		em.persist(v);
	}

	public Video findById(int id) {
		return em.find(Video.class, id);
	}

	public void deleteVideo(Video v) {
		em.remove(v);
	}

	public List<Video> findAll() {
		return em.createQuery("SELECT v FROM Video v").getResultList();
	}
}
