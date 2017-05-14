package music.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 管理员类
 * @author 帅
 *
 */
public class Admin implements Serializable{

	private static final long serialVersionUID = 8714071127799262867L;
	
	
	int adminId;			//管理员ID
	String adminUsername;	//管理员用户名
	String adminPassword;	//管理员密码
	Date adminRegisterDate;	//管理员注册时间
	Date adminLastDate;		//管理员上次登录时间
	
	// 构造方法
	public Admin() {
		
	}
	public Admin(Map<String, Object> map){
		this.adminId = (int) map.get("adminId");
		this.adminUsername = (String) map.get("adminUsername");
		this.adminPassword = (String) map.get("adminPassword");
		this.adminRegisterDate = (Date) map.get("adminRegisterDate");
		this.adminLastDate = (Date) map.get("adminLastDate");
	}
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getAdminUsername() {
		return adminUsername;
	}
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public Date getAdminRegisterDate() {
		return adminRegisterDate;
	}
	public void setAdminRegisterDate(Date adminRegisterDate) {
		this.adminRegisterDate = adminRegisterDate;
	}
	public Date getAdminLastDate() {
		return adminLastDate;
	}
	public void setAdminLastDate(Date adminLastDate) {
		this.adminLastDate = adminLastDate;
	}
	
	
}
