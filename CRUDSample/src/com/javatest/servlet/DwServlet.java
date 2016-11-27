package com.javatest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.javatest.dao.DwDao;

@WebServlet("/DwServlet")
public class DwServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;
    public DwServlet() {
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

		DwDao dwDao = new DwDao();
		String flag = request.getParameter("flag");
		String jsonStr = "";

		switch (flag) {
		case "srhall":
			jsonStr = dwDao.searchAll();
			break;
		case "delete":
			break;
		case "add":
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
