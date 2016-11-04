package com.javatest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javatest.model.User;
import com.javatest.util.JDBCUtil;

public class UserDao {
	private String usertable="javauser";
	public User login(User u) throws Exception{
		PreparedStatement pstmt;
		JDBCUtil jdbcUtil = new JDBCUtil();
		Connection con = jdbcUtil.getCon();
		String password = u.getPassword();
		if (con == null) {
			System.out.println("connection failed");
			return null;
		}else{
			String sql = "select * from "+usertable+" where id= ?";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, u.getUsername());
				ResultSet rs = pstmt.executeQuery();
				if(rs!=null){
					if(rs.next()){
						User user = new User();
						user.setUsername(rs.getString("id"));
						user.setPassword(rs.getString("pwd"));
						user.setType(rs.getInt("type"));
						if (password.equals(user.getPassword())) {
							return user;
						}
					}
				}
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
		PreparedStatement pstmt;
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			String sql = "insert into "+usertable+"(id,pwd,type,salt) values(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPassword());
			pstmt.setInt(3, u.getType());
			pstmt.setString(4, u.getSalt());
			int ok = pstmt.executeUpdate();
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
		PreparedStatement pstmt;
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			String sql = "update "+usertable+" set pwd=? , salt=?  where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u.getPassword());
			pstmt.setString(2, u.getSalt());
			pstmt.setString(3, u.getUsername());
			int ok = pstmt.executeUpdate();
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
		PreparedStatement pstmt;
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			String sql = "select * from "+usertable+" where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u.getUsername());
			ResultSet rs = pstmt.executeQuery();
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
		PreparedStatement pstmt;
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			String sql = "delete from "+usertable+" where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u.getUsername());
			int ok = pstmt.executeUpdate();
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
	public boolean isAdmin(User u){
		PreparedStatement pstmt;
		JDBCUtil jdbcUtil = new JDBCUtil();
		try {
			Connection con = jdbcUtil.getCon();
			String sql = "select * from "+usertable+" where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u.getUsername());
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null && rs.next()){
				if(rs.getInt("type")==1){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
