package com.java.superadmin;

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

public class LoginSuperAdminServlet extends HttpServlet {
	  public void doPost(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		  
		  //check if login and password match
		  String adminId = "2";
		  String password = request.getParameter("pwd");
		  
		  //for encryption
		  BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		  
		  // connecting to database
		  Connection con = null;  
		  Statement stmt = null;
		  ResultSet rs = null;
		  try {
			  Class.forName("com.mysql.jdbc.Driver");
			  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
			  stmt = con.createStatement();
			  rs = stmt.executeQuery("SELECT password FROM Admin WHERE admin_id='"+adminId+"'");
			  rs.next();
			  
			  ArrayList<String> errorSuperLoginList = new ArrayList<String>();
			  boolean validSuperLogin = true;
			  
			  //Backend validation
			  if(password.equals("")){
				  validSuperLogin = false;
				  errorSuperLoginList.add("Password cannot be empty");
			  }else{
				  if(password.length() < 8 || password.length() > 15){
					  validSuperLogin = false;
					  errorSuperLoginList.add("Password must be between 8 to 15 characters");
				  }else if(!passwordEncryptor.checkPassword(password, rs.getString(1))){
					  //Password dont not match
					  validSuperLogin = false;
					  errorSuperLoginList.add("Password entered is incorrect");
				  }
			  }
			 
        	if(validSuperLogin){
        		//Store super admin as session
        		HttpSession session = request.getSession(); 
				session.setAttribute("adminIdForSuperAdmin",adminId); 
				
				RequestDispatcher view = request.getRequestDispatcher("register.jsp");
				view.forward(request,response);	  
	        
        	}else{
        		request.setAttribute("errorSuperLoginList", errorSuperLoginList);
	        	 RequestDispatcher view = request.getRequestDispatcher("create.jsp");
	        	 view.forward(request,response);
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
