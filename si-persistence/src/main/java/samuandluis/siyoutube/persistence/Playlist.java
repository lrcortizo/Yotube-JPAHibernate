package samuandluis.siyoutube.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
	
	@OneToMany(mappedBy="playlist", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	private List<VideoPlaylist> videoPlaylists;

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

	public List<VideoPlaylist> getVideoPlaylist() {
		return videoPlaylists;
	}

	public void setVideoPlaylist(List<VideoPlaylist> videoPlaylist) {
		this.videoPlaylists = videoPlaylist;
	}	
	
	public List <Video> getVideos() {
		List <Video> videos = new ArrayList<>();
		
		for (VideoPlaylist vp : videoPlaylists) {
			videos.add(vp.getVideo());
		}
		
		return videos;
	}
	
	public void setVideos(List<Video> videos) {
		// remove my videos that are not in the new list of videos
		for (Video v: this.getVideos()) {
			if (!videos.contains(v)) {
				this.removeVideo(v);
			}
		}
		
		// add all the new videos
		for (Video v: videos) {
			if (!this.getVideos().contains(v)) {
				this.addVideo(v);
			}
		}
	}
	
	public void addVideo(Video v) {

		VideoPlaylist vp = new VideoPlaylist();
		vp.setPlaylist(this);
		vp.setVideo(v);
		// the VideoPlaylist is automatically persisted due to CascadeType.PERSIST
	}
	
	public void removeVideo(Video v) {
		for (VideoPlaylist vp: this.videoPlaylists) {
			if (vp.getVideo().equals(v)) {
				// this will call internalRemoveVideoPlaylist
				vp.setPlaylist(null);

				// the video playlist will be also removed from the DB due to "orphanRemoval"
			}
		}
	}
	
	void internalRemoveVideoPlaylist(VideoPlaylist videoPlaylist) {
		this.videoPlaylists.remove(videoPlaylist);
	}

	void internalAddVideoPlaylist(VideoPlaylist videoPlaylist) {
		this.videoPlaylists.add(videoPlaylist);
	}
	
}
