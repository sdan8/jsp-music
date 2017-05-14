package music.action;

import java.io.IOException;

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
import music.dao.NormalUserDao;
import music.dao.impl.NormalUserDaoImpl;
import music.vo.NormalUser;

/**
 * Servlet implementation class NormalUserServlet
 */
@WebServlet("/NormalUserServlet")
public class NormalUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ServletConfig servletConfig;
	private String info = "";
	private NormalUserDao normalUserDao = new NormalUserDaoImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NormalUserServlet() {
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
		
		// 普通用户登录
		if (info.equals("login")){
			this.user_login(request, response);
		}
		// 普通用户注册
		if (info.equals("signup")){
			this.user_signup(request, response);
		}
		// 普通用户注销
		if (info.equals("logout")){
			this.user_logout(request, response);
		}
		// 普通用户管理
		if (info.equals("manager")){
			this.user_manager(request, response);
		}
		// 用户基本设置
		if (info.equals("set")){
			this.user_set(request, response);
		}
		// 修改头像
		if (info.equals("avatar")){
			this.user_avatar(request, response);
		}
		// 修改密码
		if (info.equals("psw")){
			this.user_psw(request, response);
		}
		if (info.equals("ban")){
			this.user_ban(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// 普通用户登录
	protected void user_login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		
		NormalUser userData = new NormalUser();
		userData.setUserName(userName);
		userData.setUserPassword(userPassword);
		
		NormalUser user = normalUserDao.login(userData);
		if (user != null){
			if (user.getUserStatus() == 0){
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				session.setAttribute("login_flag", Constant.LOGIN_SUCCESS);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				request.setAttribute("message", "该用户已被封禁！");
				request.setAttribute("flag", true);
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
		} else {
			request.setAttribute("message", "用户名或密码输入错误！");
			request.setAttribute("flag", true);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	// 普通用户注册
	protected void user_signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		String userEmail = request.getParameter("userEmail");
		
		NormalUser user = new NormalUser();
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		user.setUserNickname(userName);
		user.setUserSex(Constant.SEX_DEFAULT);
		user.setUserEmail(userEmail);
		user.setUserAvatar(Constant.DEFAULT_AVATAR);
		user.setUserRegisterDate(Constant.DEFAULT_DATE);
		user.setUserLastDate(Constant.DEFAULT_DATE);
		user.setUserStatus(Constant.USER_STATUS_NORMAL);
		
		request.setAttribute("message", normalUserDao.singup(user) ? "注册成功！" : "注册失败！");
		request.setAttribute("flag", true);
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	// 普通用户注销
	protected void user_logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.setAttribute("login_flag", Constant.LOGIN_FAILURE);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	// 普通用户管理
	protected void user_manager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("profile.jsp").forward(request, response);
	}
	// 用户基本设置
	protected void user_set(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userNickname = request.getParameter("userNickname");
		int userSex = Integer.parseInt(request.getParameter("sex"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		NormalUser userData = new NormalUser();
		userData.setUserId(userId);
		userData.setUserNickname(userNickname);
		userData.setUserSex(userSex);
		
		if (normalUserDao.setting(userData)){
			request.setAttribute("message", "修改成功！");
		} else {
			request.setAttribute("message", "修改失败！");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("user/setting.jsp").forward(request, response);
	}
	protected void user_avatar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
					String filedir = Constant.DEFAULT_AVATAR_SECPATH + fileName + "." + singleFile.getFileExt();
					// 带后缀名保存与数据库中
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					//执行上传操作
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("上传至： " + filedir);
					
					
					int userId = Integer.parseInt(su.getRequest().getParameter("userId"));
					
					NormalUser userData = new NormalUser();
					userData.setUserId(userId);
					userData.setUserAvatar(saveFileName);
					
					if (normalUserDao.save_avatar(userData)){
						request.setAttribute("message", "修改头像成功！");
					} else {
						request.setAttribute("message", "修改头像失败！");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("修改头像Servlet异常！", e);
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("user/avatar.jsp").forward(request, response);
	}
	protected void user_psw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newPassword = request.getParameter("newPassword");
		String password = request.getParameter("password");
		int userId = Integer.parseInt(request.getParameter("userId"));
		String userName = request.getParameter("userName");
		
		NormalUser userLoginData = new NormalUser();
		userLoginData.setUserPassword(password);
		userLoginData.setUserName(userName);
		
		NormalUser user = normalUserDao.login(userLoginData);
		if (user != null){
			// 原密码正确
			NormalUser userData = new NormalUser();
			userData.setUserId(userId);
			userData.setUserPassword(newPassword);
			if (normalUserDao.save_psw(userData)){
				request.setAttribute("message", "修改密码成功！");
			} else {
				request.setAttribute("message", "修改密码失败！");
			}
		} else {
			request.setAttribute("message", "原密码错误！");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("user/psw.jsp").forward(request, response);
	}
	protected void user_ban(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		NormalUser userData = new NormalUser();
		userData.setUserId(userId);
		if (normalUserDao.ban(userData)){
			request.setAttribute("message", "封禁成功！");
		} else {
			request.setAttribute("message", "封禁失败！");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/ban.jsp").forward(request, response);
	}
}
