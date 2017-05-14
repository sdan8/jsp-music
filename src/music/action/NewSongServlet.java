package music.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import music.dao.NewSongDao;
import music.dao.impl.NewSongDaoImpl;
import music.vo.NewSong;
import music.vo.Song;

/**
 * Servlet implementation class NewSongServlet
 */
@WebServlet("/NewSongServlet")
public class NewSongServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String info = "";
	private NewSongDao newSongDao = new NewSongDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewSongServlet() {
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
	
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int songId = Integer.parseInt(request.getParameter("songId"));
		
		Song songData = new Song();
		songData.setSongId(songId);
		
		if (newSongDao.add(songData)){
			request.setAttribute("message", "添加歌曲成功！");
		} else {
			request.setAttribute("message", "添加歌曲失败！");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/newSong.jsp").forward(request, response);
	}
	protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int newSongId = Integer.parseInt(request.getParameter("newSongId"));
		
		NewSong newSong = new NewSong();
		newSong.setNewSongId(newSongId);
		
		if (newSongDao.remove(newSong)){
			request.setAttribute("message", "移除成功！");
		} else {
			request.setAttribute("message", "移除失败！");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/newSong.jsp").forward(request, response);
	}
	protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<NewSong> newSongs = newSongDao.findAll();
		
		request.setAttribute("newSongs", newSongs);
		request.getRequestDispatcher("admin/newSong.jsp").forward(request, response);
	}

}
