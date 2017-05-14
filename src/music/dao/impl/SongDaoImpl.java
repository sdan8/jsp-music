package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.dao.SongDao;
import music.util.JdbcUtil;
import music.vo.Album;
import music.vo.Comments;
import music.vo.Pager;
import music.vo.Singer;
import music.vo.Song;
import music.vo.SongAllInfo;

public class SongDaoImpl implements SongDao {

	/**
	 * 保存song对象到数据库
	 * @param song 要保存的对象
	 * @return 保存成功返回true，反之返回false
	 */
	@Override
	public boolean save(Song song) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO song(singerId, albumId, songTitle, songPlaytimes, songDldtimes, songFile) VALUES(?,?,?,?,?,?)";
		JdbcUtil jdbc = null;
		
		int singerId = song.getSingerId();
		int albumId = song.getAlbumId();
		String songTitle = song.getSongTitle();
		int songPlaytimes = song.getSongPlaytimes();
		int songDldtimes = song.getSongDldtimes();
		String songFile = song.getSongFile();
		
		params.add(singerId);
		params.add(albumId);
		if (songTitle != null && !"".equals(songTitle)){
			params.add(songTitle);
		} else {
			return result;
		}
		params.add(songPlaytimes);
		params.add(songDldtimes);
		if (songFile != null && !"".equals(songFile)){
			params.add(songFile);
		} else {
			return result;
		}
		
