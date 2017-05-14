package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import music.util.JdbcUtil;
import music.vo.NormalUserSong;
import music.vo.Singer;
import music.vo.Song;

public class NormalUserSongDaoImpl implements music.dao.NormalUserSongDao {

	/**
	 * 保存NormalUserSong对象到数据库
	 * @param normalUserSong
	 * @return 保存成功返回true，反之返回false
	 */
	@Override
	public boolean save(NormalUserSong normalUserSong) {
		
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO normalusersong(userId, songId) VALUES(?,?)";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSong.getUserId();
		int songId = normalUserSong.getSongId();
		
		params.add(userId);
		params.add(songId);
		
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
	 * @param normalUserSong
	 * @return 存在返回true，反之返回false
	 */
	@Override
	public boolean isfollow(NormalUserSong normalUserSong) {
		
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM normalusersong WHERE userId=? AND songId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSong.getUserId();
		int songId = normalUserSong.getSongId();
		
		params.add(userId);
		params.add(songId);
		
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
	 * 删除NormalUserSong对象到数据库
	 * @param normalUserSong 将userId和songId封装到这个对象中
	 * @return 删除成功返回true，反之返回false
	 */
	@Override
	public boolean delete(NormalUserSong normalUserSong) {
		
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "DELETE FROM normalusersong WHERE userId=? AND songId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSong.getUserId();
		int songId = normalUserSong.getSongId();
		
		params.add(userId);
		params.add(songId);
		
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
	 * @param normalUserSong 将用户ID封装在这个对象中
	 * @return
	 */
	@Override
	public List<NormalUserSong> findAllSong(NormalUserSong normalUserSong) {
		
		List<NormalUserSong> result = new ArrayList<NormalUserSong>();
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT song.songId, song.singerId, song.albumId, song.songTitle, song.songPlaytimes, song.songDldtimes, song.songFile, "
				+ "singer.singerName "
				+ "FROM normalusersong, song, singer "
				+ "WHERE normalusersong.userId=? AND song.songId=normalusersong.songId AND song.singerId=singer.singerId";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSong.getUserId();
		
		params.add(userId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			List<Map<String, Object>> queryResultList = jdbc.findResult(sql, params);
			if (queryResultList != null && !queryResultList.isEmpty()){
				// 该存在记录
				for (Map<String, Object> map : queryResultList) {
					int songId = (int) map.get("songId");
					int singerId = (int) map.get("singerId");
					int albumId = (int) map.get("albumId");
					String songTitle = (String) map.get("songTitle");
					int songPlaytimes = (int) map.get("songPlaytimes");
					int songDldtimes = (int) map.get("songDldtimes");
					String songFile = (String) map.get("songFile");
					String singerName = (String) map.get("singerName");
					
					Song song = new Song();
					song.setSongId(songId);
					song.setSingerId(singerId);
					song.setAlbumId(albumId);
					song.setSongTitle(songTitle);
					song.setSongDldtimes(songDldtimes);
					song.setSongPlaytimes(songPlaytimes);
					song.setSongFile(songFile);
					
					Singer singer = new Singer();
					singer.setSingerName(singerName);
					song.setSinger(singer);
					
					NormalUserSong nus = new NormalUserSong();
					nus.setSong(song);
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
