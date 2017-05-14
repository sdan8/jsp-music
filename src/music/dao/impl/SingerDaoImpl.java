package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.dao.SingerDao;
import music.util.JdbcUtil;
import music.vo.Album;
import music.vo.Pager;
import music.vo.Singer;
import music.vo.SingerAllInfo;
import music.vo.Song;

public class SingerDaoImpl implements SingerDao {

	/**
	 * 保存singer对象到数据库
	 * @param singer 要保存的对象
	 * @return 保存成功返回true，反之返回false
	 */
	@Override
	public boolean save(Singer singer) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO singer(singerName, singerSex, singerThumbnail, singerIntroduction) VALUES(?,?,?,?)";
		JdbcUtil jdbc = null;
		
		String singerName = singer.getSingerName();
		int singerSex = singer.getSingerSex();
		String singerThumbnail = singer.getSingerThumbnail();
		String singerIntroduction = singer.getSingerIntroduction();
		
		if (singerName != null && !"".equals(singerName)){
			params.add(singerName);
		} else {
			return result;
		}
		params.add(singerSex);
		if (singerThumbnail != null && !"".equals(singerThumbnail)){
			params.add(singerThumbnail);
		} else {
			return result;
		}
		if (singerIntroduction != null && !"".equals(singerIntroduction)){
			params.add(singerIntroduction);
		} else {
			return result;
		}
		System.out.println("保存歌手开始迭代！");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("保存歌手迭代结束");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// 如果保存成功则返回结果为true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("保存歌手异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("保存歌手成功！");
		return result;
	}

	/**
	 * 删除singer对象
	 * @param singer 根据id删除
	 * @return 删除成功返回true，反之返回false
	 */
	@Override
	public boolean delete(Singer singer) {
		boolean result = false;
		
		String sql = "DELETE FROM singer WHERE singerId=?";
		List<Object> params = new ArrayList<Object>();
		JdbcUtil jdbc = null;
		
		params.add(singer.getSingerId());
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// 如果保存成功则返回结果为true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("删除歌手异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		
		return result;
	}

	/**
	 * 修改singer对象到数据库
	 * @param singer 要修改的对象，根据id识别
	 * @return 修改成功返回true，反之返回false
	 */
	@Override
	public boolean update(Singer singer) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		
		String sql = "UPDATE singer SET singerName=?, singerSex=?, singerThumbnail=?, singerIntroduction=? WHERE singerId=?";
		JdbcUtil jdbc = null;
		
		int singerId = singer.getSingerId();
		String singerName = singer.getSingerName();
		int singerSex = singer.getSingerSex();
		String singerThumbnail = singer.getSingerThumbnail();
		String singerIntroduction = singer.getSingerIntroduction();
		
		if (singerName != null && !"".equals(singerName)){
			params.add(singerName);
		} else {
			return result;
		}
		params.add(singerSex);
		if (singerThumbnail != null && !"".equals(singerThumbnail)){
			params.add(singerThumbnail);
		} else {
			return result;
		}
		if (singerIntroduction != null && !"".equals(singerIntroduction)){
			params.add(singerIntroduction);
		} else {
			return result;
		}
		params.add(singerId);
		
		System.out.println("修改歌手开始迭代！");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("修改歌手迭代结束");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// 如果修改成功则返回结果为true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("修改歌手异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("修改歌手成功！");
		return result;
	}

	/**
	 * 根据查询条件，查询歌手分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return
	 */
	@Override
	public Pager<Singer> findSingers(Singer searchModel, int pageNum, int pageSize) {
		Pager<Singer> result = null;
		// 存放查询参数
		List<Object> paramList = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder("select * from singer where 1=1");
		StringBuilder countSql = new StringBuilder("select count(singerId) as totalRecord from singer where 1=1 ");

		// 起始索引
		int fromIndex	= pageSize * (pageNum -1);
		
		// 使用limit关键字，实现分页
		sql.append(" limit " + fromIndex + ", " + pageSize );
		
		// 存放所有查询出的学生对象
		List<Singer> singerList = new ArrayList<Singer>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // 获取数据库链接
			
			// 获取总记录数
			List<Map<String, Object>> countResult = jdbcUtil.findResult(countSql.toString(), paramList);
			Map<String, Object> countMap = countResult.get(0);
			int totalRecord = ((Number)countMap.get("totalRecord")).intValue();
			
			// 获取查询的歌手记录
			List<Map<String, Object>> studentResult = jdbcUtil.findResult(sql.toString(), paramList);
			if (studentResult != null) {
				for (Map<String, Object> map : studentResult) {
					Singer s = new Singer(map);
					singerList.add(s);
				}
			}
			
			//获取总页数
			int totalPage = totalRecord / pageSize;
			if(totalRecord % pageSize !=0){
				totalPage++;
			}
			
			// 组装pager对象
			result = new Pager<Singer>(pageSize, pageNum, 
							totalRecord, totalPage, singerList);
			
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
	 * 根据查询条件，查询所有的歌手信息
	 * @return Singer对象列表
	 */
	@Override
	public List<Singer> findAllSingers() {
		
		// 存放查询参数
		List<Object> paramList = new ArrayList<Object>();
		
		String sql = "SELECT * FROM singer WHERE 1=1";
		
		// 存放所有查询出的歌手对象
		List<Singer> singerList = new ArrayList<Singer>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // 获取数据库链接
			
			// 获取总记录数
			List<Map<String, Object>> queryResultList = jdbcUtil.findResult(sql, paramList);
			if (queryResultList != null){
				for (Map<String, Object> map : queryResultList) {
					Singer s = new Singer(map);
					singerList.add(s);
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // 一定要释放资源
			}
		}
		return singerList;
	}

	/**
	 * 查询该歌手所有相关信息
	 * @param singer 歌手ID封装在Singer对象中
	 * @return
	 */
	@Override
	public SingerAllInfo findSingerInfo(Singer singer) {
		
		int singerId = singer.getSingerId();
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(singerId);
		
		String sqlSongs = "SELECT singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction, "
				+ "song.songId, song.songTitle, song.songPlaytimes, song.songDldtimes,song.songFile "
				+ "FROM singer, song "
				+ "WHERE singer.singerId=song.singerId AND singer.singerId=?";
		String sqlAlbums = "SELECT album.albumId, album.albumTitle, album.albumPic, album.albumPubDate, album.albumPubCom "
				+ "FROM singer, album "
				+ "WHERE singer.singerId=album.singerId AND singer.singerId=?";
		
		
		SingerAllInfo singerAllInfo = new SingerAllInfo();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // 获取数据库链接
			
			// 获取总记录数
			List<Map<String, Object>> queryResultList = jdbcUtil.findResult(sqlSongs, paramList);
			List<Map<String, Object>> queryAlbumsResult = jdbcUtil.findResult(sqlAlbums, paramList);
			
			List<Song> resultSongs = new ArrayList<Song>();
			List<Album> resultAlbums = new ArrayList<Album>();
			if (queryResultList != null && !queryResultList.isEmpty()){
				Map<String, Object> map = queryResultList.get(0);
				
				String singerName = (String) map.get("singerName");
				int singerSex = (int) map.get("singerSex");
				String singerThumbnail = (String) map.get("singerThumbnail");
				String singerIntroduction = (String) map.get("singerIntroduction");
				Singer sin = new Singer(singerId, singerName, singerSex, singerThumbnail, singerIntroduction);
				singerAllInfo.setSinger(sin);
				
				for (Map<String, Object> mapSong : queryResultList) {
					int songId = (int) mapSong.get("songId");
					String songTitle = (String) mapSong.get("songTitle");
					int songPlaytimes = (int) mapSong.get("songPlaytimes");
					int songDldtimes = (int) mapSong.get("songDldtimes");
					String songFile = (String) mapSong.get("songFile");
					
					Song s = new Song();
					s.setSongId(songId);
					s.setSongTitle(songTitle);
					s.setSongPlaytimes(songPlaytimes);
					s.setSongDldtimes(songDldtimes);
					s.setSongFile(songFile);
					resultSongs.add(s);
				}
			}
			if (queryAlbumsResult != null && !queryAlbumsResult.isEmpty()){
				for (Map<String, Object> mapAlbum : queryAlbumsResult) {
					
					int albumId = (int) mapAlbum.get("albumId");
					String albumTitle = (String) mapAlbum.get("albumTitle");
					String albumPic = (String) mapAlbum.get("albumPic");
					Date albumPubDate = (Date) mapAlbum.get("albumPubDate");
					String albumPubCom = (String) mapAlbum.get("albumPubCom");
					
					Album a = new Album(albumId, singerId, albumTitle, albumPic, albumPubDate, albumPubCom);
					resultAlbums.add(a);
				}
				
			}
			singerAllInfo.setSongs(resultSongs);
			singerAllInfo.setAlbums(resultAlbums);
			
		} catch (SQLException e) {
			throw new RuntimeException("查询所有数据异常！", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // 一定要释放资源
			}
		}
		
		return singerAllInfo;
	}

}
