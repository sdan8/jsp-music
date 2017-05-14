package music.dao;


import java.util.List;

import music.vo.Pager;
import music.vo.Song;
import music.vo.SongAllInfo;

public interface SongDao {

	/**
	 * 保存song对象到数据库
	 * @param song 要保存的对象
	 * @return 保存成功返回true，反之返回false
	 */
	public boolean save(Song song);
	
	/**
	 * 删除song对象
	 * @param song 根据id删除
	 * @return 删除成功返回true，反之返回false
	 */
	public boolean delete(Song song);
	
	/**
	 * 修改song对象到数据库
	 * @param song 要修改的对象，根据id识别
	 * @return 修改成功返回true，反之返回false
	 */
	public boolean update(Song song);
	/**
	 * 根据查询条件，查询歌曲分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return
	 */
	public Pager<Song> findSongs(Song searchModel, int pageNum, int pageSize);
	/**
	 * 根据歌曲ID查询一首歌曲的所有相关信息
	 * @param song 歌曲ID封装在Song对象中
	 * @return 返回歌曲所有信息对象
	 */
	public SongAllInfo findSongInfo(Song song);
	/**
	 * 根据歌曲ID记录下载歌曲次数
	 * @param song
	 */
	public void downloadSong(Song song);
	/**
	 * 返回歌曲下载排行前十
	 * @return
	 */
	public List<Song> downloadRank();
	/**
	 * 根据songTitle模糊查询所有Song对象
	 * @param song
	 * @return
	 */
	public List<Song> findSongsByTitle(Song song);
}
