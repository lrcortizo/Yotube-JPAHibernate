package samuandluis.siyoutube.persistence;

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
		
	//Dani no tiene este atributo y lo maneja todo desde lo que sería nuestro Playlist y VideoPlaylist
	//@OneToMany(mappedBy="video", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	//private List<VideoPlaylist> videoPlaylist;

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