		System.out.println("保存歌曲开始迭代！");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("保存歌曲迭代结束");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// 如果保存成功则返回结果为true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("保存歌曲异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("保存歌曲成功！");
		return result;
	}

	/**
	 * 删除song对象
	 * @param song 根据id删除
	 * @return 删除成功返回true，反之返回false
	 */
	@Override
	public boolean delete(Song song) {
		boolean result = false;
		
		String sql = "DELETE FROM song WHERE songId=?";
		List<Object> params = new ArrayList<Object>();
		JdbcUtil jdbc = null;
		
		params.add(song.getSongId());
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// 如果保存成功则返回结果为true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("删除歌曲异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		
		return result;
	}

	/**
	 * 修改song对象到数据库
	 * @param song 要修改的对象，根据id识别
	 * @return 修改成功返回true，反之返回false
	 */
	@Override
	public boolean update(Song song) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		
		String sql = "UPDATE song SET singerId=?, albumId=?, songTitle=?, songFile=? WHERE songId=?";
		JdbcUtil jdbc = null;
		
		int singerId = song.getSingerId();
		int albumId = song.getAlbumId();
		String songTitle = song.getSongTitle();
		int songId = song.getSongId();
		String songFile = song.getSongFile();
		
		params.add(singerId);
		params.add(albumId);
		if (songTitle != null && !"".equals(songTitle)){
			params.add(songTitle);
		} else {
			return result;
		}
		if (songFile != null && !"".equals(songFile)){
			params.add(songFile);
		} else {
			return result;
		}
		params.add(songId);
		
		System.out.println("修改歌曲开始迭代！");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("修改歌曲迭代结束");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// 如果修改成功则返回结果为true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("修改歌曲异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("修改歌曲成功！");
		return result;
	}

	/**
	 * 根据查询条件，查询歌曲分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return
	 */
	@Override
	public Pager<Song> findSongs(Song searchModel, int pageNum, int pageSize) {
		
		Pager<Song> result = null;
		// 存放查询参数
		List<Object> paramList = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder("select * from song where 1=1");
		StringBuilder countSql = new StringBuilder("select count(songId) as totalRecord from song where 1=1 ");

		// 起始索引
		int fromIndex	= pageSize * (pageNum -1);
		
		// 使用limit关键字，实现分页
		sql.append(" limit " + fromIndex + ", " + pageSize );
		
		// 存放所有查询出的歌曲对象
		List<Song> songList = new ArrayList<Song>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // 获取数据库链接
			
			// 获取总记录数
			List<Map<String, Object>> countResult = jdbcUtil.findResult(countSql.toString(), paramList);
			Map<String, Object> countMap = countResult.get(0);
			int totalRecord = ((Number)countMap.get("totalRecord")).intValue();
			
			// 获取查询的专辑记录
			List<Map<String, Object>> songResult = jdbcUtil.findResult(sql.toString(), paramList);
			if (songResult != null) {
				for (Map<String, Object> map : songResult) {
					Song s = new Song(map);
					songList.add(s);
				}
			}
			
			//获取总页数
			int totalPage = totalRecord / pageSize;
			if(totalRecord % pageSize !=0){
				totalPage++;
			}
			
			// 组装pager对象
			result = new Pager<Song>(pageSize, pageNum, totalRecord, totalPage, songList);
			
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // 一定要释放资源
			}
		}
		return result;
	}

	/**
	 * 根据歌曲ID查询一首歌曲的所有相关信息
	 * @param song 歌曲ID封装在Song对象中
	 * @return 返回歌曲所有信息对象
	 */
	@Override
	public SongAllInfo findSongInfo(Song song) {
		int songId = song.getSongId();
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(songId);
		
		String sql = "SELECT song.songId, song.songTitle, song.songPlaytimes, song.songDldtimes, song.songFile, "
				+ "album.albumId, album.albumTitle, album.albumPic, album.albumPubDate, album.albumPubCom, "
				+ "singer.singerId, singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction "
				+ "FROM song, album, singer "
				+ "WHERE song.songId=? AND song.albumId=album.albumId AND song.singerId=singer.singerId";
		String sqlComments = "SELECT comments.commentId, comments.commentText, comments.commentDate, "
				+ "normaluser.userId, normaluser.userAvatar, normaluser.userNickname, "
				+ "songId "
				+ "FROM comments, normaluser "
				+ "WHERE comments.songId=? AND normaluser.userId=comments.userId";
		// 存放查询出的SongAllInfo对象
		SongAllInfo songAllInfo = new SongAllInfo();
		
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // 获取数据库链接
			
			// 获取总记录数
			List<Map<String, Object>> queryResultList = jdbcUtil.findResult(sql, paramList);
			List<Map<String, Object>> queryCommentsResult = jdbcUtil.findResult(sqlComments, paramList);
			if (queryResultList != null){
				Map<String, Object> map = queryResultList.get(0);
				String songTitle = (String) map.get("songTitle");
				int songPlaytimes = (int) map.get("songPlaytimes");
				int songDldtimes = (int) map.get("songDldtimes");
				String songFile = (String) map.get("songFile");
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
				
				Song resultSong = new Song(songId, singerId, albumId, songTitle, songPlaytimes, songDldtimes, songFile);
				Album resultAlbum = new Album(albumId, singerId, albumTitle, albumPic, albumPubDate, albumPubCom);
				Singer resultSinger = new Singer(singerId, singerName, singerSex, singerThumbnail, singerIntroduction);
				List<Comments> resultComments = new ArrayList<Comments>();
				if (queryCommentsResult != null){
					for (Map<String, Object> mapCmt : queryCommentsResult) {
						Comments s = new Comments(mapCmt);
						resultComments.add(s);
					}
				}
				songAllInfo.setSong(resultSong);
				songAllInfo.setAlbum(resultAlbum);
				songAllInfo.setSinger(resultSinger);
				songAllInfo.setComments(resultComments);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // 一定要释放资源
			}
		}
		
		return songAllInfo;
	}

	/**
	 * 根据歌曲ID记录下载歌曲次数
	 * @param song
	 */
	@Override
	public void downloadSong(Song song) {
		int songId = song.getSongId();
		
		List<Object> params = new ArrayList<Object>();
		
		String sql = "UPDATE song SET songDldtimes=songDldtimes+1 WHERE songId=?";
		JdbcUtil jdbc = null;
		
		params.add(songId);
		
		System.out.println("下载歌曲开始迭代！");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("下载歌曲迭代结束");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			jdbc.updateByPreparedStatement(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException("下载歌曲异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("下载歌曲成功！");
	}

	
	/**
	 * 返回歌曲下载排行前十
	 * @return
	 */
	@Override
	public List<Song> downloadRank() {
		
		// 存放查询参数
		List<Object> paramList = new ArrayList<Object>();
		
		String sql = "SELECT songId, songTitle, songDldtimes FROM song ORDER BY songDldtimes DESC LIMIT 0,10";

		List<Song> songList = new ArrayList<Song>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // 获取数据库链接
			// 获取查询的专辑记录
			List<Map<String, Object>> songResult = jdbcUtil.findResult(sql, paramList);
			if (songResult != null) {
				for (Map<String, Object> map : songResult) {
					Song s = new Song();
					s.setSongId((int) map.get("songId"));
					s.setSongTitle((String) map.get("songTitle"));
					s.setSongDldtimes((int) map.get("songDldtimes"));
					
					songList.add(s);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("查询下载排行榜异常！", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // 一定要释放资源
			}
		}
		return songList;
	}

	/**
	 * 根据songTitle模糊查询所有Song对象
	 * @param song
	 * @return
	 */
	@Override
	public List<Song> findSongsByTitle(Song song) {
		
		String songLikeTitle = "%" + song.getSongTitle() + "%";
		
		// 存放查询参数
		List<Object> paramList = new ArrayList<Object>();
		
		String sql = "SELECT song.songId, song.songTitle, song.songPlaytimes, song.songDldtimes, song.songFile, "
				+ "singer.singerId, singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction "
				+ "FROM song, singer "
				+ "WHERE song.songTitle LIKE ? AND song.singerId=singer.singerId";

		paramList.add(songLikeTitle);
		List<Song> songList = new ArrayList<Song>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // 获取数据库链接
			// 获取查询的专辑记录
			List<Map<String, Object>> songResult = jdbcUtil.findResult(sql, paramList);
			if (songResult != null) {
				for (Map<String, Object> map : songResult) {
					Song s = new Song();
					s.setSongId((int) map.get("songId"));
					s.setSongTitle((String) map.get("songTitle"));
					s.setSongPlaytimes((int) map.get("songPlaytimes"));
					s.setSongDldtimes((int) map.get("songDldtimes"));
					s.setSongFile((String) map.get("songFile"));
					
					Singer singer = new Singer();
					singer.setSingerId((int) map.get("singerId"));
					singer.setSingerName((String) map.get("singerName"));
					singer.setSingerSex((int) map.get("singerSex"));
					singer.setSingerThumbnail((String) map.get("singerThumbnail"));
					singer.setSingerIntroduction((String) map.get("singerIntroduction"));
					
					s.setSinger(singer);
					songList.add(s);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("查询下载排行榜异常！", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // 一定要释放资源
			}
		}
		return songList;
	}

	
}
