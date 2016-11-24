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
		int pageNo = Integer.parseInt(page);
		String isWhere = keyword.isEmpty() ? " Where " : " AND ";
		String jsonStr="";
		String pages = "0";
		try {
			JDBCUtil jdbcUtil = new JDBCUtil();
			Connection con = jdbcUtil.getCon();
			
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
					jsonStr = "\"没有符合条件的记录!\"";
				}else{
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
						aqf.setWh(rs.getString("wh").trim());
						aqf.setName(rs.getString("name").trim());
						aqf.setDw(rs.getString("dw").trim());
						aqf.setZt(rs.getString("zt"));
						aqfs[i] = aqf;
					}
					
					sql = "SELECT COUNT(*) FROM "+aqftable+keyword;
					rs = stmt.executeQuery(sql);
					int recordCount = 0;
				      if (rs.next()) 
				      {
				              recordCount = rs.getInt(1);
				       }
					pages = Integer. toString(recordCount);
	
					if(aqfs != null && aqfs.length != 0){
						JSONArray jsa = JSONArray.fromObject(aqfs);
						jsonStr = jsa.toString();
					}
				}
			}
		}catch (Exception e) {
				jsonStr = "\"查询失败!\"";
				e.printStackTrace();
				return  "{\"aqfs\":"+jsonStr+",\"pages\":\""+pages+"\"}";
		}
		return "{\"aqfs\":"+jsonStr+",\"pages\":\""+pages+"\"}";
	}
	
	public boolean delete(String wh) {
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			String sql = "delete from "+aqftable+" where wh='"
					+wh+"'";
			Statement stmt=con.createStatement();
			int ok = stmt.executeUpdate(sql);
			if(ok > 0 ){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean add(Aqf a){
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			Statement stmt=con.createStatement();
			String sql = "insert into "+aqftable+"(xh,tj,gzjz,gzyl,zdyl,fsdm,azwz,wh,name,dw,zt) values(N'"+
					a.getXh()+"', "+a.getTj()+", N'"+a.getGzjz()+"', N'"+a.getGzyl()+"', N'"+a.getZdyl()+
					"', N'"+a.getFsdm()+"', N'"+a.getAzwz()+"', N'"+a.getWh()+"', N'"+a.getName()+
					"', N'"+a.getDw()+"', N'"+a.getZt()+"')";
			int ok = stmt.executeUpdate(sql);
			if(ok > 0 ){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isExsit(String wh){
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			String sql = "select * from "+aqftable+" where wh='"
					+wh+"'";
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs!=null && rs.next()){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean update(Aqf a){
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			String sql = "update "+aqftable+" set xh=N'"+a.getXh()+"', tj=N'"+a.getTj()+"', gzjz=N'"+a.getGzjz()+
					"', gzyl=N'"+a.getGzyl()+"', zdyl=N'"+a.getZdyl()+"', fsdm=N'"+a.getFsdm()+"', zt=N'"+a.getZt()+
					"', azwz=N'"+a.getAzwz()+"', name=N'"+a.getName()+"', dw=N'"+a.getDw()+
					"' where wh='"+a.getWh()+"'";
			Statement stmt=con.createStatement();
			int ok = stmt.executeUpdate(sql);
			if(ok > 0 ){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
