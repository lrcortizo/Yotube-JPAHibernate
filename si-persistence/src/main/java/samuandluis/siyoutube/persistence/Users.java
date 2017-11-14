package samuandluis.siyoutube.persistence;

import java.util.List;

import javax.persistence.EntityManager;

public class Users {

	private EntityManager em;

	public Users(EntityManager em) {
		this.em = em;
	}
	
	public void addNewUser (User u) {
		em.persist(u);
		Channel c = new Channel(u.getNickname());
		em.persist(c);
	}
	
	public User findById(int id) {
		return em.find(User.class, id);
	}

	public void deleteUser(User u) {
		em.remove(u);
		em.remove(u.getChannel());
	}

	public List<User> findAll() {
		return em.createQuery("SELECT u FROM User u").getResultList();
	}
}
