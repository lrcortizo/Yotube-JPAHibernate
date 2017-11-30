package samuandluis.siyoutube.persistence;

import java.util.List;

import javax.persistence.EntityManager;

public class Channels {

	private EntityManager em;
	
	public Channels (EntityManager em) {
		this.em = em;
	}
	
	public void addNewChannel (Channel c) {
		em.persist(c); //with this instruction, the channel is added too
	}
	
	public Channel findById(int id) {
		return em.find(Channel.class, id);
	}
	
	public List<Channel> findAll() {
		return em.createQuery("SELECT c FROM Channel c").getResultList();
	}
}
