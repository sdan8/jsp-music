package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.dao.NormalUserAlbumDao;
import music.util.JdbcUtil;
import music.vo.Album;
import music.vo.NormalUserAlbum;
import music.vo.Singer;

public class NormalUserAlbumDaoImpl implements NormalUserAlbumDao {

	/**
	 * 保存NormalUserAlbum对象到数据库
	 * @param normalUserAlbum
	 * @return 保存成功返回true，反之返回false
	 */
	@Override
	public boolean save(NormalUserAlbum normalUserAlbum) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO normaluseralbum(userId, albumId) VALUES(?,?)";
		JdbcUtil jdbc = null;
		
		int userId = normalUserAlbum.getUserId();
		int albumId = normalUserAlbum.getAlbumId();
		
		params.add(userId);
		params.add(albumId);
		
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
	 * @param normalUserAlbum
	 * @return 存在返回true，反之返回false
	 */
	@Override
	public boolean isfollow(NormalUserAlbum normalUserAlbum) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM normaluseralbum WHERE userId=? AND albumId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserAlbum.getUserId();
		int albumId = normalUserAlbum.getAlbumId();
		
		params.add(userId);
		params.add(albumId);
		
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
	 * 删除NormalUserAlbum对象到数据库
	 * @param normalUserAlbum
	 * @return 删除成功返回true，反之返回false
	 */
	@Override
	public boolean delete(NormalUserAlbum normalUserAlbum) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "DELETE FROM normaluseralbum WHERE userId=? AND albumId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserAlbum.getUserId();
		int albumId = normalUserAlbum.getAlbumId();
		
		params.add(userId);
		params.add(albumId);
		
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
	 * 根据用户ID返回当前用户的关注所有专辑信息
	 * @param nus 将用户ID封装在这个对象中
	 * @return
	 */
	@Override
	public List<NormalUserAlbum> findAllAlbum(NormalUserAlbum nus) {
		List<NormalUserAlbum> result = new ArrayList<NormalUserAlbum>();
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT album.albumId, album.albumTitle, album.albumPic, album.albumPubDate, album.albumPubCom, "
				+ "singer.singerId, singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction "
				+ "FROM normaluseralbum, album, singer "
				+ "WHERE normaluseralbum.userId=? AND normaluseralbum.albumId=album.albumId AND album.singerId=singer.singerId";
		JdbcUtil jdbc = null;
		
		int userId = nus.getUserId();
		
		params.add(userId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			List<Map<String, Object>> queryResultList = jdbc.findResult(sql, params);
			if (queryResultList != null && !queryResultList.isEmpty()){
				// 该存在记录
				for (Map<String, Object> map : queryResultList) {
					
					
					
					int albumId = (int) map.get("albumId");
					String albumTitle = (String) map.get("albumTitle");
					String albumPic = (String) map.get("albumPic");
					Date albumPubDate = (Date) map.get("albumPubDate");
					String albumPubCom = (String) map.get("albumPubCom");
					
					int singerId = (int) map.get("singerId");
					String singerName = (String) map.get("singerName");
					int singerSex = (int) map.get("singerSex");
					String singerThumbnail = (String) map.get("singerThumbnail");
					String singerIntroduction = (String) map.get("singerIntroduction");
					
					Album album = new Album();
					album.setAlbumId(albumId);
					album.setAlbumTitle(albumTitle);
					album.setAlbumPic(albumPic);
					album.setAlbumPubDate(albumPubDate);
					album.setAlbumPubCom(albumPubCom);
					album.setSingerId(singerId);
					
					Singer singer = new Singer();
					singer.setSingerName(singerName);
					singer.setSingerId(singerId);
					singer.setSingerSex(singerSex);
					singer.setSingerThumbnail(singerThumbnail);
					singer.setSingerIntroduction(singerIntroduction);
					
					album.setSinger(singer);
					
					NormalUserAlbum n = new NormalUserAlbum();
					n.setAlbum(album);
					n.setAlbumId(albumId);
					n.setUserId(userId);
					
					result.add(n);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("查询所有收藏专辑异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		return result;
	}

}
