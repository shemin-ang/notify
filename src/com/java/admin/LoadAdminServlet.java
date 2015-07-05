package com.java.admin;

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

public class LoadAdminServlet extends HttpServlet {
	  public void doPost(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		  
		  //check if login and password match
		  String adminIdForAdmin = request.getParameter("adminId").trim();
		  
		  // connecting to database
		  Connection con = null;  
		  Statement stmt = null;
		  ResultSet rs = null;
		  try {
			  Class.forName("com.mysql.jdbc.Driver");
			  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
			  stmt = con.createStatement();
			  rs = stmt.executeQuery("SELECT name,email,secondEmail FROM Admin WHERE admin_id='"+adminIdForAdmin+"'");
			  
			  String name = "";
			  String email = "";
			  String secondEmail = "";
			  String url = "";
			  String address = "";
			  String message = "";
			  String noCompany = "false";
			  
			  if(rs.isBeforeFirst()){
				  rs.next();
				  name = rs.getObject(1).toString();
				  email = rs.getObject(2).toString();
				  if(rs.getObject(3) == null){
					  secondEmail = "";
				  }else{
					  secondEmail = rs.getObject(3).toString();
				  }
			  }
			  
			  ResultSet rs2 = stmt.executeQuery("SELECT url,address,logo FROM Company WHERE admin_id_co='"+adminIdForAdmin +"'");
			  if(rs2.isBeforeFirst()){
				  rs2.next();
				  url = rs2.getObject(1).toString();
				  address = rs2.getObject(2).toString();
			  }else{
				  noCompany = "true";
			  }
				
			  ResultSet rs3 = stmt.executeQuery("SELECT message FROM Submission WHERE admin_id_sub='"+adminIdForAdmin +"'");
			  if(rs3.isBeforeFirst()){
				  rs3.next();
				  message = rs3.getObject(1).toString();
			  }else{
				  ResultSet rs4 = stmt.executeQuery("SELECT message FROM Submission WHERE admin_id_sub=2");
				  rs4.next();
				  message = rs4.getObject(1).toString();
			  }
			  
			  ArrayList<String> categoryList = new ArrayList<String>();
			  ArrayList<String> categoryEmailList = new ArrayList<String>();
			  ResultSet rs5 = stmt.executeQuery("SELECT category,email FROM Category WHERE admin_id_cat='"+adminIdForAdmin +"'");
			  while(rs5.next()){
				  categoryList.add(rs5.getString(1));
				  categoryEmailList.add(rs5.getString(2));
			  }
			  
			  ArrayList<String> errorCompanyList = new ArrayList<String>();
			  ArrayList<String> errorCategoryList = new ArrayList<String>();
			  ArrayList<String> errorSubmissionList = new ArrayList<String>();
			  ArrayList<String> errorAccountList = new ArrayList<String>();
				
			  request.setAttribute("firstEntry", "true");
			  request.setAttribute("url", url);
			  request.setAttribute("address", address);
			  request.setAttribute("message", message);
			  request.setAttribute("name", name);
			  request.setAttribute("email", email);
			  request.setAttribute("secondEmail", secondEmail);
			  request.setAttribute("noCompany", noCompany);
			  request.setAttribute("categoryList", categoryList);
			  request.setAttribute("categoryEmailList", categoryEmailList);
			  
			  request.setAttribute("errorCompanyList",errorCompanyList);
			  request.setAttribute("errorCategoryList",errorCategoryList);
			  request.setAttribute("errorSubmissionList",errorSubmissionList);
			  request.setAttribute("errorAccountList",errorAccountList);
			  
			  RequestDispatcher view = request.getRequestDispatcher("admin.jsp");
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
