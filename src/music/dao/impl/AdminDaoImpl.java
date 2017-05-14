package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.Constant;
import music.dao.AdminDao;
import music.util.JdbcUtil;
import music.vo.Admin;

/**
 * 管理员DAO实现类
 * @author 帅
 *
 */
public class AdminDaoImpl implements AdminDao {

	/**
	 * 保存admin对象到数据库
	 * @param admin 要保存的对象
	 * @return 保存成功返回true，反之返回false
	 */
	@Override
	public boolean save(Admin admin) {
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO admin(adminUsername, adminPassword, adminRegisterDate, adminLastDate) VALUES(?,?,?,?)";
		JdbcUtil jdbc = null;
		System.out.println("检查是否存在...");
		// 判断用户是否存在
		if (!isExist(admin)){
			
			String adminUsername = admin.getAdminUsername();
			String adminPassword = admin.getAdminPassword();
			Date adminRegisterDate = admin.getAdminRegisterDate();
			Date adminLastDate = admin.getAdminLastDate();
			
			System.out.println("用户不存在 : " + adminUsername);
			
			
			if (adminUsername != null && !"".equals(adminUsername)){
				params.add(adminUsername);
			} else {
				return false;
			}
			if (adminPassword != null && !"".equals(adminPassword)){
				params.add(adminPassword);
			} else {
				return false;
			}
			if (adminRegisterDate != null){
				params.add(adminRegisterDate);
			} else {
				params.add(Constant.DEFAULT_DATE);
			}
			if (adminLastDate != null){
				params.add(adminLastDate);
			} else {
				params.add(Constant.DEFAULT_DATE);
			}
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
				throw new RuntimeException("保存管理员异常！", e);
			} finally {
				if (jdbc != null) {
					jdbc.releaseConn();
				}
			}
		} else {
			return false;
		}
		System.out.println("成功！");
		return true;
	}

	/**
	 * 修改admin对象到数据库
	 * @param admin 要修改的对象
	 */
	@Override
	public boolean update(Admin admin) {
		int result = -1;
		List<Object> params = new ArrayList<Object>();
		String sql = "UPDATE admin SET adminPassword=? WHERE adminId=?";
		JdbcUtil jdbc = null;
		
		String adminPassword = admin.getAdminPassword();
		int adminId = admin.getAdminId();
		
		if (adminPassword != null && !"".equals(adminPassword)){
			params.add(adminPassword);
		} else {
			return false;
		}
		params.add(adminId);
		
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
			throw new RuntimeException("修改管理员信息异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
	
		return result > 0 ? true : false;
	}

	/**
	 * 管理员登录
	 * @param admin
	 * @return 登录成功返回管理员对象，反之返回null
	 */
	@Override
	public Admin login(Admin admin) {
		Admin result = null;
		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> mapList = null;
		String username = admin.getAdminUsername();
		String password = admin.getAdminPassword();
		
		JdbcUtil jdbc = null;
		
		StringBuilder sql = new StringBuilder("SELECT * FROM admin WHERE 1=1");
		if (username != null && !"".equals(username)){
			params.add(username);
			sql.append(" and adminUsername=?");
		} else {
			return null;
		}
		if (password != null && !"".equals(password)){
			params.add(password);
			sql.append(" and adminPassword=?");
		} else {
			return null;
		}
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			mapList = jdbc.findResult(sql.toString(), params);
			if (mapList != null && !mapList.isEmpty()){
				System.out.println("mapList : " + mapList.toString());
				result = new Admin(mapList.get(0));
			}
		} catch (SQLException e) {
			throw new RuntimeException("管理员登录异常！", e);
		} finally {
			if (jdbc != null){
				jdbc.releaseConn();
			}
		}
		
		return result;
	}
	
	/**
	 * 查询用户是否存在，根据用户名查询
	 * @param searchModel 查询的用户
	 * @return 存在返回true，反之返回false
	 */
	public static boolean isExist(Admin searchModel){

		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> mapList = null;
		String username = searchModel.getAdminUsername();
		JdbcUtil jdbc = null;
		
		StringBuilder sql = new StringBuilder("SELECT * FROM admin WHERE 1=1");
		if (username != null && !"".equals(username)){
			params.add(username);
			sql.append(" and adminUsername=?");
		}
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			mapList = jdbc.findResult(sql.toString(), params);
		} catch (SQLException e) {
			throw new RuntimeException("查询管理员是否存在异常！", e);
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

	
}
