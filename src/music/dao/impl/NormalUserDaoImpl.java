package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.Constant;
import music.dao.NormalUserDao;
import music.util.JdbcUtil;
import music.vo.NormalUser;

public class NormalUserDaoImpl implements NormalUserDao {

	@Override
	public boolean singup(NormalUser normalUser) {
		
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO normaluser(userName, userPassword, userNickname, userSex, userEmail, userAvatar, userRegisterDate, userLastDate, userStatus) VALUES(?,?,?,?,?,?,?,?,?)";
		JdbcUtil jdbc = null;
		System.out.println("检查是否存在...");
		// 判断用户是否存在
		if (!isExist(normalUser)){
			
			String userName = normalUser.getUserName();
			String userPassword = normalUser.getUserPassword();
			String userNickname = normalUser.getUserNickname();
			int userSex = normalUser.getUserSex();
			String userEmail = normalUser.getUserEmail();
			String userAvatar = normalUser.getUserAvatar();
			Date userRegisterDate = normalUser.getUserRegisterDate();
			Date userLastDate = normalUser.getUserLastDate();
			int userStatus = normalUser.getUserStatus();
			
			if (userName != null && !"".equals(userName)){
				params.add(userName);
			} else {
				return false;
			}
			if (userPassword != null && !"".equals(userPassword)){
				params.add(userPassword);
			} else {
				return false;
			}
			if (userNickname != null && !"".equals(userNickname)){
				params.add(userNickname);
			} else {
				return false;
			}
			params.add(userSex);
			if (userEmail != null && !"".equals(userEmail)){
				params.add(userEmail);
			} else {
				return false;
			}
			if (userAvatar != null && !"".equals(userAvatar)){
				params.add(userAvatar);
			} else {
				return false;
			}
			if (userRegisterDate != null){
				params.add(userRegisterDate);
			} else {
				params.add(Constant.DEFAULT_DATE);
			}
			if (userLastDate != null){
				params.add(userLastDate);
			} else {
				params.add(Constant.DEFAULT_DATE);
			}
			params.add(userStatus);
			
			System.out.println("开始迭代！");
			for (Object object : params) {
				System.out.println(object);
			}
			System.out.println("迭代结束");
			try {
				jdbc = new JdbcUtil();
				jdbc.getConnection();
				jdbc.updateByPreparedStatement(sql, params);
			} catch (SQLException e) {
				throw new RuntimeException("保存用户异常！", e);
			} finally {
				if (jdbc != null) {
					jdbc.releaseConn();
				}
			}
		} else {
			return false;
		}
		System.out.println("保存用户成功！");
		return true;
	}

	/**
	 * 登录用户
	 * @param normalUser 封装登录信息，包括用户名和密码
	 * @return 登录成功返回NormalUser对象，失败返回null
	 */
	@Override
	public NormalUser login(NormalUser normalUser) {
		NormalUser result = null;
		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> mapList = null;
		String username = normalUser.getUserName();
		String password = normalUser.getUserPassword();
		
		JdbcUtil jdbc = null;
		
		StringBuilder sql = new StringBuilder("SELECT * FROM normaluser WHERE 1=1");
		if (username != null && !"".equals(username)){
			params.add(username);
			sql.append(" and userName=?");
		} else {
			return null;
		}
		if (password != null && !"".equals(password)){
			params.add(password);
			sql.append(" and userPassword=?");
		} else {
			return null;
		}
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			mapList = jdbc.findResult(sql.toString(), params);
			if (mapList != null && !mapList.isEmpty()){
				System.out.println("mapList : " + mapList.toString());
				result = new NormalUser(mapList.get(0));
			}
		} catch (SQLException e) {
			throw new RuntimeException("用户登录异常！", e);
		} finally {
			if (jdbc != null){
				jdbc.releaseConn();
			}
		}
		
