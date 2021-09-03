package com.ti.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection{
												
	public static Connection getConnection() throws SQLException{

		String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC&characterEncoding=utf8";
		String user = "root";
		String password = "6611";
		Connection con = null;
		con = DriverManager.getConnection(url, user, password);
	
		return con;
	}
	public static void close(Connection con, Statement stmt, ResultSet rs) {
		try {
			if(rs != null ){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(stmt != null ){
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(con != null ){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


