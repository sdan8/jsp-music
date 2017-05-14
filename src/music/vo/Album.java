package music.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 专辑类
 * @author 帅
 *
 */
public class Album implements Serializable{

	private static final long serialVersionUID = 413439954162136346L;
	
	int albumId;		// 专辑ID
	int singerId;		// 所属歌手ID
	String albumTitle;	// 专辑标题
	String albumPic;	// 专辑图片
	Date albumPubDate;	// 专辑出版日期
	String albumPubCom;	// 专辑出版公司
	
	// 数据库没有的字段
	Singer singer;
	
	// 构造方法
	public Album() {
		super();
	}
	
	public Album(int albumId, int singerId, String albumTitle, String albumPic, Date albumPubDate, String albumPubCom) {
		super();
		this.albumId = albumId;
		this.singerId = singerId;
		this.albumTitle = albumTitle;
		this.albumPic = albumPic;
		this.albumPubDate = albumPubDate;
		this.albumPubCom = albumPubCom;
	}

	public Album(Map<String, Object> map){
		this.albumId = (int) map.get("albumId");
		this.singerId = (int) map.get("singerId");
		this.albumTitle = (String) map.get("albumTitle");
		this.albumPic = (String) map.get("albumPic");
		this.albumPubDate = (Date) map.get("albumPubDate");
		this.albumPubCom = (String) map.get("albumPubCom");
	}
	
	
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}
	public int getSingerId() {
		return singerId;
	}
	public void setSingerId(int singerId) {
		this.singerId = singerId;
	}
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	public String getAlbumPic() {
		return albumPic;
	}
	public void setAlbumPic(String albumPic) {
		this.albumPic = albumPic;
	}
	public Date getAlbumPubDate() {
		return albumPubDate;
	}
	public void setAlbumPubDate(Date albumPubDate) {
		this.albumPubDate = albumPubDate;
	}
	public String getAlbumPubCom() {
		return albumPubCom;
	}
	public void setAlbumPubCom(String albumPubCom) {
		this.albumPubCom = albumPubCom;
	}

	public Singer getSinger() {
		return singer;
	}

	public void setSinger(Singer singer) {
		this.singer = singer;
	}
	
	
}
