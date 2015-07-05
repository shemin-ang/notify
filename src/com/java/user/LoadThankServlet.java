package com.java.user;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;

public class LoadThankServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String adminId = request.getParameter("adminId");
		
		Connection con = null;  
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		
			con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT message FROM Submission WHERE admin_id_sub='"+adminId+"'");
			  
			String message = "";
			if(rs.isBeforeFirst()){
				rs.next();
				message = rs.getString(1);
			}else{
				ResultSet rs2 = stmt.executeQuery("SELECT message FROM Submission WHERE admin_id_sub=2");
				rs2.next();
				message = rs2.getString(1);
			}
			
			request.setAttribute("firstEntry","true");
			request.setAttribute("message",message);
			RequestDispatcher view = request.getRequestDispatcher("thankyou.jsp");
			view.forward(request,response);

		}catch (SQLException e) {
			RequestDispatcher view = request.getRequestDispatcher("internetError.jsp");
			view.forward(request,response);
		} catch (ClassNotFoundException e) {
			RequestDispatcher view = request.getRequestDispatcher("internetError.jsp");
			view.forward(request,response);
		} finally {
			try {
				if(rs != null) {
					rs.close();
					  rs = null;
				  }
				  if(stmt != null) {
					  stmt.close();
					  stmt = null;
				  }
				  if(con != null) {
					  con.close();
					  con = null;
				  }
			} catch (SQLException e) {}	  
		}
	}
}
