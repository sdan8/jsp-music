package music.dao;

import java.util.List;

import music.vo.Recommend;
import music.vo.Song;

public interface RecommendDao {

	/**
	 * 添加推荐歌曲
	 * @param song
	 * @return
	 */
	public boolean add(Song song);
	/**
	 * 移除推荐歌曲
	 * @param song
	 * @return
	 */
	public boolean remove(Recommend song);
	/**
	 * 查询所有已推荐歌曲
	 * @return
	 */
	public List<Recommend> findAll();
}
