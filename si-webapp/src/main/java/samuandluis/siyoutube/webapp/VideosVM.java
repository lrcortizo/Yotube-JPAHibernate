package samuandluis.siyoutube.webapp;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import samuandluis.siyoutube.webapp.DesktopEntityManagerManager;
import samuandluis.siyoutube.persistence.Comments;
import samuandluis.siyoutube.persistence.Likes;
import samuandluis.siyoutube.persistence.Like;
import samuandluis.siyoutube.persistence.Comment;
import samuandluis.siyoutube.persistence.Video;
import samuandluis.siyoutube.persistence.Videos;
public class VideosVM {
	
	private EntityManager em;
	private Videos videos;
	private Comments comments;
	private Likes likes;
	
	private boolean isEditing = false;
	
	// video under edition...
	private Video currentVideo;
	
	@Init
	public void init() {
		this.em = DesktopEntityManagerManager.getDesktopEntityManager();
		this.videos = new Videos(em);
		this.comments = new Comments(em);
		this.likes = new Likes(em);
	}
	
	public List<Video> getVideos() {
		return this.videos.findAll();
	}
	
	public List<Comment> getComments() {
		return this.comments.findAll();
	}
	
	public List<Like> getLikes() {
		return this.likes.findAll();
	}
	
	public Video getCurrentVideo() {
		return currentVideo;
	}
	
	public void setCurrentVideo(Video currentVideo) {
		this.currentVideo = currentVideo;
	}
	
	public int getNumComments(@BindingParam("v") Video v) {
		return v.getComments().size();
	}
	
	public int getNumRatings(@BindingParam("v") Video v) {
		return v.getLikes().size();
	}
	
	@Command
	@NotifyChange("currentVideo")
	public void newVideo() {
		this.isEditing = false;
		this.currentVideo = new Video();
	}
	
	@Command
	@NotifyChange("currentVideo")
	public void resetEditing() {
		this.currentVideo = null;
	}
	
	@Command
	@NotifyChange({"currentVideo", "videos"})
	public void saveVideo() {
		
		this.em.getTransaction().begin();
			if (!isEditing) {
				this.videos.addNewVideo(this.currentVideo);
			}
		this.em.getTransaction().commit();
		
		this.currentVideo = null;
	}
	
	@Command
	@NotifyChange("videos")
	public void delete(@BindingParam("v") Video Video) {
		this.em.getTransaction().begin();
			this.videos.deleteVideo(Video);
		this.em.getTransaction().commit();
	}
	
	@Command
	@NotifyChange("currentVideo")
	public void edit(@BindingParam("v") Video Video) {
		this.isEditing = true;
		this.currentVideo = Video;
	}
	
	
}