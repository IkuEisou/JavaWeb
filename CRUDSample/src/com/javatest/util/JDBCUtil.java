package com.javatest.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
	public Connection getCon() throws Exception{
		Connection con = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;"
					+ "DatabaseName=devdb", "sa", "fnst_1234");
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
