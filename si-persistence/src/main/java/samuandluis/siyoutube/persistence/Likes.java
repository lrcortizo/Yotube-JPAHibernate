package samuandluis.siyoutube.persistence;

import java.util.List;

import javax.persistence.EntityManager;

public class Likes {

private EntityManager em;
	
	public Likes (EntityManager em) {
		this.em = em;
	}
	
	public void addNewLike(Like l) {
		em.persist(l); 
	}
	
	public Like findById(int id) {
		return em.find(Like.class, id);
	}
	
	public void deleteLike(Like l) {
		em.remove(l);
	}
	
	public List<Like> findAll() {
		return em.createQuery("SELECT l FROM Like l").getResultList();
	}
	
	public List<Like> findAll(String videoId) {
		return em.createQuery("SELECT l FROM Like l where l.video_id = "+ videoId).getResultList();
	}
	
}
