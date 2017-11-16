package samuandluis.siyoutube.persistence;

import java.util.List;

import javax.persistence.EntityManager;

public class Channels {

	private EntityManager em;
	
	public Channels (EntityManager em) {
		this.em = em;
	}
	
	public Channel findById(int id) {
		return em.find(Channel.class, id);
	}
	
	
	public Channel findByName(String name) {
		return em.find(Channel.class, name);
	}

	public List<Channel> findAll() {
		return em.createQuery("SELECT c FROM Channel c").getResultList();
	}
}
