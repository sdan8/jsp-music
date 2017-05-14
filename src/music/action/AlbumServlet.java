package music.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import music.dao.NormalUserAlbumDao;
import music.dao.SingerDao;
import music.dao.impl.AlbumDaoImpl;
import music.dao.impl.NormalUserAlbumDaoImpl;
import music.dao.impl.SingerDaoImpl;
import music.vo.Album;
import music.vo.AlbumAllInfo;
import music.vo.NormalUser;
import music.vo.NormalUserAlbum;
import music.vo.Pager;
import music.vo.Singer;

/**
 * Servlet implementation class AlbumServlet
 */
@WebServlet("/AlbumServlet")
public class AlbumServlet extends HttpServlet {
       
	private static final long serialVersionUID = -8349590988971902657L;
	
	private ServletConfig servletConfig;
	private String info = "";
	private AlbumDao albumDao = new AlbumDaoImpl();
	private SingerDao singerDao = new SingerDaoImpl();
	private NormalUserAlbumDao normalUserAlbumDao = new NormalUserAlbumDaoImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlbumServlet() {
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
		
		// 添加专辑
		if (info.equals("add")){
			this.album_add(request, response);
		}
		// 删除专辑
		if (info.equals("del")){
			this.album_del(request, response);
		}
		// 修改专辑
		if (info.equals("update")){
			this.album_update(request, response);
		}
		// 根据页数查看所有专辑
		if (info.equals("find")){
			this.findByPage(request, response);
		}
		// 跳转到当前专辑页面
		if (info.equals("play")){
			this.album_play(request, response);
		}
		// 收藏专辑
		if (info.equals("follow")){
			this.album_follow(request, response);
		}
		// 跳转到所有专辑页面
		if (info.equals("list")){
			this.album_list(request, response);
		}
		// 跳转到关注管理页面
		if (info.equals("followmgr")){
			this.album_followmgr(request, response);
		}
		// 取消关注
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

	// 添加专辑
	protected void album_add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//新建一个SmartUpload对象
			SmartUpload su = new SmartUpload();
			//上传初始化
			su.initialize(servletConfig, request, response);
			//限制每个上传文件的最大长度
			su.setMaxFileSize(1000000);
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
			String[] type = {"JPG","jpg"};
			// 判断上传文件的类型是否正确
			int place = java.util.Arrays.binarySearch(type, fileType);
			//判断文件扩展名是否正确
			if (place != -1){
				//判断该文件是否被选择
				if (!singleFile.isMissing()){
					
					//以系统时间作为上传文件名称，设置上传完整路径
					String fileName = String.valueOf(System.currentTimeMillis());
					String filedir = Constant.ALBUM_PATH + fileName + "." + singleFile.getFileExt();
					// 带后缀名保存与数据库中
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					//执行上传操作
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("上传至： " + filedir);
					
					
					String albumTitle = su.getRequest().getParameter("albumTitle");
					int singerId = Integer.parseInt(su.getRequest().getParameter("singerId"));
					String albumPubCom = su.getRequest().getParameter("albumPubCom");
					// 获取日期
					String albumPubDateString = su.getRequest().getParameter("albumPubDate");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date albumPubDate = sdf.parse(albumPubDateString);
					
					Album albumData = new Album();
					albumData.setAlbumTitle(albumTitle);
					albumData.setSingerId(singerId);
					albumData.setAlbumPubCom(albumPubCom);
					albumData.setAlbumPubDate(albumPubDate);
					albumData.setAlbumPic(saveFileName);
					
					AlbumDao albumDao = new AlbumDaoImpl();
					
					if (albumDao.save(albumData)){
						request.setAttribute("message", "成功添加专辑！");
					} else {
						request.setAttribute("message", "添加专辑失败！");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("添加专辑Servlet异常！", e);
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/albummgr.jsp").forward(request, response);
	}
	// 删除专辑
	protected void album_del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int albumId = Integer.parseInt(request.getParameter("albumId"));
		
		Album albumData = new Album();
		albumData.setAlbumId(albumId);
		
		AlbumDao albumDao = new AlbumDaoImpl();
		if (albumDao.delete(albumData)){
			request.setAttribute("message", "删除成功！");
		} else {
			request.setAttribute("message", "删除失败！");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/albummgr.jsp").forward(request, response);
	}
	// 修改专辑
	protected void album_update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//新建一个SmartUpload对象
			SmartUpload su = new SmartUpload();
			//上传初始化
			su.initialize(servletConfig, request, response);
			//限制每个上传文件的最大长度
			su.setMaxFileSize(1000000);
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
			String[] type = {"JPG","jpg"};
			// 判断上传文件的类型是否正确
			int place = java.util.Arrays.binarySearch(type, fileType);
			//判断文件扩展名是否正确
			if (place != -1){
				//判断该文件是否被选择
				if (!singleFile.isMissing()){
					
					//以系统时间作为上传文件名称，设置上传完整路径
					String fileName = String.valueOf(System.currentTimeMillis());
					String filedir = Constant.ALBUM_PATH + fileName + "." + singleFile.getFileExt();
					// 带后缀名保存与数据库中
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					//执行上传操作
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("上传至： " + filedir);
					
					int albumId = Integer.parseInt(su.getRequest().getParameter("albumId"));
					String albumTitle = su.getRequest().getParameter("albumTitle");
					int singerId = Integer.parseInt(su.getRequest().getParameter("singerId"));
					String albumPubCom = su.getRequest().getParameter("albumPubCom");
					// 获取日期
					String albumPubDateString = su.getRequest().getParameter("albumPubDate");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date albumPubDate = sdf.parse(albumPubDateString);
					
					Album albumData = new Album();
					albumData.setAlbumId(albumId);
					albumData.setAlbumTitle(albumTitle);
					albumData.setSingerId(singerId);
					albumData.setAlbumPubCom(albumPubCom);
					albumData.setAlbumPubDate(albumPubDate);
					albumData.setAlbumPic(saveFileName);
					
					AlbumDao albumDao = new AlbumDaoImpl();
					
					if (albumDao.update(albumData)){
						request.setAttribute("message", "修改专辑成功！");
					} else {
						request.setAttribute("message", "修改专辑失败！");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("修改专辑Servlet异常！", e);
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/albummgr.jsp").forward(request, response);
	}
	// 根据页数查看所有专辑
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
		Album searchModel = new Album();
		
		// 获取查询结果
		Pager<Album> result = albumDao.findAlbums(searchModel, pageNum, pageSize);
		
		List<Singer> singers = singerDao.findAllSingers();
		
		
		// 返回结果到页面
		request.setAttribute("result", result);
		request.setAttribute("singers", singers);
		System.out.println("result : " + result);
		request.getRequestDispatcher("admin/albummgr.jsp").forward(request, response);
	}
	// 跳转到当前专辑页面
	protected void album_play(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int playId = Integer.parseInt(request.getParameter("playId"));
		Album albumData = new Album();
		albumData.setAlbumId(playId);
		
		AlbumAllInfo albumAllInfo = albumDao.findAlbumInfo(albumData);
		
		System.out.println("设置albumInfo");
		request.setAttribute("albumInfo", albumAllInfo);
		System.out.println("跳转到song.jsp");
		System.out.println(request.getSession().getAttribute("login_flag"));
		System.out.println(request.getSession().getAttribute("admin_login_flag"));
		request.getRequestDispatcher("album.jsp").forward(request, response);
	}
	// 收藏专辑
	protected void album_follow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int albumId = Integer.parseInt(request.getParameter("albumId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		
		NormalUserAlbum nusData = new NormalUserAlbum();
		nusData.setAlbumId(albumId);
		nusData.setUserId(userId);
		
		// 判断是否已经收藏
		if (normalUserAlbumDao.isfollow(nusData)){
			if (normalUserAlbumDao.delete(nusData)){
				request.setAttribute("message", "取消收藏成功！");
			} else {
				request.setAttribute("message", "取消收藏失败！");
			}
		} else {
			if (normalUserAlbumDao.save(nusData)){
				request.setAttribute("message", "收藏成功！");
			} else {
				request.setAttribute("message", "收藏失败！");
			}
		}
		
		request.setAttribute("flag", true);
		request.getRequestDispatcher("AlbumServlet?info=play&playId=" + albumId).forward(request, response);
	}
	// 跳转到所有专辑页面
	protected void album_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Album> albumList = albumDao.findAllAlbums();
		request.setAttribute("albumList", albumList);
		request.getRequestDispatcher("albumlist.jsp").forward(request, response);
	}
	// 跳转到关注管理页面
	protected void album_followmgr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		NormalUser user = (NormalUser) session.getAttribute("user");
		int userId = user.getUserId();
		NormalUserAlbum nus = new NormalUserAlbum();
		nus.setUserId(userId);
		List<NormalUserAlbum> list = normalUserAlbumDao.findAllAlbum(nus);
		request.setAttribute("list", list);
		request.getRequestDispatcher("user/followAlbum.jsp").forward(request, response);
	}
	// 取消关注
	protected void remove_follow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int albumId = Integer.parseInt(request.getParameter("albumId"));
		HttpSession session = request.getSession();
		NormalUser user = (NormalUser) session.getAttribute("user");
		int userId = user.getUserId();
		NormalUserAlbum nuaData = new NormalUserAlbum();
		nuaData.setUserId(userId);
		nuaData.setAlbumId(albumId);
		if (!normalUserAlbumDao.delete(nuaData)){
			request.setAttribute("message", "删除失败！");
			request.setAttribute("flag", true);
		}
		request.getRequestDispatcher("AlbumServlet?info=followmgr").forward(request, response);
	}
}
