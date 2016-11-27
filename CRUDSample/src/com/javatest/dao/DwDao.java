package com.javatest.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.javatest.model.Dw;
import com.javatest.util.JDBCUtil;

import net.sf.json.JSONArray;

public class DwDao {
	private String dwtable="[dw]";
	
	public String searchAll(){
		String jsonStr="";
		try {
			JDBCUtil jdbcUtil = new JDBCUtil();
			Connection con = jdbcUtil.getCon();
			
			if (con != null) {
				Statement stmt=con.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				String sql = "SELECT name FROM "+dwtable;

				ResultSet rs = stmt.executeQuery(sql);
				rs.last();
				int rows = rs.getRow();
				if(rows == 0){
					jsonStr = "\"没有符合条件的记录!\"";
				}else{
					Dw[] dws = new Dw[rows];
					rs.first();
					for(int i = 0;i<rows;i++,rs.next()){
						Dw aqf = new Dw();
						aqf.setName(rs.getString("name").trim());
						dws[i] = aqf;
					}
	
					if(dws != null && dws.length != 0){
						JSONArray jsa = JSONArray.fromObject(dws);
						jsonStr = jsa.toString();
					}
				}
			}
		}catch (Exception e) {
				jsonStr = "\"查询失败!\"";
				e.printStackTrace();
				return  "{\"dws\":"+jsonStr+"\"}";
		}
		return "{\"dws\":"+jsonStr+"}";
	}
}
