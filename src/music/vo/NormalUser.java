package music.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 普通用户类
 * @author 帅
 *
 */
public class NormalUser implements Serializable{
	
	private static final long serialVersionUID = -2688544176336105712L;
	
	int userId;				// 用户ID
	String userName;		// 用户名
	String userPassword;	// 用户密码
	String userNickname;	// 用户昵称
	int userSex;			// 用户性别（0.保密，1.男，2.女）
	String userEmail;		// 用户邮箱
	String userAvatar;		// 用户头像路径
	Date userRegisterDate;	// 用户注册时间
	Date userLastDate;		// 用户上次登录时间
	int userStatus;			// 用户状态
	
	
	// 构造方法
	public NormalUser() {
		
	}
	public NormalUser(Map<String, Object> map){
		this.userId = (int) map.get("userId");
		this.userName = (String) map.get("userName");
		this.userPassword = (String) map.get("userPassword");
		this.userNickname = (String) map.get("userNickname");
		this.userSex = (int) map.get("userSex");
		this.userEmail = (String) map.get("userEmail");
		this.userAvatar = (String) map.get("userAvatar");
		this.userRegisterDate = (Date) map.get("userRegisterDate");
		this.userLastDate = (Date) map.get("userLastDate");
		this.userStatus = (int) map.get("userStatus");
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public int getUserSex() {
		return userSex;
	}
	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserAvatar() {
		return userAvatar;
	}
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	public Date getUserRegisterDate() {
		return userRegisterDate;
	}
	public void setUserRegisterDate(Date userRegisterDate) {
		this.userRegisterDate = userRegisterDate;
	}
	public Date getUserLastDate() {
		return userLastDate;
	}
	public void setUserLastDate(Date userLastDate) {
		this.userLastDate = userLastDate;
	}
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	
	
	
}
