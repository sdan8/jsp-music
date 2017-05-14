package music.vo;

import java.util.List;

/**
 * 歌曲所有信息对象
 * @author 帅
 *
 */
public class SongAllInfo {

	Song song;
	Album album;
	Singer singer;
	List<Comments> comments;
	
	// 构造方法
	public SongAllInfo() {
		super();
	}
	
	
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	public Singer getSinger() {
		return singer;
	}
	public void setSinger(Singer singer) {
		this.singer = singer;
	}


	public List<Comments> getComments() {
		return comments;
	}


	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	
	
}
