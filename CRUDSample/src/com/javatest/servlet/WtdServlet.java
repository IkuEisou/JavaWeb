package com.javatest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.javatest.dao.WtdDao;
import com.javatest.model.Wtd;

@WebServlet("/WtdServlet")
public class WtdServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;
    public WtdServlet() {
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

		WtdDao wtdDao = new WtdDao();
		String flag = request.getParameter("flag");
		String jsonStr = "";
		String keyword = "";
		String page = request.getParameter("page");
		switch (flag) {
		case "srhaqf":
			String dw = request.getParameter("dw");
	
			if(!dw.isEmpty()){
				keyword += " WHERE dw LIKE '%"+dw+"%' ";
			}
			jsonStr = wtdDao.searchAqf(page, keyword);
			break;
		case "srhwtd":
			dw = request.getParameter("dw");
			String dh = request.getParameter("dh");
			String st = request.getParameter("st").replace("T", " ");
			String et = request.getParameter("et").replace("T", " ");
			
			if(!dw.isEmpty()){
				keyword += " WHERE dw LIKE '%"+dw+"%' ";
			}
			if(!dh.isEmpty()){
				if(!keyword.isEmpty()){
					keyword += " AND ";
				}else{
					keyword += " WHERE ";
				}
				keyword += "dh LIKE N'%"+dh+"%'";
			}
			if(!st.isEmpty()){
				if(!keyword.isEmpty()){
					keyword += " AND ";
				}else{
					keyword += " WHERE ";
				}
				keyword += "time > '"+st+"'";
			}
			if(!et.isEmpty()){
				if(!keyword.isEmpty()){
					keyword += " AND ";
				}else{
					keyword += " WHERE ";
				}
				keyword += "time < '"+et+"'";
			}
			jsonStr = wtdDao.searchWtd(page, keyword);
			break;			
		case "delete":
			dh = request.getParameter("dh");
			if (wtdDao.delete(dh)) {
				jsonStr = "{\"msg\":\"delete success.\"}";
			}else{
				jsonStr = "{\"msg\":\"delete failed.\"}";
			}
			break;
		case "add":
			Wtd a = new Wtd();
			String wh = request.getParameter("wh").trim();
			dw = request.getParameter("dw");
			String jy = request.getParameter("jy");
			dh = request.getParameter("dh");
			String fee = request.getParameter("fee");
			a.setWh(wh);
			a.setJy(jy);
			a.setDw(dw);
			a.setDh(dh);
			a.setFee(fee);
			if (wtdDao.isExsit(wh)) {
				if(wtdDao.update(a)){
					jsonStr = "{\"msg\":\"update success.\"}";
				}else{
					jsonStr = "{\"msg\":\"update failed.\"}";
				}
			}else {
				if(wtdDao.add(a)){
					jsonStr = "{\"msg\":\"insert success.\"}";
				}else{
					jsonStr = "{\"msg\":\"insert failed.\"}";
				}
			}
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
