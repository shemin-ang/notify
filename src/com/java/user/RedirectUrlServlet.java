package com.java.user;

//Loading required libraries
import java.io.*;
import java.util.*;

import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jasypt.util.password.BasicPasswordEncryptor;

public class RedirectUrlServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		doPost(request, response);
	}  
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		  
			String url = request.getParameter("url");
			String[] servletName = url.split("/");
			String adminUrl = servletName[servletName.length-1];
			
		// connecting to database
		  Connection con = null;  
		  Statement stmt = null;
		  ResultSet rs = null;
		  try {
			  Class.forName("com.mysql.jdbc.Driver");
			  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
			  stmt = con.createStatement();
			  rs = stmt.executeQuery("SELECT admin_id_co FROM Company WHERE url='"+adminUrl+"'");
			  
			  if(!rs.isBeforeFirst()){
				  
			  }else{
				  rs.next();
				  //Store admin id into session
				  HttpSession session = request.getSession(); 
				  session.setAttribute("adminId",rs.getString(1));  
				  
				  
				  request.setAttribute("success", "success"); 
				  response.setCharacterEncoding("UTF-8"); 
				  response.getWriter().print("success");
			  }
			
		  } catch (SQLException e) {
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
