package com.javatest.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.javatest.model.User;
import com.javatest.util.JDBCUtil;

import net.sf.json.JSONArray;

public class UserDao {
	private String usertable="[user]";
	public User login(User u) throws Exception{
		JDBCUtil jdbcUtil = new JDBCUtil();
		Connection con = jdbcUtil.getCon();
		String password = u.getPassword();
		if (con == null) {
			System.out.println("connection failed");
			return null;
		}else{
			Statement stmt=con.createStatement();
			String sql = "SELECT * FROM "+usertable+" WHERE name='"+u.getUsername()+"'";
			try {
				ResultSet rs = stmt.executeQuery(sql);
				if(rs == null || !rs.next()){
					return null;
				}

				User user = new User();
				user.setUsername(rs.getString("name"));
				user.setPassword(rs.getString("pwd"));
				
				if (password.equals(user.getPassword().trim())) {
					return user;
				}
				else
					return null;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally{
				jdbcUtil.closeCon(con);
			}
		}
	}

	public boolean addUser(User u){
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			Statement stmt=con.createStatement();
			String sql = "insert into "+usertable+"(name,pwd,real,dep,role) values('"+
					u.getUsername()+"', "+u.getPassword()+", N'"+u.getReal()+"', N'"+u.getDep()
					+"', N'"+u.getRole()+"')";
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
	
	public boolean updateUser(User u){
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			String sql = "update "+usertable+" set pwd='"+u.getPassword()
					+"', real=N'"+u.getReal()+"', dep=N'"+u.getDep()+"', role=N'"+u.getRole()+
					"' where name='"+u.getUsername()+"'";
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
	
	public boolean isExsit(User u){
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			String sql = "select * from "+usertable+" where name='"
					+u.getUsername()+"'";
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

	public boolean deleteUser(User u) {
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			String sql = "delete from "+usertable+" where name='"
					+u.getUsername()+"'";
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
	
	public String search(String page, String keyword){
		try {
			JDBCUtil jdbcUtil = new JDBCUtil();
			Connection con = jdbcUtil.getCon();
			int pageNo = Integer.parseInt(page);
			String isWhere = keyword.isEmpty() ? " Where " : " AND ";
			
			if (con != null) {
				Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				String sql = "SELECT TOP 5 * FROM "+usertable+keyword+isWhere+
						"  ID NOT IN(SELECT TOP "+((pageNo - 1)*5)+" ID FROM "+usertable+keyword+
						" ORDER BY ID) ORDER BY ID";

				ResultSet rs = stmt.executeQuery(sql);
				rs.last();
				int rows = rs.getRow();
				if(rows == 0){
					return null;
				}
				User[] usrs = new User[rows];
				rs.first();
				for(int i = 0;i<rows;i++,rs.next()){
					User user = new User();
					user.setUsername(rs.getString("name").trim());
					user.setPassword(rs.getString("pwd").trim());
					user.setRole(rs.getString("role").trim());
					user.setDep(rs.getString("dep").trim());
					user.setReal(rs.getString("real").trim());
					usrs[i] = user;
				}
				
				sql = "SELECT COUNT(*) FROM "+usertable+keyword;
				rs = stmt.executeQuery(sql);
				int recordCount = 0;
			      if (rs.next()) 
			      {
			              recordCount = rs.getInt(1);
			       }
				String pages = Integer. toString(recordCount);
				String jsonStr="";
				if(usrs != null && usrs.length != 0){
					JSONArray jsa = JSONArray.fromObject(usrs);
					jsonStr = jsa.toString();
				}
				jsonStr = "{\"usrs\":"+jsonStr+",\"pages\":\""+pages+"\"}";
				return jsonStr;
			}
		}catch (Exception e) {
				e.printStackTrace();
		}
		return null;
	}
}
