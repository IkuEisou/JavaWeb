package com.javatest.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.javatest.model.Aqf;
import com.javatest.util.JDBCUtil;

import net.sf.json.JSONArray;

public class AqfDao {
	private String aqftable="[aqf]";
	
	public String search(String page, String keyword){
		try {
			JDBCUtil jdbcUtil = new JDBCUtil();
			Connection con = jdbcUtil.getCon();
			int pageNo = Integer.parseInt(page);
			String isWhere = keyword.isEmpty() ? " Where " : " AND ";
			
			if (con != null) {
				Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				String sql = "SELECT TOP 5 * FROM "+aqftable+keyword+isWhere+
						"  ID NOT IN(SELECT TOP "+((pageNo - 1)*5)+" ID FROM "+aqftable+keyword+
						" ORDER BY ID) ORDER BY ID";

				ResultSet rs = stmt.executeQuery(sql);
				rs.last();
				int rows = rs.getRow();
				if(rows == 0){
					return null;
				}
				Aqf[] aqfs = new Aqf[rows];
				rs.first();
				for(int i = 0;i<rows;i++,rs.next()){
					Aqf aqf = new Aqf();
//					aqf.setId(rs.getString("id").trim());
					aqf.setXh(rs.getString("xh").trim());
					aqf.setTj(rs.getString("tj").trim());
					aqf.setGzjz(rs.getString("gzjz").trim());
					aqf.setGzyl(rs.getString("gzyl").trim());
					aqf.setZdyl(rs.getString("zdyl").trim());
					aqf.setFsdm(rs.getString("fsdm").trim());
					aqf.setAzwz(rs.getString("azwz").trim());
					aqf.setBgbh(rs.getString("bgbh").trim());
					aqf.setJyfy(rs.getString("jyfy").trim());
//					aqf.setDw(rs.getString("dw").trim());
					aqfs[i] = aqf;
				}
				
				sql = "SELECT COUNT(*) FROM "+aqftable+keyword;
				rs = stmt.executeQuery(sql);
				int recordCount = 0;
			      if (rs.next()) 
			      {
			              recordCount = rs.getInt(1);
			       }
				String pages = Integer. toString(recordCount);
				String jsonStr="";
				if(aqfs != null && aqfs.length != 0){
					JSONArray jsa = JSONArray.fromObject(aqfs);
					jsonStr = jsa.toString();
				}
				jsonStr = "{\"aqfs\":"+jsonStr+",\"pages\":\""+pages+"\"}";
				return jsonStr;
			}
		}catch (Exception e) {
				e.printStackTrace();
		}
		return null;
	}
}
