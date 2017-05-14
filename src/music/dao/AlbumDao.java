package music.dao;

import java.util.List;

import music.vo.Album;
import music.vo.AlbumAllInfo;
import music.vo.Pager;

public interface AlbumDao {

	/**
	 * 保存album对象到数据库
	 * @param album 要保存的对象
	 * @return 保存成功返回true，反之返回false
	 */
	public boolean save(Album album);
	
	/**
	 * 删除album对象
	 * @param album 根据id删除
	 * @return 删除成功返回true，反之返回false
	 */
	public boolean delete(Album album);
	
	/**
	 * 修改album对象到数据库
	 * @param album 要修改的对象，根据id识别
	 * @return 修改成功返回true，反之返回false
	 */
	public boolean update(Album album);
	/**
	 * 根据查询条件，查询专辑分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return
	 */
	public Pager<Album> findAlbums(Album searchModel, int pageNum, int pageSize);
	/**
	 * 根据查询条件，查询所有的专辑信息
	 * @param searchModel 封装查询条件
	 * @return Album对象列表
	 */
	public List<Album> findAllAlbums();
	/**
	 * 根据歌曲ID查询一首歌曲的所有相关信息
	 * @param song 歌曲ID封装在Song对象中
	 * @return 返回歌曲所有信息对象
	 */
	public AlbumAllInfo findAlbumInfo(Album album);
}
