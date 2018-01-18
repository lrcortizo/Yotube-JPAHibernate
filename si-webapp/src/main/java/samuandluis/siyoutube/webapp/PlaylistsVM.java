package samuandluis.siyoutube.webapp;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import samuandluis.siyoutube.webapp.DesktopEntityManagerManager;
import samuandluis.siyoutube.persistence.Playlist;
import samuandluis.siyoutube.persistence.Playlists;
import samuandluis.siyoutube.persistence.Video;
import samuandluis.siyoutube.persistence.Videos;

public class PlaylistsVM {
	
	private EntityManager em;
	private Playlists playlists;
	private Videos videos;
	
	private boolean isEditing = false;
	
	// Playlist under edition...
	private Playlist currentPlaylist;
	
	@Init
	public void init() {
		this.em = DesktopEntityManagerManager.getDesktopEntityManager();
		this.playlists = new Playlists(em);
		this.videos = new Videos(em);
	}
	
	public int getPlaylistVideos(@BindingParam("p") Playlist p) {
		return p.getVideos().size();
	}
	
	public List<Playlist> getPlaylists() {
		return this.playlists.findAll();
	}
	
	public List<Video> getVideos() {
		return this.videos.findAll();
	}
	
	public Playlist getCurrentPlaylist() {
		return currentPlaylist;
	}
	
	public void setCurrentPlaylist(Playlist currentPlaylist) {
		this.currentPlaylist = currentPlaylist;
	}
	
	@Command
	@NotifyChange("currentPlaylist")
	public void newPlaylist() {
		this.isEditing = false;
		this.currentPlaylist = new Playlist();
	}
	
	@Command
	@NotifyChange("currentPlaylist")
	public void resetEditing() {
		this.currentPlaylist = null;
	}
	
	@Command
	@NotifyChange({"currentPlaylist", "playlists"})
	public void savePlaylist() {
		
		this.em.getTransaction().begin();
			if (!isEditing) {
				this.playlists.addNewPlaylist(this.currentPlaylist);
			}
		this.em.getTransaction().commit();
		
		this.currentPlaylist = null;
	}
	
	@Command
	@NotifyChange("playlists")
	public void delete(@BindingParam("p") Playlist Playlist) {
		this.em.getTransaction().begin();
			this.playlists.deletePlaylist(Playlist);
		this.em.getTransaction().commit();
	}
	
	@Command
	@NotifyChange("currentPlaylist")
	public void edit(@BindingParam("p") Playlist Playlist) {
		this.isEditing = true;
		this.currentPlaylist = Playlist;
	}
}
