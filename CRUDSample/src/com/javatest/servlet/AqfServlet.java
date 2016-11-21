package com.javatest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javatest.dao.AqfDao;

@WebServlet("/AqfServlet")
public class AqfServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;
    public AqfServlet() {
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

		AqfDao aqfDao = new AqfDao();
		String flag = request.getParameter("flag");
		String jsonStr = "";
		String keyword = "";
		String page = request.getParameter("page");
		switch (flag) {
		case "srh":
			String xh = request.getParameter("xh");
	
			if(!xh.isEmpty()){
				keyword += " WHERE xh='"+xh+"' ";
			}
			jsonStr = aqfDao.search(page, keyword);
			break;
		default:
			response.sendRedirect("login.html");
			break;
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
