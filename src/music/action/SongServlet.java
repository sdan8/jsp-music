package music.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;

import music.Constant;
import music.dao.AlbumDao;
import music.dao.NormalUserSongDao;
import music.dao.SingerDao;
import music.dao.SongDao;
import music.dao.impl.AlbumDaoImpl;
import music.dao.impl.NormalUserSongDaoImpl;
import music.dao.impl.SingerDaoImpl;
import music.dao.impl.SongDaoImpl;
import music.vo.Album;
import music.vo.NormalUser;
import music.vo.NormalUserSong;
import music.vo.Pager;
import music.vo.Singer;
import music.vo.Song;
import music.vo.SongAllInfo;

/**
 * Servlet implementation class SongServlet
 */
@WebServlet("/SongServlet")
public class SongServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServletConfig servletConfig;
	
	private String info = "";
	private SongDao songDao = new SongDaoImpl();
	private SingerDao singerDao = new SingerDaoImpl();
	private AlbumDao albumDao = new AlbumDaoImpl();
	private NormalUserSongDao normalUserSongDao = new NormalUserSongDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SongServlet() {
        super();
    }

    /**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		this.servletConfig = config;
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		info = request.getParameter("info");
		
		// 添加歌曲
		if (info.equals("add")){
			this.song_add(request, response);
		}
		// 删除歌曲
		if (info.equals("del")){
			this.song_del(request, response);
		}
		// 修改歌曲
		if (info.equals("update")){
			this.song_update(request, response);
		}
		// 根据页数查看所有歌曲
		if (info.equals("find")){
			this.findByPage(request, response);
		}
		// 跳转到播放当前歌曲页面
		if (info.equals("play")){
			this.song_play(request, response);
		}
		// 下载当前歌曲
		if (info.equals("download")){
			this.song_download(request, response);
		}
		// 收藏当前歌曲
		if (info.equals("follow")){
			this.song_follow(request, response);
		}
		// 模糊搜索歌曲
		if (info.equals("search")){
			this.song_search(request, response);
		}
		// 跳转到关注管理页面
		if (info.equals("followmgr")){
			this.song_followmgr(request, response);
		}
		// 删除关注
		if (info.equals("removeFollow")){
			this.remove_follow(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	// 添加歌曲
	protected void song_add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//新建一个SmartUpload对象
			SmartUpload su = new SmartUpload();
			//上传初始化
			su.initialize(servletConfig, request, response);
			//限制每个上传文件的最大长度 10MB
			su.setMaxFileSize(10*1024*1024);
			//限制总上传数据的长度
//			su.setTotalMaxFileSize(200000);
			//设定允许上传的文件（通过扩展名限制），仅允许jpg，png文件
//			su.setAllowedFilesList("jpg,png");
			//设定禁止上传的文件（通过扩展名限制），禁止上传带有exe,bat,jsp,htm,html扩展名的文件和没有扩展名的文件
			su.setDeniedFilesList("exe,bat,jsp,htm,html");
			//上传文件
			su.upload();
			//获取上传的文件操作
			Files files = su.getFiles();
			//获取单个文件
			File singleFile = files.getFile(0);
			//获取上传文件的扩展名
			String fileType = singleFile.getFileExt();
			//设置上传文件的扩展名
			String[] type = {"MP3","mp3"};
			// 判断上传文件的类型是否正确
			int place = java.util.Arrays.binarySearch(type, fileType);
			//判断文件扩展名是否正确
			if (place != -1){
				//判断该文件是否被选择
				if (!singleFile.isMissing()){
					
					//以系统时间作为上传文件名称，设置上传完整路径
					String fileName = String.valueOf(System.currentTimeMillis());
					String filedir = Constant.MUSIC_PATH + fileName + "." + singleFile.getFileExt();
					// 带后缀名保存与数据库中
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					//执行上传操作
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("上传至： " + filedir);
					
					
					String songTitle = su.getRequest().getParameter("songTitle");
					int singerId = Integer.parseInt(su.getRequest().getParameter("singerId"));
					int albumId = Integer.parseInt(su.getRequest().getParameter("albumId"));
					
					Song songData = new Song();
					songData.setSingerId(singerId);
					songData.setAlbumId(albumId);
					songData.setSongTitle(songTitle);
					songData.setSongPlaytimes(0);
					songData.setSongDldtimes(0);
					songData.setSongFile(saveFileName);
					
					SongDao songDao = new SongDaoImpl();
					if (songDao.save(songData)){
						request.setAttribute("message", "成功添加歌曲！");
					} else {
						request.setAttribute("message", "添加歌曲失败！");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("添加歌曲Servlet异常！", e);
		}
		
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/songmgr.jsp").forward(request, response);
	}
	// 删除歌曲
	protected void song_del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int songId = Integer.parseInt(request.getParameter("songId"));
		
		Song songData = new Song();
		songData.setSongId(songId);;
		
		SongDao songDao = new SongDaoImpl();
		if (songDao.delete(songData)){
			request.setAttribute("message", "删除成功！");
		} else {
			request.setAttribute("message", "删除失败！");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/songmgr.jsp").forward(request, response);
	}
	// 修改歌曲
	protected void song_update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//新建一个SmartUpload对象
			SmartUpload su = new SmartUpload();
			//上传初始化
			su.initialize(servletConfig, request, response);
			//限制每个上传文件的最大长度 10MB
			su.setMaxFileSize(10*1024*1024);
			//限制总上传数据的长度
//			su.setTotalMaxFileSize(200000);
			//设定允许上传的文件（通过扩展名限制），仅允许jpg，png文件
//			su.setAllowedFilesList("jpg,png");
			//设定禁止上传的文件（通过扩展名限制），禁止上传带有exe,bat,jsp,htm,html扩展名的文件和没有扩展名的文件
			su.setDeniedFilesList("exe,bat,jsp,htm,html");
			//上传文件
			su.upload();
			//获取上传的文件操作
			Files files = su.getFiles();
			//获取单个文件
			File singleFile = files.getFile(0);
			//获取上传文件的扩展名
			String fileType = singleFile.getFileExt();
			//设置上传文件的扩展名
			String[] type = {"MP3","mp3"};
			// 判断上传文件的类型是否正确
			int place = java.util.Arrays.binarySearch(type, fileType);
			//判断文件扩展名是否正确
			if (place != -1){
				//判断该文件是否被选择
				if (!singleFile.isMissing()){
					
					//以系统时间作为上传文件名称，设置上传完整路径
					String fileName = String.valueOf(System.currentTimeMillis());
					String filedir = Constant.MUSIC_PATH + fileName + "." + singleFile.getFileExt();
					// 带后缀名保存与数据库中
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					//执行上传操作
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("上传至： " + filedir);
					
					int songId = Integer.parseInt(su.getRequest().getParameter("songId"));
					String songTitle = su.getRequest().getParameter("songTitle");
					int singerId = Integer.parseInt(su.getRequest().getParameter("singerId"));
					int albumId = Integer.parseInt(su.getRequest().getParameter("albumId"));
					
					Song songData = new Song();
					songData.setSongId(songId);
					songData.setSingerId(singerId);
					songData.setAlbumId(albumId);
					songData.setSongTitle(songTitle);
					songData.setSongPlaytimes(0);
					songData.setSongDldtimes(0);
					songData.setSongFile(saveFileName);
					
					SongDao songDao = new SongDaoImpl();
					if (songDao.update(songData)){
						request.setAttribute("message", "成功修改歌曲！");
					} else {
						request.setAttribute("message", "修改歌曲失败！");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("修改歌曲Servlet异常！", e);
		}
		
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/songmgr.jsp").forward(request, response);
	}
	// 根据页数查看所有歌曲
	protected void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNumStr = request.getParameter("pageNum"); 
		System.out.println("pageNumStr : " + pageNumStr);
		
		int pageNum = 1; //显示第几页数据
		if(pageNumStr!=null && !"".equals(pageNumStr.trim())){
			pageNum = Integer.parseInt(pageNumStr);
		}
		
		int pageSize = 10;  // 每页显示多少条记录
		String pageSizeStr = request.getParameter("pageSize");
		if(pageSizeStr!=null && !"".equals(pageSizeStr.trim())){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		// 组装查询条件
		Song searchModel = new Song();
		
		// 获取查询结果
		Pager<Song> result = songDao.findSongs(searchModel, pageNum, pageSize);
		
		List<Singer> singers = singerDao.findAllSingers();
		List<Album> albums = albumDao.findAllAlbums();
		
		// 返回结果到页面
		request.setAttribute("result", result);
		request.setAttribute("singers", singers);
		request.setAttribute("albums", albums);
		System.out.println("result : " + result);
		request.getRequestDispatcher("admin/songmgr.jsp").forward(request, response);
	}

	// 跳转到播放当前歌曲页面
	protected void song_play(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int playId = Integer.parseInt(request.getParameter("playId"));
		Song songData = new Song();
		songData.setSongId(playId);
		
		SongAllInfo songInfo = songDao.findSongInfo(songData);
		System.out.println("设置songInfo");
		request.setAttribute("songInfo", songInfo);
		System.out.println("跳转到song.jsp");
		System.out.println(request.getSession().getAttribute("login_flag"));
		System.out.println(request.getSession().getAttribute("admin_login_flag"));
		request.getRequestDispatcher("song.jsp").forward(request, response);
		
	}
	// 下载当前歌曲
	protected void song_download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getParameter("path");
		int songId = Integer.parseInt(request.getParameter("songId"));
		Song songData = new Song();
		songData.setSongId(songId);
		// 更新数据库下载次数
		songDao.downloadSong(songData);
		response.sendRedirect(path);
	}
	// 收藏当前歌曲
	protected void song_follow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int songId = Integer.parseInt(request.getParameter("songId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		NormalUserSong nusData = new NormalUserSong();
		nusData.setSongId(songId);
		nusData.setUserId(userId);
		
		// 判断是否已经收藏
		if (normalUserSongDao.isfollow(nusData)){
			if (normalUserSongDao.delete(nusData)){
				request.setAttribute("message", "取消收藏成功！");
			} else {
				request.setAttribute("message", "取消收藏失败！");
			}
		} else {
			if (normalUserSongDao.save(nusData)){
				request.setAttribute("message", "收藏成功！");
			} else {
				request.setAttribute("message", "收藏失败！");
			}
		}
		
		request.setAttribute("flag", true);
		request.getRequestDispatcher("SongServlet?info=play&playId=" + songId).forward(request, response);
	}
	
	protected void song_search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String songTitle = request.getParameter("songTitle");
		
		Song songData = new Song();
		songData.setSongTitle(songTitle);
		
		List<Song> songList = songDao.findSongsByTitle(songData);
		
		request.setAttribute("songList", songList);
		request.getRequestDispatcher("search.jsp").forward(request, response);
		
	}
	// 跳转到关注管理页面
	protected void song_followmgr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		NormalUser user = (NormalUser) session.getAttribute("user");
		int userId = user.getUserId();
		NormalUserSong data = new NormalUserSong();
		data.setUserId(userId);
		List<NormalUserSong> list = normalUserSongDao.findAllSong(data);
		request.setAttribute("list", list);
		request.getRequestDispatcher("user/followSong.jsp").forward(request, response);
	}
	protected void remove_follow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int songId = Integer.parseInt(request.getParameter("songId"));
		HttpSession session = request.getSession();
		NormalUser user = (NormalUser) session.getAttribute("user");
		int userId = user.getUserId();
		NormalUserSong nusData = new NormalUserSong();
		nusData.setUserId(userId);
		nusData.setSongId(songId);
		if (!normalUserSongDao.delete(nusData)){
			request.setAttribute("message", "删除失败！");
			request.setAttribute("flag", true);
		}
		request.getRequestDispatcher("SongServlet?info=followmgr").forward(request, response);
	}
}
