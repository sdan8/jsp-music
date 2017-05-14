package music.action;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import music.dao.CommentsDao;
import music.dao.impl.CommentsDaoImpl;
import music.vo.Comments;

/**
 * Servlet implementation class CommentsServlet
 */
@WebServlet("/CommentsServlet")
public class CommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String info = "";
	CommentsDao commentsDao = new CommentsDaoImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		info = request.getParameter("info");
		
		if (info.equals("add")){
			this.comment_Add(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	protected void comment_Add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String commentText = request.getParameter("commentText");
		int userId = Integer.parseInt(request.getParameter("userId"));
		int songId = Integer.parseInt(request.getParameter("songId"));
		
		Comments commentsData = new Comments();
		commentsData.setCommentText(commentText);
		commentsData.setUserId(userId);
		commentsData.setSongId(songId);
		commentsData.setCommentDate(new Date());
		System.out.println(commentsData.toString());
		
		if (commentsDao.save(commentsData)){
			request.setAttribute("message", "评论成功");
		} else {
			request.setAttribute("message", "评论失败");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("SongServlet?info=play&playId=" + songId).forward(request, response);
	}
}
