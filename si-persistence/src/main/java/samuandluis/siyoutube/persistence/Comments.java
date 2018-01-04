package samuandluis.siyoutube.persistence;

import java.util.List;

import javax.persistence.EntityManager;

public class Comments {
private EntityManager em;
	
	public Comments (EntityManager em) {
		this.em = em;
	}
	
	public void addNewComment(Comment c) {
		em.persist(c); 
	}
	
	public Comment findById(int id) {
		return em.find(Comment.class, id);
	}
	
	public void deleteComment(Comment c) {
		em.remove(c);
	}
	
	public List<Comment> findAll() {
		return em.createQuery("SELECT c FROM Comment c").getResultList();
	}
}
