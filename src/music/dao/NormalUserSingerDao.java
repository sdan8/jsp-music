package music.dao;

import java.util.List;

import music.vo.NormalUserSinger;

public interface NormalUserSingerDao {

	/**
	 * 保存NormalUserSinger对象到数据库
	 * @param normalUserSinger
	 * @return 保存成功返回true，反之返回false
	 */
	public boolean save(NormalUserSinger normalUserSinger);
	
	/**
	 * 判断是否已经存在这条记录
	 * @param normalUserSinger
	 * @return 存在返回true，反之返回false
	 */
	public boolean isfollow(NormalUserSinger normalUserSinger);
	/**
	 * 删除NormalUserSinger对象到数据库
	 * @param normalUserSinger
	 * @return 删除成功返回true，反之返回false
	 */
	public boolean delete(NormalUserSinger normalUserSinger);
	/**
	 * 根据用户ID返回当前用户的关注所有歌曲信息
	 * @param normalUserSinger 将用户ID封装在这个对象中
	 * @return
	 */
	public List<NormalUserSinger> findAllSinger(NormalUserSinger normalUserSinger);
}
