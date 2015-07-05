package com.java.admin;

//Loading required libraries
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jasypt.util.password.BasicPasswordEncryptor;

public class LoginServlet extends HttpServlet {
	 	private Pattern pattern;
		private Matcher matcher;
		
		private static final String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		public LoginServlet() {
			pattern = Pattern.compile(EMAIL_PATTERN);
		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		  
		  //check if login and password match
		  String email = request.getParameter("email").trim();
		  String password = request.getParameter("pwd");
		  
		  //for encryption
		  BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		  
		  ArrayList<String> errorLoginList = new ArrayList<String>();
		  boolean validLogin = true;
		  
		  // connecting to database
		  Connection con = null;  
		  Statement stmt = null;
		  ResultSet rs = null;
		  try {
			  Class.forName("com.mysql.jdbc.Driver");
			  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
			  stmt = con.createStatement();
			  rs = stmt.executeQuery("SELECT password,admin_id,name FROM Admin WHERE email='"+email+"'");
			  
			  if(email.trim().equals("")){
				  validLogin = false;
				  errorLoginList.add("Email field cannot be empty");
			  }else{
				  matcher = pattern.matcher(email);
				  boolean matchEmail = matcher.matches();
				  if(matchEmail==false){
					  validLogin = false;
					  errorLoginList.add("Email field is in the wrong format");
				 }else if(!rs.isBeforeFirst()){
					 validLogin = false;
					 errorLoginList.add("Email does not exist.");
		         }
			  }
			  
			  if(validLogin){
				  rs.next();
				  if(password.trim().equals("")){
					  validLogin = false;
					  errorLoginList.add("Password field cannot be empty");
				  }else{
					  if(password.length() < 8 || password.length() > 15){
						  validLogin = false;
						  errorLoginList.add("Password must be between 8 to 15 characters");
					  }else if(!passwordEncryptor.checkPassword(password, rs.getString(1))){
						  validLogin = false;
						  errorLoginList.add("Password entered is incorrect");
					  }
				  }
			  }
			  
			  if(validLogin){
	    		 //If password match
	    		//Store admin id into session
	    		String adminIdForAdmin = rs.getObject(2).toString();
	    		String name = rs.getObject(3).toString();
	    		
				HttpSession session = request.getSession(); 
				session.setAttribute("adminIdForAdmin",adminIdForAdmin); 
	
				RequestDispatcher view = request.getRequestDispatcher("admin.jsp");
				view.forward(request,response);
			  }else{	 
				  //If error exist
				  request.setAttribute("errorLoginList", errorLoginList);
				  RequestDispatcher view = request.getRequestDispatcher("login.jsp");
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
