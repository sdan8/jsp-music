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
import music.dao.NormalUserSingerDao;
import music.dao.SingerDao;
import music.dao.impl.NormalUserSingerDaoImpl;
import music.dao.impl.SingerDaoImpl;
import music.vo.NormalUser;
import music.vo.NormalUserSinger;
import music.vo.Pager;
import music.vo.Singer;
import music.vo.SingerAllInfo;

/**
 * Servlet implementation class SingerServlet
 */
@WebServlet("/SingerServlet")
public class SingerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServletConfig servletConfig;
	private String info = "";
	private SingerDao singerDao = new SingerDaoImpl();
	private NormalUserSingerDao normalUserSingerDao = new NormalUserSingerDaoImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SingerServlet() {
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
		
		// 添加歌手
		if (info.equals("add")){
			this.singer_add(request, response);
		}
		// 删除歌手
		if (info.equals("del")){
			this.singer_del(request, response);
		}
		// 修改歌手
		if (info.equals("update")){
			this.singer_update(request, response);
		}
		// 根据页数查看所有歌手
		if (info.equals("find")){
			this.findByPage(request, response);
		}
		// 跳转到歌手页面
		if (info.equals("play")){
			this.singer_play(request, response);
		}
		// 收藏歌手
		if (info.equals("follow")){
			this.singer_follow(request, response);
		}
		// 跳转到所有歌手页面
		if (info.equals("list")){
			this.singer_list(request, response);
		}
		// 跳转到关注管理页面
		if (info.equals("followmgr")){
			this.singer_followmgr(request, response);
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

	// 添加歌手
	protected void singer_add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
					String filedir = Constant.SINGER_PATH + fileName + "." + singleFile.getFileExt();
					// 带后缀名保存与数据库中
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					//执行上传操作
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("上传至： " + filedir);
					
					
					String singerName = su.getRequest().getParameter("singerName");
					int sex = Integer.parseInt(su.getRequest().getParameter("sex"));
					String singerIntroduction = su.getRequest().getParameter("singerIntroduction");
					
					Singer singerData = new Singer();
					singerData.setSingerName(singerName);
					singerData.setSingerSex(sex);
					singerData.setSingerIntroduction(singerIntroduction);
					singerData.setSingerThumbnail(saveFileName);
					
					SingerDao singDao = new SingerDaoImpl();
					if (singDao.save(singerData)){
						request.setAttribute("message", "成功添加歌手！");
					} else {
						request.setAttribute("message", "添加歌手失败！");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("添加歌手Servlet异常！", e);
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/singermgr.jsp").forward(request, response);
	}
	// 删除歌手
	protected void singer_del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int singerId = Integer.parseInt(request.getParameter("singerId"));
		
		Singer singerData = new Singer();
		singerData.setSingerId(singerId);
		
		SingerDao singerDao = new SingerDaoImpl();
		if (singerDao.delete(singerData)){
			request.setAttribute("message", "删除成功！");
		} else {
			request.setAttribute("message", "删除失败！");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/singermgr.jsp").forward(request, response);
	}
	// 修改歌手
	protected void singer_update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
					String filedir = Constant.SINGER_PATH + fileName + "." + singleFile.getFileExt();
					// 带后缀名保存与数据库中
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					//执行上传操作
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("上传至： " + filedir);
					
					int singerId = Integer.parseInt(su.getRequest().getParameter("singerId"));
					String singerName = su.getRequest().getParameter("singerName");
					int sex = Integer.parseInt(su.getRequest().getParameter("sex"));
					String singerIntroduction = su.getRequest().getParameter("singerIntroduction");
					
					
					Singer singerData = new Singer();
					singerData.setSingerId(singerId);
					singerData.setSingerName(singerName);
					singerData.setSingerSex(sex);
					singerData.setSingerIntroduction(singerIntroduction);
					singerData.setSingerThumbnail(saveFileName);
					
					SingerDao singDao = new SingerDaoImpl();
					if (singDao.update(singerData)){
						request.setAttribute("message", "修改歌手成功！");
					} else {
						request.setAttribute("message", "修改歌手失败！");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("修改歌手Servlet异常！", e);
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/singermgr.jsp").forward(request, response);
	}
	// 根据页数查看所有歌手
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
		Singer searchModel = new Singer();
		
		//调用service 获取查询结果
		Pager<Singer> result = singerDao.findSingers(searchModel, pageNum, pageSize);
		
		// 返回结果到页面
		request.setAttribute("result", result);
		System.out.println("result : " + result);
//		System.out.println("Title : " + result.getDataList().get(0).getSingerName());
		request.getRequestDispatcher("admin/singermgr.jsp").forward(request, response);
	}
	protected void singer_play(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int playId = Integer.parseInt(request.getParameter("playId"));
		Singer singerData = new Singer();
		singerData.setSingerId(playId);
		
		SingerAllInfo singerAllInfo = singerDao.findSingerInfo(singerData);
		System.out.println("sex : " + singerAllInfo.getSinger().getSingerSex());
		System.out.println("name : " + singerAllInfo.getSinger().getSingerName());
		request.setAttribute("singerInfo", singerAllInfo);
		request.getRequestDispatcher("singer.jsp").forward(request, response);
	}
	protected void singer_follow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int singerId = Integer.parseInt(request.getParameter("singerId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		
		NormalUserSinger nusData = new NormalUserSinger();
		nusData.setSingerId(singerId);
		nusData.setUserId(userId);
		
		// 判断是否已经收藏
		if (normalUserSingerDao.isfollow(nusData)){
			if (normalUserSingerDao.delete(nusData)){
				request.setAttribute("message", "取消收藏成功！");
			} else {
				request.setAttribute("message", "取消收藏失败！");
			}
		} else {
			if (normalUserSingerDao.save(nusData)){
				request.setAttribute("message", "收藏成功！");
			} else {
				request.setAttribute("message", "收藏失败！");
			}
		}
		
		request.setAttribute("flag", true);
		request.getRequestDispatcher("SingerServlet?info=play&playId=" + singerId).forward(request, response);
	}
	// 跳转到所有歌手页面
	protected void singer_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Singer> singerList = singerDao.findAllSingers();
		request.setAttribute("singerList", singerList);
		request.getRequestDispatcher("singerlist.jsp").forward(request, response);
	}
	// 跳转到关注管理页面
	protected void singer_followmgr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		NormalUser user = (NormalUser) session.getAttribute("user");
		int userId = user.getUserId();
		NormalUserSinger nus = new NormalUserSinger();
		nus.setUserId(userId);
		List<NormalUserSinger> list = normalUserSingerDao.findAllSinger(nus);
		request.setAttribute("list", list);
		request.getRequestDispatcher("user/followSinger.jsp").forward(request, response);
	}
	// 取消关注
	protected void remove_follow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int singerId = Integer.parseInt(request.getParameter("singerId"));
		HttpSession session = request.getSession();
		NormalUser user = (NormalUser) session.getAttribute("user");
		int userId = user.getUserId();
		NormalUserSinger nusData = new NormalUserSinger();
		nusData.setUserId(userId);
		nusData.setSingerId(singerId);
		if (!normalUserSingerDao.delete(nusData)){
			request.setAttribute("message", "删除失败！");
			request.setAttribute("flag", true);
		}
		request.getRequestDispatcher("SingerServlet?info=followmgr").forward(request, response);
	}
}
