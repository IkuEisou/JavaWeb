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
import com.javatest.model.Aqf;

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
			String wh = request.getParameter("wh");
			String name = request.getParameter("name");
			String dw = request.getParameter("dw");
			String zt = request.getParameter("zt");
	
			if(!xh.isEmpty()){
//				keyword += " WHERE xh='"+xh+"' ";
				keyword += " WHERE xh LIKE '%"+xh+"%' ";
			}
			if(!wh.isEmpty()){
				if(!keyword.isEmpty()){
					keyword += " AND ";
				}else{
					keyword += " WHERE ";
				}
//				keyword += "wh=N'"+wh+"'";
				keyword += "wh LIKE N'%"+wh+"%'";
			}
			if(!name.isEmpty()){
				if(!keyword.isEmpty()){
					keyword += " AND ";
				}else{
					keyword += " WHERE ";
				}
				keyword += "name=N'"+name+"'";
			}
			if(!dw.isEmpty()){
				if(!keyword.isEmpty()){
					keyword += " AND ";
				}else{
					keyword += " WHERE ";
				}
				keyword += "dw=N'"+dw+"'";
			}
			if(!zt.isEmpty()){
				if(!keyword.isEmpty()){
					keyword += " AND ";
				}else{
					keyword += " WHERE ";
				}
				keyword += "zt=N'"+zt+"'";
			}
			jsonStr = aqfDao.search(page, keyword);
			break;
		case "delete":
			wh = request.getParameter("wh");
			if (aqfDao.delete(wh)) {
				jsonStr = "{\"msg\":\"delete success.\"}";
			}else{
				jsonStr = "{\"msg\":\"delete failed.\"}";
			}
			break;
		case "add":
			Aqf a = new Aqf();
			wh = request.getParameter("wh").trim();
			xh = request.getParameter("xh");
			name = request.getParameter("name");
			dw = request.getParameter("dw");
			zt = request.getParameter("zt");
			String tj = request.getParameter("gctj");
			String gzjz = request.getParameter("gzjz");
			String gzyl = request.getParameter("gzyl");
			String zdyl = request.getParameter("zdyl");
			String fsdm = request.getParameter("fsdm");
			String azwz = request.getParameter("azwz");

			a.setWh(wh);
			a.setXh(xh);
			a.setName(name);
			a.setDw(dw);
			a.setZt(zt);
			a.setTj(tj);
			a.setGzjz(gzjz);
			a.setGzyl(gzyl);
			a.setZdyl(zdyl);
			a.setFsdm(fsdm);
			a.setAzwz(azwz);
			
			if (aqfDao.isExsit(wh)) {
				if(aqfDao.update(a)){
					jsonStr = "{\"msg\":\"update success.\"}";
				}else{
					jsonStr = "{\"msg\":\"update failed.\"}";
				}
			}else {
				if(aqfDao.add(a)){
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
