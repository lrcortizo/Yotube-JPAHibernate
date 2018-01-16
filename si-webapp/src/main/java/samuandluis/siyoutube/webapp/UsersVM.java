package samuandluis.siyoutube.webapp;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import samuandluis.siyoutube.webapp.DesktopEntityManagerManager;
import samuandluis.siyoutube.persistence.User;
import samuandluis.siyoutube.persistence.Users;

public class UsersVM {
	
	private EntityManager em;
	private Users users;
	
	private boolean isEditing = false;
	
	// User under edition...
	private User currentUser;
	
	@Init
	public void init() {
		this.em = DesktopEntityManagerManager.getDesktopEntityManager();
		this.users = new Users(em);
	}
	
	public List<User> getUsers() {
		return this.users.findAll();
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	@Command
	@NotifyChange("currentUser")
	public void newUser() {
		this.isEditing = false;
		this.currentUser = new User();
	}
	
	@Command
	@NotifyChange("currentUser")
	public void resetEditing() {
		this.currentUser = null;
	}
	
	@Command
	@NotifyChange({"currentUser", "users"})
	public void saveUser() {
		
		this.em.getTransaction().begin();
			if (!isEditing) {
				this.users.addNewUser(this.currentUser);
			}
		this.em.getTransaction().commit();
		
		this.currentUser = null;
	}
	
	@Command
	@NotifyChange("users")
	public void delete(@BindingParam("u") User User) {
		this.em.getTransaction().begin();
			this.users.deleteUser(User);
		this.em.getTransaction().commit();
	}
	
	@Command
	@NotifyChange("currentUser")
	public void edit(@BindingParam("u") User User) {
		this.isEditing = true;
		this.currentUser = User;
	}
}
