package music.vo;

public class Recommend {

	int recmdId;	// 推荐记录ID
	int songId;		// 推荐的歌曲ID
	
	// 数据库不存在的字段
	Song song;		// 推荐的歌曲对象
	Album album;	// 推荐的歌曲所属专辑对象
	Singer singer;	// 推荐的歌曲所属歌手对象
	
	
	// 构造方法
	public Recommend() {
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



	public int getRecmdId() {
		return recmdId;
	}
	public void setRecmdId(int recmdId) {
		this.recmdId = recmdId;
	}
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	
	
}
