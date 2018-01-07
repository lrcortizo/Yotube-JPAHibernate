package samuandluis.siyoutube.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Like {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Video video;
		
	@ManyToOne
	private User user;
	
	private Boolean like; //true = like, false = dislike
	
	public int getId() {
		return id;
	}
	
	public Video getVideo() {
		return video;
	}
	
	public void setVideo(Video video) {
		this.video = video;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Boolean getLike() {
		return this.like;
	}

	public void setLike(Boolean like) {
		this.like = like;
	}

}
