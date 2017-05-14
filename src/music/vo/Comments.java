package music.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 评论类
 * @author 帅
 *
 */
public class Comments implements Serializable {

	private static final long serialVersionUID = -1497627742479232488L;
	
	int commentId;		// 评论ID
	int userId;			// 评论所属用户
	int songId;			// 所评论歌曲ID
	String commentText;	// 评论内容
	Date commentDate;	// 评论日期
	
	NormalUser normalUser;
	
	// 构造方法
	public Comments() {
	}
	public Comments(Map<String, Object> map) {
		this.normalUser = new NormalUser();
		this.commentId = (int) map.get("commentId");
		this.commentText = (String) map.get("commentText");
		this.commentDate = (Date) map.get("commentDate");
		this.userId = (int) map.get("userId");
		this.songId = (int) map.get("songId");
		this.normalUser.setUserAvatar((String) map.get("userAvatar"));
		this.normalUser.setUserId(userId);
		this.normalUser.setUserNickname((String) map.get("userNickname"));
	}
	
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
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
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}


	public NormalUser getNormalUser() {
		return normalUser;
	}


	public void setNormalUser(NormalUser normalUser) {
		this.normalUser = normalUser;
	}


	
}
