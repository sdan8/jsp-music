package music.dao;

import java.util.List;

import music.vo.NewSong;
import music.vo.Song;

public interface NewSongDao {

	/**
	 * 添加推荐歌曲
	 * @param song
	 * @return
	 */
	public boolean add(Song song);
	/**
	 * 移除推荐歌曲
	 * @param recommend
	 * @return
	 */
	public boolean remove(NewSong recommend);
	/**
	 * 查询所有已推荐歌曲
	 * @return
	 */
	public List<NewSong> findAll();
}
