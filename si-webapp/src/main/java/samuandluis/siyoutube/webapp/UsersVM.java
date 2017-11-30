package samuandluis.siyoutube.webapp;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.Init;

import samuandluis.siyoutube.webapp.DesktopEntityManagerManager;
import samuandluis.siyoutube.persistence.User;
import samuandluis.siyoutube.persistence.Users;

public class UsersVM {
	
	private EntityManager em;
	private Users users;
	
	@Init
	public void init() {
		this.em = DesktopEntityManagerManager.getDesktopEntityManager();
		this.users = new Users(em);
	}
	
	public List<User> getUsers() {
		return this.users.findAll();
	}
}
