package org.dante.shiro.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dante.shiro.dao.UserDao;
import org.dante.shiro.utils.CryptographyUtils;
import org.dante.shiro.utils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class Register
 */
@WebServlet(name = "/RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(RegisterServlet.class);
	private UserDao userDao = new UserDao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Connection conn = DbUtils.getConn();
		try {
			userDao.addUser(conn, username, CryptographyUtils.sha256Hash(password, CryptographyUtils.SALT));
			request.setAttribute("msg", "注册成功！");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (Exception e) {
			logger.error(username + "，注册失败！", e);
			request.setAttribute("msg", "注册失败！");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		} finally {
			DbUtils.closeConn(conn);
		}
	}

}
