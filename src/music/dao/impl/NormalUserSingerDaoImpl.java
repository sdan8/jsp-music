package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import music.dao.NormalUserSingerDao;
import music.util.JdbcUtil;
import music.vo.NormalUserSinger;
import music.vo.Singer;

public class NormalUserSingerDaoImpl implements NormalUserSingerDao {

	/**
	 * 保存NormalUserSinger对象到数据库
	 * @param normalUserSinger
	 * @return 保存成功返回true，反之返回false
	 */
	@Override
	public boolean save(NormalUserSinger normalUserSinger) {

		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO normalusersinger(userId, singerId) VALUES(?,?)";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSinger.getUserId();
		int singerId = normalUserSinger.getSingerId();
		
		params.add(userId);
		params.add(singerId);
		
		System.out.println("收藏歌曲开始迭代！");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("收藏歌曲迭代结束");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// 如果保存成功则返回结果为true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("收藏歌曲异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("收藏歌曲成功！");
		return result;
	}

	/**
	 * 判断是否已经存在这条记录
	 * @param normalUserSinger
	 * @return 存在返回true，反之返回false
	 */
	@Override
	public boolean isfollow(NormalUserSinger normalUserSinger) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM normalusersinger WHERE userId=? AND singerId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSinger.getUserId();
		int singerId = normalUserSinger.getSingerId();
		
		params.add(userId);
		params.add(singerId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			List<Map<String, Object>> queryResultList = jdbc.findResult(sql, params);
			if (queryResultList != null && !queryResultList.isEmpty()){
				// 该存在记录
				System.out.println("收藏存在！！");
				System.out.println(queryResultList);
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("查询收藏歌曲异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		return result;
	}

	/**
	 * 删除NormalUserSinger对象到数据库
	 * @param normalUserSinger
	 * @return 删除成功返回true，反之返回false
	 */
	@Override
	public boolean delete(NormalUserSinger normalUserSinger) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "DELETE FROM normalusersinger WHERE userId=? AND singerId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSinger.getUserId();
		int singerId = normalUserSinger.getSingerId();
		
		params.add(userId);
		params.add(singerId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// 如果保存成功则返回结果为true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("取消收藏歌曲异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("取消收藏歌曲成功！");
		return result;
	}

	/**
	 * 根据用户ID返回当前用户的关注所有歌曲信息
	 * @param normalUserSinger 将用户ID封装在这个对象中
	 * @return
	 */
	@Override
	public List<NormalUserSinger> findAllSinger(NormalUserSinger normalUserSinger) {
		List<NormalUserSinger> result = new ArrayList<NormalUserSinger>();
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT singer.singerId, singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction "
				+ "FROM singer, normalusersinger "
				+ "WHERE normalusersinger.singerId=singer.singerId AND normalusersinger.userId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSinger.getUserId();
		
		params.add(userId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			List<Map<String, Object>> queryResultList = jdbc.findResult(sql, params);
			if (queryResultList != null && !queryResultList.isEmpty()){
				// 该存在记录
				for (Map<String, Object> map : queryResultList) {
					int singerId = (int) map.get("singerId");
					String singerName = (String) map.get("singerName");
					int singerSex = (int) map.get("singerSex");
					String singerThumbnail = (String) map.get("singerThumbnail");
					String singerIntroduction = (String) map.get("singerIntroduction");
					
					
					Singer singer = new Singer();
					singer.setSingerId(singerId);
					singer.setSingerName(singerName);
					singer.setSingerSex(singerSex);
					singer.setSingerThumbnail(singerThumbnail);
					singer.setSingerIntroduction(singerIntroduction);
					
					NormalUserSinger nus = new NormalUserSinger();
					nus.setSinger(singer);
					nus.setSingerId(singerId);
					nus.setUserId(userId);
					result.add(nus);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("查询所有收藏歌曲异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		return result;
	}


}
