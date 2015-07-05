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

public class LoadFormServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		doPost(request, response);
	}  
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		  
		  String adminId = request.getParameter("adminId");
		  ArrayList<String> categoryOrderList = new ArrayList<String>();
		  ArrayList<String> categoryList = new ArrayList<String>();
		  ArrayList<String> errorList = new ArrayList<String>();
		  
		  // connecting to database
		  Connection con = null;  
		  Statement stmt = null;
		  ResultSet rs = null;
		  try {
			  Class.forName("com.mysql.jdbc.Driver");
			  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
			  stmt = con.createStatement();
			  rs = stmt.executeQuery("SELECT category_order,category FROM Category WHERE admin_id_cat='"+adminId+"'");
			  
			  while(rs.next()){
				  categoryOrderList.add(rs.getString(1));
				  categoryList.add(rs.getString(2));
			  }
			  
			  request.setAttribute("firstEntry", "true");
			  request.setAttribute("categoryOrderList", categoryOrderList);
			  request.setAttribute("categoryList", categoryList);
			  request.setAttribute("errorList", errorList);
			  RequestDispatcher view = request.getRequestDispatcher("form.jsp");
			  view.forward(request,response);
			
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
