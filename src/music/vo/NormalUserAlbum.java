package music.vo;

import java.io.Serializable;

/**
 * 用户收藏专辑类
 * @author 帅
 *
 */
public class NormalUserAlbum implements Serializable {

	private static final long serialVersionUID = -4591715018803067818L;
	
	int userId;		// 用户ID
	int albumId;	// 专辑ID
	
	
	// 数据库没有的字段
	Album album;
	
	// 构造方法
	public NormalUserAlbum() {
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}


	public Album getAlbum() {
		return album;
	}


	public void setAlbum(Album album) {
		this.album = album;
	}
	
	
}
