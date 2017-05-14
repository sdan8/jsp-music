package music.vo;

import java.util.List;

public class AlbumAllInfo {

	Album album;
	List<Song> songs;
	Singer singer;
	
	public AlbumAllInfo() {
	}
	
	
	public AlbumAllInfo(Album album, List<Song> songs, Singer singer) {
		super();
		this.album = album;
		this.songs = songs;
		this.singer = singer;
	}


	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	public List<Song> getSongs() {
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	public Singer getSinger() {
		return singer;
	}
	public void setSinger(Singer singer) {
		this.singer = singer;
	}
	
	
}