		return result;
	}
	
	/**
	 * 根据用户名验证用户是否存在
	 * @param normalUser 用户名封装在NormalUser对象中
	 * @return 如果存在返回true，反之返回false
	 */
	public boolean isExist(NormalUser normalUser){
		
		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> mapList = null;
		String username = normalUser.getUserName();
		JdbcUtil jdbc = null;
		
		StringBuilder sql = new StringBuilder("SELECT * FROM normaluser WHERE 1=1");
		if (username != null && !"".equals(username)){
			params.add(username);
			sql.append(" and userName=?");
		}
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			mapList = jdbc.findResult(sql.toString(), params);
		} catch (SQLException e) {
			throw new RuntimeException("查询用户是否存在异常！", e);
		} finally {
			if (jdbc != null){
				jdbc.releaseConn();
			}
		}
		for (Map<String, Object> map : mapList) {
			System.out.println(map);
		}
		return mapList.isEmpty() ? false : true;
		
	}

	@Override
	public boolean setting(NormalUser normalUser) {
		int result = -1;
		List<Object> params = new ArrayList<Object>();
		String sql = "UPDATE normaluser SET userNickname=?, userSex=? WHERE userId=?";
		JdbcUtil jdbc = null;
		
		String userNickname = normalUser.getUserNickname();
		int userSex = normalUser.getUserSex();
		int userId = normalUser.getUserId();
		
		if (userNickname != null && !"".equals(userNickname)){
			params.add(userNickname);
		} else {
			return false;
		}
		params.add(userSex);
		params.add(userId);
		
		System.out.println("修改基本信息开始迭代！");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("修改基本信息迭代结束");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				result = 1;
			}
		} catch (SQLException e) {
			throw new RuntimeException("修改基本信息异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
	
		return result > 0 ? true : false;
	}

	/**
	 * 保存用户头像
	 * @param normalUser
	 * @return
	 */
	@Override
	public boolean save_avatar(NormalUser normalUser) {
		
		int result = -1;
		List<Object> params = new ArrayList<Object>();
		String sql = "UPDATE normaluser SET userAvatar=? WHERE userId=?";
		JdbcUtil jdbc = null;
		
		String userAvatar = normalUser.getUserAvatar();
		int userId = normalUser.getUserId();
		
		if (userAvatar != null && !"".equals(userAvatar)){
			params.add(userAvatar);
		} else {
			return false;
		}
		params.add(userId);
		
		System.out.println("修改头像开始迭代！");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("修改头像迭代结束");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				result = 1;
			}
		} catch (SQLException e) {
			throw new RuntimeException("修改头像异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
	
		return result > 0 ? true : false;
	}

	/**
	 * 根据userId修改用户密码
	 * @param normalUser
	 * @return
	 */
	@Override
	public boolean save_psw(NormalUser normalUser) {
		
		int result = -1;
		List<Object> params = new ArrayList<Object>();
		String sql = "UPDATE normaluser SET userPassword=? WHERE userId=?";
		JdbcUtil jdbc = null;
		
		String userPassword = normalUser.getUserPassword();
		int userId = normalUser.getUserId();
		
		if (userPassword != null && !"".equals(userPassword)){
			params.add(userPassword);
		} else {
			return false;
		}
		params.add(userId);
		
		System.out.println("修改密码开始迭代！");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("修改密码迭代结束");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				result = 1;
			}
		} catch (SQLException e) {
			throw new RuntimeException("修改密码异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
	
		return result > 0 ? true : false;
	}

	/**
	 * 根据用户ID封禁用户
	 * @param normalUser 将用户ID封装到对象中
	 * @return
	 */
	@Override
	public boolean ban(NormalUser normalUser) {
		
		int result = -1;
		List<Object> params = new ArrayList<Object>();
		String sql = "UPDATE normaluser SET userStatus=? WHERE userId=?";
		JdbcUtil jdbc = null;
		
		int userStatus = Constant.USER_STATUS_BAN;
		int userId = normalUser.getUserId();
		
		params.add(userStatus);
		params.add(userId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				result = 1;
			}
		} catch (SQLException e) {
			throw new RuntimeException("封禁用户异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
	
		return result > 0 ? true : false;
	}

	
}
