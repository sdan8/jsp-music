package music.vo;

import java.io.Serializable;

/**
 * 用户收藏歌曲类
 * @author 帅
 *
 */
public class NormalUserSong implements Serializable {

	private static final long serialVersionUID = -3339991183053257247L;
	
	int userId;	// 用户ID
	int songId;	// 歌曲ID
	
	
	// 数据库没有的对象
	Song song;
	
	
	// 构造方法
	public NormalUserSong() {
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}


	public Song getSong() {
		return song;
	}


	public void setSong(Song song) {
		this.song = song;
	}
	
	
}
