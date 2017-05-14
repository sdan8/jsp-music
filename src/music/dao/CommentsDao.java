package music.dao;

import music.vo.Comments;

public interface CommentsDao {

	/**
	 * 保存comment对象到数据库
	 * @param comment 要保存的comment对象
	 * @return 保存成功返回true，反之返回false
	 */
	public boolean save(Comments comment);
}
