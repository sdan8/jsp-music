package music.vo;

public class NewSong {

	int newSongId;
	int songId;
	
	// 数据库不存在的字段
	Song song;		// 推荐的歌曲名称
	Singer singer;	// 推荐的歌曲所属歌手对象
	Album album;	// 推荐的歌曲所属专辑对象
	
	public NewSong() {
		super();
	}
	
	
	
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}


	public Singer getSinger() {
		return singer;
	}



	public void setSinger(Singer singer) {
		this.singer = singer;
	}



	public Album getAlbum() {
		return album;
	}



	public void setAlbum(Album album) {
		this.album = album;
	}



	public int getNewSongId() {
		return newSongId;
	}
	public void setNewSongId(int newSongId) {
		this.newSongId = newSongId;
	}
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	
	
}
