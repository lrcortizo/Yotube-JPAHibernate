package samuandluis.siyoutube.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Video video;
		
	@ManyToOne
	private User user;
	
	private String text;

	public Comment () {}
	
	public int getId() {
		return id;
	}
	
	public Video getVideo() {
		return video;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}
	
}
