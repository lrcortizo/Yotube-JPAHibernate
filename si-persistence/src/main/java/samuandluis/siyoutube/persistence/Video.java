package samuandluis.siyoutube.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GenerationType;

@Entity
public class Video {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;
	private String category;
	
	@ManyToOne
	private Channel channel;
	
	//@OneToMany(mappedBy="video", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	//private List<VideoPlaylist> videoPlaylist;

	@OneToMany(mappedBy="video", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<Comment>();
	
	@OneToMany(mappedBy="video", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	private List<Like> likes = new ArrayList<Like>();

	
	public int getId() {
		return id;
	}
	/*
	public List<VideoPlaylist> getVideoPlaylist() {
		return videoPlaylist;
	}
	
	public void setVideoPlaylist(List<VideoPlaylist> videoPlaylist) {
		this.videoPlaylist = videoPlaylist;
	}
	*/
	public List <Comment> getComments() {
		return this.comments;
	}
	
	public List <Like> getLikes() {
		return this.likes;
	}
	
	public void setComments(List<Comment> comments) {
		for (Comment c: comments) {
			if (!this.getComments().contains(c)) {
				this.addComment(c);
			}
		}
	}
		
	public void addComment(Comment c) {

		this.getComments().add(c);
		c.setVideo(this);
	}
	
	public void setLikes(List<Like> likes) {
		for (Like l: likes) {
			if (!this.getLikes().contains(l)) {
				this.addLike(l);
			}
		}
	}
		
	public void addLike(Like l) {

		this.getLikes().add(l);
		l.setVideo(this);
	}
	
	public Channel getChannel() {
		return this.channel;
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

}
