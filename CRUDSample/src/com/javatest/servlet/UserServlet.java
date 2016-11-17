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
import net.sf.json.JSONArray;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("userLogin")!=null) {
			doPost(request, response);
		}else{
			response.sendRedirect("login.html");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDao userDao = new UserDao();
		String flag = request.getParameter("flag");
		if (flag==null || "".equals(flag)) {
			response.sendRedirect("login.html");
		}else if("login".equals(flag)){
			String jsonStr;
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
					session.setAttribute("pwdLogin", userLogin.getPassword());
					jsonStr = "{\"msg\":\"main.html\",\"status\":200}";
//					response.sendRedirect("main.html");
				}else{
//					response.getWriter().print("<script>alert('login failed.'); window.location='login.html' </script>");
					jsonStr = "{\"msg\":\"invalid user/password.\",\"status\":302}";
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if ("regist".equals(flag)) {
			String jsonStr;
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String real = request.getParameter("realname").trim();
			String dep = request.getParameter("dep").trim();
			String role = request.getParameter("role").trim();
			User u = new User();
			u.setUsername(username);
			u.setPassword(password);
			u.setDep(dep);
			u.setReal(real);
			u.setRole(role);
			
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
				jsonStr = "{\"msg\":\"username does not exist.\"}";
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
		}else if ("srhall".equals(flag)){
			String page = request.getParameter("page");
			String jsonStr = "{\"msg\":\"init user list failed.\"}";
			User[] usrs = userDao.searchAll(page);
			if(usrs != null && usrs.length != 0){
				JSONArray jsa = JSONArray.fromObject(usrs);
				jsonStr = jsa.toString();
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
		}else if ("srh".equals(flag)){
			String jsonStr = "{\"msg\":\"srh user list failed.\"}";
			String username = request.getParameter("username").trim();
			String realname = request.getParameter("realname").trim();
			String dep = request.getParameter("dep").trim();
			String role = request.getParameter("role").trim();
			String keyword = "";
			if(!username.isEmpty()){
				keyword += " WHERE name='"+username+"' ";
			}
			if(!realname.isEmpty()){
				if(!keyword.isEmpty()){
					keyword += ",";
				}else{
					keyword += " WHERE ";
				}
				keyword += "real='"+realname+"'";
			}
			if(!dep.isEmpty()){
				if(!keyword.isEmpty()){
					keyword += ",";
				}else{
					keyword += " WHERE ";
				}
				keyword += "dep='"+dep+"'";
			}
			if(!role.isEmpty()){
				if(!keyword.isEmpty()){
					keyword += ",";
				}else{
					keyword += " WHERE ";
				}
				keyword += "role='"+role+"'";
			}
			User[] usrs = userDao.search(keyword);
			if(usrs != null && usrs.length != 0){
				JSONArray jsa = JSONArray.fromObject(usrs);
				jsonStr = jsa.toString();
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
