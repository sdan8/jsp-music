package music.dao;


import music.vo.Admin;

/**
 * 管理员DAO
 * @author 帅
 *
 */
public interface AdminDao {

	/**
	 * 保存admin对象到数据库
	 * @param admin 要保存的对象
	 * @return 保存成功返回true，反之返回false
	 */
	public boolean save(Admin admin);
	
	/**
	 * 修改admin对象到数据库
	 * @param admin 要修改的对象
	 */
	public boolean update(Admin admin);
	
	/**
	 * 管理员登录
	 * @param admin
	 * @return 登录成功返回管理员对象，反之返回null
	 */
	public Admin login(Admin admin);
}
