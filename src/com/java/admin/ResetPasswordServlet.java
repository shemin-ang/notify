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

import com.java.model.HtmlEmailSender;

public class ResetPasswordServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		  
		  //check if login match email format (backend check)
		  String password = request.getParameter("pwd").trim();
		  String confirmedPassword = request.getParameter("confirmpwd").trim();
		  String resetUrl = request.getParameter("resetUrl").trim();
		  String[] resetId = resetUrl.split("=");
		  
		  //encrypting the password
		  BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

		  // connecting to database
		  Connection con = null;  
		  Statement stmt = null;
		  ResultSet rs = null;
		  try {
			  Class.forName("com.mysql.jdbc.Driver");
			  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
			  stmt = con.createStatement();
			  rs = stmt.executeQuery("SELECT admin_id_reset FROM ResetPassword WHERE reset_id='"+resetId[1]+"'");
			  
			  // displaying records
			  if(!rs.isBeforeFirst()){
				 request.setAttribute("errorMsg", "Error in reseting password.");
		 		 RequestDispatcher view = request.getRequestDispatcher("forgetpassword.jsp");
				 view.forward(request,response);
	         }else{
	 			//Check if both password and confirm password matches
	        	 if(confirmedPassword.equals(password)){
		        	rs.next();
		 			String adminId = rs.getObject(1).toString();
		 			String encryptedPassword = passwordEncryptor.encryptPassword(password);
		 			
					stmt.executeUpdate("UPDATE Admin SET password='"+ encryptedPassword + "' WHERE admin_id='"+adminId+"'");
					//Delete record from db
					//Prevent user from using the same link ans reseting again
					stmt.executeUpdate("DELETE FROM ResetPassword WHERE reset_id='"+resetId[1]+"'");
					
	 	        	String successMsg = "Your password has been successfully changed.";
	 	        	request.setAttribute("successMsg", successMsg);
	 	        	RequestDispatcher view = request.getRequestDispatcher("login.jsp");
	 				view.forward(request,response);
	        	 }else{
	        		 request.setAttribute("errorMsg", "Password and Confirmed Password do not match.");
		 	         RequestDispatcher view = request.getRequestDispatcher("forgetpassword.jsp");
		 			 view.forward(request,response);
	        	 }
	         }
			  
		  } catch (SQLException e) {
			  RequestDispatcher view = request.getRequestDispatcher("internetError.jsp");
			  view.forward(request,response);
		  } catch (ClassNotFoundException e) {
			  RequestDispatcher view = request.getRequestDispatcher("internetError.jsp");
			  view.forward(request,response);
		  } catch (Exception e) {
			  e.printStackTrace();
			  request.setAttribute("errorMsg", e.getMessage());
			  RequestDispatcher view = request.getRequestDispatcher("forgetpassword.jsp");
			  view.forward(request,response);
		  }
		  finally {
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
