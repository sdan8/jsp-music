package music.dao;

import java.util.List;

import music.vo.NormalUserSong;

public interface NormalUserSongDao {

	/**
	 * 保存NormalUserSong对象到数据库
	 * @param normalUserSong
	 * @return 保存成功返回true，反之返回false
	 */
	public boolean save(NormalUserSong normalUserSong);
	
	/**
	 * 判断是否已经存在这条记录
	 * @param normalUserSong
	 * @return 存在返回true，反之返回false
	 */
	public boolean isfollow(NormalUserSong normalUserSong);
	/**
	 * 删除NormalUserSong对象到数据库
	 * @param normalUserSong
	 * @return 删除成功返回true，反之返回false
	 */
	public boolean delete(NormalUserSong normalUserSong);
	/**
	 * 根据用户ID返回当前用户的关注所有歌曲信息
	 * @param normalUserSong 将用户ID封装在这个对象中
	 * @return
	 */
	public List<NormalUserSong> findAllSong(NormalUserSong normalUserSong);
	
}
