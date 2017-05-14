package music.dao;

import java.util.List;

import music.vo.Pager;
import music.vo.Singer;
import music.vo.SingerAllInfo;

public interface SingerDao {

	/**
	 * 保存singer对象到数据库
	 * @param singer 要保存的对象
	 * @return 保存成功返回true，反之返回false
	 */
	public boolean save(Singer singer);
	
	/**
	 * 删除singer对象
	 * @param singer 根据id删除
	 * @return 删除成功返回true，反之返回false
	 */
	public boolean delete(Singer singer);
	
	/**
	 * 修改singer对象到数据库
	 * @param singer 要修改的对象，根据id识别
	 * @return 修改成功返回true，反之返回false
	 */
	public boolean update(Singer singer);
	/**
	 * 根据查询条件，查询歌手分页信息
	 * @param searchModel 封装查询条件
	 * @param pageNum 查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return
	 */
	public Pager<Singer> findSingers(Singer searchModel, int pageNum, int pageSize);
	/**
	 * 根据查询条件，查询所有的歌手信息
	 * @param searchModel 封装查询条件
	 * @return Singer对象列表
	 */
	public List<Singer> findAllSingers();
	/**
	 * 查询该歌手所有相关信息
	 * @param singer 歌手ID封装在Singer对象中
	 * @return
	 */
	public SingerAllInfo findSingerInfo(Singer singer);
}
