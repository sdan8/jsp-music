package music.vo;

import java.util.List;

public class SingerAllInfo {

	Singer singer;
	List<Album> albums;
	List<Song> songs;
	
	
	public SingerAllInfo() {
	}
	
	public SingerAllInfo(Singer singer, List<Album> albums, List<Song> songs) {
		this.singer = singer;
		this.albums = albums;
		this.songs = songs;
	}

	
	public Singer getSinger() {
		return singer;
	}
	public void setSinger(Singer singer) {
		this.singer = singer;
	}
	public List<Album> getAlbums() {
		return albums;
	}
	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
	public List<Song> getSongs() {
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
	
}
