package music.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import music.dao.RecommendDao;
import music.dao.impl.RecommendDaoImpl;
import music.vo.Recommend;
import music.vo.Song;

/**
 * Servlet implementation class RecommendServlet
 */
@WebServlet("/RecommendServlet")
public class RecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String info = "";
	private RecommendDao recommendDao = new RecommendDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		info = request.getParameter("info");
		
		// 添加推荐歌曲
		if (info.equals("add")){
			this.add(request, response);
		}
		// 移除推荐歌曲
		if (info.equals("remove")){
			this.remove(request, response);
		}
		// 根据页数查看所有歌曲
		if (info.equals("find")){
			this.findAll(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// 添加推荐歌曲
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int songId = Integer.parseInt(request.getParameter("songId"));
		
		Song songData = new Song();
		songData.setSongId(songId);
		
		if (recommendDao.add(songData)){
			request.setAttribute("message", "推荐歌曲成功！");
		} else {
			request.setAttribute("message", "推荐歌曲失败！");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/recommend.jsp").forward(request, response);
	}
	// 移除推荐歌曲
	protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int recmdId = Integer.parseInt(request.getParameter("recmdId"));
		
		Recommend recmd = new Recommend();
		recmd.setRecmdId(recmdId);
		
		if (recommendDao.remove(recmd)){
			request.setAttribute("message", "移除成功！");
		} else {
			request.setAttribute("message", "移除失败！");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/recommend.jsp").forward(request, response);
	}
	// 根据页数查看所有歌曲
	protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Recommend> recommends = recommendDao.findAll();
		
		// 返回结果到页面
		request.setAttribute("recommends", recommends);
		request.getRequestDispatcher("admin/recommend.jsp").forward(request, response);
	}
}
