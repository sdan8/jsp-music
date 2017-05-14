package music.action;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import music.Constant;
import music.dao.AdminDao;
import music.dao.impl.AdminDaoImpl;
import music.vo.Admin;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String info = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 根据info参数不同的值，执行不同的操作
		info = request.getParameter("info");
		// 登录
		if (info.equals("login")){
			this.admin_login(request, response);
		}
		// 注销
		if (info.equals("logout")){
			this.admin_logout(request, response);
		}
		// 添加管理员
		if (info.equals("add")){
			this.admin_add(request, response);
		}
		// 修改密码
		if (info.equals("psw")){
			this.admin_modifyPsw(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 管理员登录操作
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void admin_login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Admin adminData = new Admin();
		adminData.setAdminUsername(username);
		adminData.setAdminPassword(password);
		
		AdminDao adminDao = new AdminDaoImpl();
		Admin admin = adminDao.login(adminData);
		if (admin != null){
//			request.setAttribute("admin", admin);
//			request.setAttribute("login_flag", Constant.LOGIN_SUCCESS);
			HttpSession session = request.getSession();
			session.setAttribute("admin", admin);
			session.setAttribute("admin_login_flag", Constant.LOGIN_SUCCESS);
			request.getRequestDispatcher("manager.jsp").forward(request, response);
			
		}
	}
	/**
	 * 管理员注销操作
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void admin_logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("admin");
		session.setAttribute("admin_login_flag", Constant.LOGIN_FAILURE);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	/**
	 * 管理员添加
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void admin_add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Date register = new Date();
		Date lastDate = new Date();
		
		Admin adminData = new Admin();
		adminData.setAdminUsername(username);
		adminData.setAdminPassword(password);
		adminData.setAdminRegisterDate(register);
		adminData.setAdminLastDate(lastDate);
		
		AdminDao adminDao = new AdminDaoImpl();
		
		request.setAttribute("message", adminDao.save(adminData) ? "添加成功！" : "添加失败！");
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/newAdmin.jsp").forward(request, response);
	}
	/**
	 * 修改管理员密码
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void admin_modifyPsw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		
		Admin adminData = new Admin();
		adminData.setAdminUsername(username);
		adminData.setAdminPassword(password);
		
		// 检查密码是否正确
		
		AdminDao adminDao = new AdminDaoImpl();
		Admin admin = adminDao.login(adminData);
		if (admin != null){
			System.out.println("密码正确！");
			admin.setAdminPassword(newPassword);
			if (adminDao.update(admin)){
				request.setAttribute("message", "修改密码成功！");
			} else {
				request.setAttribute("message", "修改密码失败！");
			}
		} else {
			request.setAttribute("message", "修改密码失败！");
			System.out.println("密码错误！");
		}
		System.out.println("结束！");
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/psw.jsp").forward(request, response);
		
	}
}
