package samuandluis.siyoutube.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(VideoPlaylist.VideoPlaylistId.class)
public class VideoPlaylist {

	@Id
	@ManyToOne
	private Video video;
	
	@Id
	@ManyToOne
	private Playlist playlist;
	
	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public static class VideoPlaylistId implements Serializable {
		private int video;
		private int playlist;
		
		
		public VideoPlaylistId() {}
		public VideoPlaylistId(int video, int playlist) {
			super();
			this.video = video;
			this.playlist = playlist;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + playlist;
			result = prime * result + video;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			VideoPlaylistId other = (VideoPlaylistId) obj;
			if (playlist != other.playlist)
				return false;
			if (video != other.video)
				return false;
			return true;
		}
	}
}
