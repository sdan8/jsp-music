package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.dao.AlbumDao;
import music.util.JdbcUtil;
import music.vo.Album;
import music.vo.AlbumAllInfo;
import music.vo.Pager;
import music.vo.Singer;
import music.vo.Song;

public class AlbumDaoImpl implements AlbumDao {

	/**
	 * 保存album对象到数据库
	 * @param album 要保存的对象
	 * @return 保存成功返回true，反之返回false
	 */
	@Override
	public boolean save(Album album) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO album(singerId, albumTitle, albumPic, albumPubDate, albumPubCom) VALUES(?,?,?,?,?)";
		JdbcUtil jdbc = null;
		
		int singerId = album.getSingerId();
		String albumTitle = album.getAlbumTitle();
		String albumPic = album.getAlbumPic();
		Date albumPubDate = album.getAlbumPubDate();
		String albumPubCom = album.getAlbumPubCom();
		
		params.add(singerId);
		if (albumTitle != null && !"".equals(albumTitle)){
			params.add(albumTitle);
		} else {
			return result;
		}
		if (albumPic != null && !"".equals(albumPic)){
			params.add(albumPic);
		} else {
			return result;
		}
		params.add(albumPubDate);
		if (albumPubCom != null && !"".equals(albumPubCom)){
			params.add(albumPubCom);
		} else {
			return result;
		}
		System.out.println("保存专辑开始迭代！");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("保存专辑迭代结束");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// 如果保存成功则返回结果为true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("保存专辑异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("保存专辑成功！");
		return result;
	}

	/**
	 * 删除album对象
	 * @param album 根据id删除
	 * @return 删除成功返回true，反之返回false
	 */
	@Override
	public boolean delete(Album album) {
		boolean result = false;
		
		String sql = "DELETE FROM album WHERE albumId=?";
		List<Object> params = new ArrayList<Object>();
		JdbcUtil jdbc = null;
		
		params.add(album.getAlbumId());
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// 如果保存成功则返回结果为true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("删除专辑异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		
		return result;
	}

	/**
	 * 修改album对象到数据库
	 * @param album 要修改的对象，根据id识别
	 * @return 修改成功返回true，反之返回false
	 */
	@Override
	public boolean update(Album album) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		
		String sql = "UPDATE album SET singerId=?, albumTitle=?, albumPic=?, albumPubDate=?, albumPubCom=? WHERE albumId=?";
		JdbcUtil jdbc = null;
		
		int singerId = album.getSingerId();
		String albumTitle = album.getAlbumTitle();
		String albumPic = album.getAlbumPic();
		Date albumPubDate = album.getAlbumPubDate();
		String albumPubCom = album.getAlbumPubCom();
		int albumId = album.getAlbumId();
		
		params.add(singerId);
		if (albumTitle != null && !"".equals(albumTitle)){
			params.add(albumTitle);
		} else {
			return result;
		}
		if (albumPic != null && !"".equals(albumPic)){
			params.add(albumPic);
		} else {
			return result;
		}
		params.add(albumPubDate);
		if (albumPubCom != null && !"".equals(albumPubCom)){
			params.add(albumPubCom);
		} else {
			return result;
		}
		params.add(albumId);
		
		System.out.println("修改专辑开始迭代！");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("修改专辑迭代结束");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// 如果修改成功则返回结果为true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("修改专辑异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("修改专辑成功！");
		return result;
	}

	/**
	 * 根据查询条件，查询专辑分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return
	 */
	@Override
	public Pager<Album> findAlbums(Album searchModel, int pageNum, int pageSize) {
		Pager<Album> result = null;
		// 存放查询参数
		List<Object> paramList = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder("select * from album where 1=1");
		StringBuilder countSql = new StringBuilder("select count(albumId) as totalRecord from album where 1=1 ");

		// 起始索引
		int fromIndex	= pageSize * (pageNum -1);
		
		// 使用limit关键字，实现分页
		sql.append(" limit " + fromIndex + ", " + pageSize );
		
		// 存放所有查询出的学生对象
		List<Album> albumList = new ArrayList<Album>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // 获取数据库链接
			
			// 获取总记录数
			List<Map<String, Object>> countResult = jdbcUtil.findResult(countSql.toString(), paramList);
			Map<String, Object> countMap = countResult.get(0);
			int totalRecord = ((Number)countMap.get("totalRecord")).intValue();
			
			// 获取查询的专辑记录
			List<Map<String, Object>> albumResult = jdbcUtil.findResult(sql.toString(), paramList);
			if (albumResult != null) {
				for (Map<String, Object> map : albumResult) {
					Album s = new Album(map);
					albumList.add(s);
				}
			}
			
			//获取总页数
			int totalPage = totalRecord / pageSize;
			if(totalRecord % pageSize !=0){
				totalPage++;
			}
			
			// 组装pager对象
			result = new Pager<Album>(pageSize, pageNum, totalRecord, totalPage, albumList);
			
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // 一定要释放资源
			}
		}
		return result;
	}

	@Override
	public List<Album> findAllAlbums() {

		// 存放查询参数
		List<Object> paramList = new ArrayList<Object>();
		
		String sql = "SELECT * FROM album WHERE 1=1";
		
		// 存放所有查询出的歌手对象
		List<Album> albumList = new ArrayList<Album>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // 获取数据库链接
			
			// 获取总记录数
			List<Map<String, Object>> queryResultList = jdbcUtil.findResult(sql, paramList);
			if (queryResultList != null){
				for (Map<String, Object> map : queryResultList) {
					Album a = new Album(map);
					albumList.add(a);
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // 一定要释放资源
			}
		}
		return albumList;
	}

	
	/**
	 * 根据专辑ID查询一个专辑的所有相关信息
	 * @param song 专辑ID封装在Album对象中
	 * @return 返回专辑所有信息对象
	 */
	@Override
	public AlbumAllInfo findAlbumInfo(Album album) {
		
		int albumId = album.getAlbumId();
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(albumId);
		
		String sql = "SELECT album.albumId, album.albumTitle, album.albumPic, album.albumPubDate, album.albumPubCom, "
				+ "singer.singerId, singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction "
				+ "FROM album, singer "
				+ "WHERE album.albumId=? AND album.singerId=singer.singerId";
		String sqlSongs = "SELECT song.songId, song.songTitle, song.songPlaytimes, song.songDldtimes, song.songFile "
				+ "FROM album, song "
				+ "WHERE song.albumId=album.albumId AND album.albumId=?";
		
		
		AlbumAllInfo albumAllInfo = new AlbumAllInfo();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // 获取数据库链接
			
			// 获取总记录数
			List<Map<String, Object>> queryResultList = jdbcUtil.findResult(sql, paramList);
			List<Map<String, Object>> querySongsResult = jdbcUtil.findResult(sqlSongs, paramList);
			if (queryResultList != null && !queryResultList.isEmpty()){
				Map<String, Object> map = queryResultList.get(0);
				String albumTitle = (String) map.get("albumTitle");
				String albumPic = (String) map.get("albumPic");
				Date albumPubDate = (Date) map.get("albumPubDate");
				String albumPubCom = (String) map.get("albumPubCom");
				int singerId = (int) map.get("singerId");
				String singerName = (String) map.get("singerName");
				int singerSex = (int) map.get("singerSex");
				String singerThumbnail = (String) map.get("singerThumbnail");
				String singerIntroduction = (String) map.get("singerIntroduction");
				
				
				
				
				Album resultAlbum = new Album(albumId, singerId, albumTitle, albumPic, albumPubDate, albumPubCom);
				Singer resultSinger = new Singer(singerId, singerName, singerSex, singerThumbnail, singerIntroduction);
				List<Song> resultSongs = new ArrayList<Song>();
				if (querySongsResult != null){
					for (Map<String, Object> mapSong : querySongsResult) {
						int songId = (int) mapSong.get("songId");
						String songTitle = (String) mapSong.get("songTitle");
						int songPlaytimes = (int) mapSong.get("songPlaytimes");
						int songDldtimes = (int) mapSong.get("songDldtimes");
						String songFile = (String) mapSong.get("songFile");
						
						Song s = new Song(songId, singerId, albumId, songTitle, songPlaytimes, songDldtimes, songFile);
						resultSongs.add(s);
					}
				}
				albumAllInfo.setSongs(resultSongs);;
				albumAllInfo.setAlbum(resultAlbum);
				albumAllInfo.setSinger(resultSinger);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // 一定要释放资源
			}
		}
		
		return albumAllInfo;
		
	}

	
}
