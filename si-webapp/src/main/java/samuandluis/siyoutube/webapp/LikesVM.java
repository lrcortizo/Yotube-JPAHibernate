package samuandluis.siyoutube.webapp;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import samuandluis.siyoutube.webapp.DesktopEntityManagerManager;
import samuandluis.siyoutube.persistence.Like;
import samuandluis.siyoutube.persistence.Likes;
public class LikesVM {
	
	private EntityManager em;
	private Likes likes;
	
	private boolean isEditing = false;
	
	// like under edition...
	private Like currentLike;
	
	@Init
	public void init() {
		this.em = DesktopEntityManagerManager.getDesktopEntityManager();
		this.likes = new Likes(em);
	}
	
	public List<Like> getLikes() {
		return this.likes.findAll();
	}
	
	public Like getCurrentLike() {
		return currentLike;
	}
	
	public void setCurrentLike(Like currentLike) {
		this.currentLike = currentLike;
	}
	
	@Command
	@NotifyChange("currentLike")
	public void newLike() {
		this.isEditing = false;
		this.currentLike = new Like();
	}
	
	@Command
	@NotifyChange("currentLike")
	public void resetEditing() {
		this.currentLike = null;
	}
	
	@Command
	@NotifyChange({"currentLike", "likes"})
	public void saveLike() {
		
		this.em.getTransaction().begin();
			if (!isEditing) {
				this.likes.addNewLike(this.currentLike);
			}
		this.em.getTransaction().commit();
		
		this.currentLike = null;
	}
	
	@Command
	@NotifyChange("likes")
	public void delete(@BindingParam("l") Like Like) {
		this.em.getTransaction().begin();
			this.likes.deleteLike(Like);
		this.em.getTransaction().commit();
	}
	
	@Command
	@NotifyChange("currentLike")
	public void edit(@BindingParam("l") Like Like) {
		this.isEditing = true;
		this.currentLike = Like;
	}
	
	
}