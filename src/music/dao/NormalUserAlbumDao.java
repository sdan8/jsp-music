package music.dao;

import java.util.List;

import music.vo.NormalUserAlbum;

public interface NormalUserAlbumDao {

	/**
	 * 保存NormalUserAlbum对象到数据库
	 * @param normalUserAlbum
	 * @return 保存成功返回true，反之返回false
	 */
	public boolean save(NormalUserAlbum normalUserAlbum);
	
	/**
	 * 判断是否已经存在这条记录
	 * @param normalUserAlbum
	 * @return 存在返回true，反之返回false
	 */
	public boolean isfollow(NormalUserAlbum normalUserAlbum);
	/**
	 * 删除NormalUserAlbum对象到数据库
	 * @param normalUserAlbum
	 * @return 删除成功返回true，反之返回false
	 */
	public boolean delete(NormalUserAlbum normalUserAlbum);

	/**
	 * 根据用户ID返回当前用户的关注所有专辑信息
	 * @param nus 将用户ID封装在这个对象中
	 * @return
	 */
	public List<NormalUserAlbum> findAllAlbum(NormalUserAlbum nus);
}
