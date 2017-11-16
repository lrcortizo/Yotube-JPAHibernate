package samuandluis.siyoutube.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Channel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne (fetch=FetchType.LAZY)
	private User user;
	private List<Video> listVideos;
	
	
	public Channel () {
		this.listVideos = new ArrayList<Video>();
	}
	
	public int getId() {
		return id;
	}
	
	public List<Video> getListVideos() {
		return listVideos;
	}
	
	public void setListVideos(List<Video> listVideos) {
		this.listVideos = listVideos;
	}
	
	
}
