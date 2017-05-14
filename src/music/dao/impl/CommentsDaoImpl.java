package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import music.dao.CommentsDao;
import music.util.JdbcUtil;
import music.vo.Comments;

public class CommentsDaoImpl implements CommentsDao {

	/**
	 * 保存comment对象到数据库
	 * @param comment 要保存的comment对象
	 * @return 保存成功返回true，反之返回false
	 */
	@Override
	public boolean save(Comments comment) {
		
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO comments(userId, songId, commentText, commentDate) VALUES(?,?,?,?)";
		JdbcUtil jdbc = null;
		
		int userId = comment.getUserId();
		int songId = comment.getSongId();
		String commentText = comment.getCommentText();
		Date commentDate = comment.getCommentDate();
		
		params.add(userId);
		params.add(songId);
		if (commentText != null && !"".equals(commentText)){
			params.add(commentText);
		} else {
			return result;
		}
		params.add(commentDate);
		
		System.out.println("保存评论开始迭代！");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("保存评论迭代结束");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// 如果保存成功则返回结果为true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("保存评论异常！", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("保存评论成功！");
		return result;
	}

}
