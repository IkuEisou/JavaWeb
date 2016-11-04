package com.javatest.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class JDBCUtil {
	public Connection getCon() throws Exception{
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://10.167.227.48:5432/testdb","postgres", "fnst1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public void closeCon(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
