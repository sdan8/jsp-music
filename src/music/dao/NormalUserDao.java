package music.dao;

import music.vo.NormalUser;

public interface NormalUserDao {

	/**
	 * 注册用户
	 * @param normalUser 要注册的用户信息封装在NormalUser对象中
	 * @return 注册成功返回true，反之返回false
	 */
	public boolean singup(NormalUser normalUser);
	
	/**
	 * 登录用户
	 * @param normalUser 封装登录信息，包括用户名和密码
	 * @return 登录成功返回NormalUser对象，失败返回null
	 */
	public NormalUser login(NormalUser normalUser);
	/**
	 * 根据normalUser修改用户基本设置
	 * @param normalUser
	 * @return
	 */
	public boolean setting(NormalUser normalUser);
	/**
	 * 保存用户头像
	 * @param normalUser
	 * @return
	 */
	public boolean save_avatar(NormalUser normalUser);
	/**
	 * 根据userId修改用户密码
	 * @param normalUser
	 * @return
	 */
	public boolean save_psw(NormalUser normalUser);
	/**
	 * 根据用户ID封禁用户
	 * @param normalUser 将用户ID封装到对象中
	 * @return
	 */
	public boolean ban(NormalUser normalUser);
}
