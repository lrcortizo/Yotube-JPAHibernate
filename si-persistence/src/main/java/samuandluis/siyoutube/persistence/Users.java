package samuandluis.siyoutube.persistence;

import java.util.List;

import javax.persistence.EntityManager;

public class Users {

	private EntityManager em;

	public Users(EntityManager em) {
		this.em = em;
	}
	
	public void addNewUser (User u) {
		em.persist(u); //with this instruction, the channel is added too
	}
	
	public User findById(int id) {
		return em.find(User.class, id);
	}

	public void deleteUser(User u) {
		em.remove(u); //with this instruction, the channel is removed too
	}

	public List<User> findAll() {
		return em.createQuery("SELECT u FROM User u").getResultList();
	}
}
