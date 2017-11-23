package samuandluis.siyoutube.persistence;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Playlist {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(mappedBy="playlist")
	private List<Video> listVideos;

	private String name;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Video> getListVideos() {
		return listVideos;
	}

	public void setListVideos(List<Video> listVideos) {
		this.listVideos = listVideos;
	}	
	
	void internalAddVideo (Video v) {
		this.listVideos.add(v);
	}
	
	void internalRemoveVideo (Video v) {
		this.listVideos.remove(v);
	}
}
