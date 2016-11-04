package com.javatest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javatest.dao.UserDao;
import com.javatest.model.User;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("userLogin")!=null && session.getAttribute("loginType")!=null && session.getAttribute("loginType").equals("admin")) {
			doPost(request, response);
		}else{
			response.sendRedirect("login.html");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDao userDao = new UserDao();
		String flag = request.getParameter("flag");
		if (flag==null || "".equals(flag)) {
			response.sendRedirect("login.html");
		}else if("login".equals(flag)){
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User u = new User();
			u.setUsername(username);
			u.setPassword(password);
			User userLogin;
			try {
				userLogin = userDao.login(u);
				if(userLogin!=null){
					session.setAttribute("userLogin", userLogin.getUsername());
					if (userLogin.getType()==1) {
						session.setAttribute("loginType", "admin");
						response.sendRedirect("manage.jsp");
					}else{
						session.setAttribute("loginType", "common");
						response.sendRedirect("CarShop.jsp");
					}
				}else{
					response.getWriter().print("<script>alert('login failed.'); window.location='login.html' </script>");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("logout".equals(flag)){
			session.setAttribute("userLogin", null);
			session.setAttribute("loginType", null);
			response.sendRedirect("login.html");
		}else if("checkLogin".equals(flag)){
			PrintWriter out = null;
			String jsonStr;
			try {
				if(session.getAttribute("userLogin")!=null && session.getAttribute("loginType")!=null && session.getAttribute("loginType").equals("admin")){
					jsonStr = "{\"msg\":\"LOGIN\"}";
				}else{
					jsonStr = "{\"msg\":\"NOLOGIN\"}";
				}
			    out = response.getWriter();
			    out.write(jsonStr);
			    out.flush();
			} catch (IOException e) {
			    e.printStackTrace();
			} finally {
			    if (out != null) {
			        out.close();
			    }
			}
		}else if ("regist".equals(flag)) {
			String jsonStr;
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User u = new User();
			u.setUsername(username);
			u.setPassword(password);
			u.setType(0);
			if (userDao.isExsit(u)) {
				if(userDao.updateUser(u)){
					jsonStr = "{\"msg\":\"update success.\"}";
				}else{
					jsonStr = "{\"msg\":\"update failed.\"}";
				}
			}else {
				if(userDao.addUser(u)){
					jsonStr = "{\"msg\":\"insert success.\"}";
				}else{
					jsonStr = "{\"msg\":\"insert failed.\"}";
				}
			}
			PrintWriter out = null;
			try {
			    out = response.getWriter();
			    out.write(jsonStr);
			    out.flush();
			} catch (IOException e) {
			    e.printStackTrace();
			} finally {
			    if (out != null) {
			        out.close();
			    }
			}	
		}else if ("delete".equals(flag)) {
			String jsonStr;
			String username = request.getParameter("username");
			User u = new User();
			u.setUsername(username);
			if (!userDao.isExsit(u)) {
				jsonStr = "{\"msg\":\"userID does not exist.\"}";
			}else if(userDao.isAdmin(u)){
				jsonStr = "{\"msg\":\"admin can not be deleted.\"}";
			}else{
				if (userDao.deleteUser(u)) {
					jsonStr = "{\"msg\":\"delete success.\"}";
				}else{
					jsonStr = "{\"msg\":\"delete failed.\"}";
				}
			}
			PrintWriter out = null;
			try {
			    out = response.getWriter();
			    out.write(jsonStr);
			    out.flush();
			} catch (IOException e) {
			    e.printStackTrace();
			} finally {
			    if (out != null) {
			        out.close();
			    }
			}	
		}
	}
}
