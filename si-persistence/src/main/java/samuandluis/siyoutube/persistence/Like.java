package samuandluis.siyoutube.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="UserLikesVideo")
public class Like {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Video video;
		
	@ManyToOne
	private User user;
	
	@Column(name = "likes")
	private boolean like; //true = like, false = dislike
	
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
	
	public boolean getLike() {
		return this.like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

}
