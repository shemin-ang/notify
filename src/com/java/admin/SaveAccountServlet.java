package com.java.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SaveAccountServlet extends HttpServlet {
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public SaveAccountServlet() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException  {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			String adminId = request.getParameter("adminId");			
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String secondEmail = request.getParameter("secondEmail");
			String oldPassword = request.getParameter("oldPwd");
			String password = request.getParameter("pwd");
			String confirmPassword = request.getParameter("confirmPwd");
			
			//for encryption
			BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
			
			//connecting to database
			  Connection con = null;  
			  Statement stmt = null;
			  ResultSet rs = null;
			  try {
				  Class.forName("com.mysql.jdbc.Driver");
				  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
				  stmt = con.createStatement();
				  rs = stmt.executeQuery("SELECT password FROM Admin WHERE admin_id='"+adminId+"'");
				  rs.next();
				  
				  
				  boolean validAccount = true;
				  ArrayList<String> errorAccountList = new ArrayList<String>();
				  if(name.trim().equals("")){
					  validAccount = false;
					  errorAccountList.add("Name field cannot be empty");
				  }else if(name.trim().length() > 45){
					  validAccount = false;
					  errorAccountList.add("Name field must be less than 45 words");
				  }
				  
				  if(email.trim().equals("")){
					  validAccount = false;
					  errorAccountList.add("Email field cannot be empty");
				  }else{
					  matcher = pattern.matcher(email);
					  boolean matchEmail = matcher.matches();
					  if(matchEmail==false){
						  validAccount = false;
						  errorAccountList.add("Email field is not in the correct format");
					  }else if(email.trim().length() > 147){
						  validAccount = false;
						  errorAccountList.add("Email field must be less than 150 words");
					  }
				  }
				  if(!secondEmail.trim().equals("")){
					  matcher = pattern.matcher(secondEmail);
					  boolean matchEmail = matcher.matches();
					  if(matchEmail==false){
						  validAccount = false;
						  errorAccountList.add("Email field is not in the correct format");
					  }else if(secondEmail.trim().length() > 147){
						  validAccount = false;
						  errorAccountList.add("SecondEmail field must be less than 150 words");
					  } 
				  }
				  
				  if(oldPassword.trim().equals("")){
					  //validAccount = false;
					  //errorAccountList.add("Old Password field cannot be empty");
				  }else{
					  if(oldPassword.length() < 8 || oldPassword.length() > 15){
						  validAccount = false;
						  errorAccountList.add("Old Password field must be between 8 to 15 characters");
					  }else if(!passwordEncryptor.checkPassword(oldPassword, rs.getString(1))){
						  if(!oldPassword.trim().equals("")){
							  validAccount = false;
							  errorAccountList.add("Old Password entered is incorrect");
						  }
					  }
				  }
				  if(password.trim().equals("")){
					  //validAccount = false;
					  //errorAccountList.add("New Password field cannot be empty");
				  }else{
					  if(password.length() < 8 || password.length() > 15){
						  validAccount = false;
						  errorAccountList.add("New Password field must be between 8 to 15 characters");
					  }
				  }
				  if(confirmPassword.trim().equals("")){
					  //validAccount = false;
					  //errorAccountList.add("Confirm Password field cannot be empty");
				  }else{
					  if(confirmPassword.length() < 8 || confirmPassword.length() > 15){
						  validAccount = false;
						  errorAccountList.add("Confirm Password field must be between 8 to 15 characters");
					  }else if(!password.equals(confirmPassword)){
						  validAccount = false;
						  errorAccountList.add("Confirm Password do not match New Password");
					  }
				  }
				  
				  if(validAccount){
					  String encryptedPassword = passwordEncryptor.encryptPassword(password);
					  //Insert or update into database
					  if(oldPassword.trim().equals("")){
						  stmt.executeUpdate("UPDATE Admin SET email='"+ email + "', name='"+ name + "', secondEmail='"+ secondEmail + "' WHERE admin_id='"+adminId+"'"); 
					  }else{
						  stmt.executeUpdate("UPDATE Admin SET email='"+ email + "',password='"+ encryptedPassword + "', name='"+ name + "', secondEmail='"+ secondEmail + "' WHERE admin_id='"+adminId+"'");  
					  }
			          request.setAttribute("successMsg4", "Account details successfully changed");
			          RequestDispatcher view = request.getRequestDispatcher("admin.jsp");
			          view.forward(request,response);
				  }else{
					  
					  //ResultSet rs1 = stmt.executeQuery("SELECT name,email FROM Admin WHERE admin_id='"+adminId+"'");
					  
					  //String name = "";
					  //String email = "";
					  String url = "";
					  String address = "";
					  String message = "";
					  /*
					  if(rs1.isBeforeFirst()){
						  rs1.next();
						  name = rs1.getObject(1).toString();
						  email = rs1.getObject(2).toString();
					  }
					  */
					  ResultSet rs2 = stmt.executeQuery("SELECT url,address,logo FROM Company WHERE admin_id_co='"+adminId +"'");
					  if(rs2.isBeforeFirst()){
						  rs2.next();
						  url = rs2.getObject(1).toString();
						  address = rs2.getObject(2).toString();
					  }
						
					  ResultSet rs3 = stmt.executeQuery("SELECT message FROM Submission WHERE admin_id_sub='"+adminId +"'");
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
					  ResultSet rs5 = stmt.executeQuery("SELECT category,email FROM Category WHERE admin_id_cat='"+adminId +"'");
					  while(rs5.next()){
						  categoryList.add(rs5.getString(1));
						  categoryEmailList.add(rs5.getString(2));
					  }
					  
					  ArrayList<String> errorCompanyList = new ArrayList<String>();
					  ArrayList<String> errorCategoryList = new ArrayList<String>();
					  ArrayList<String> errorSubmissionList = new ArrayList<String>();
						
					  request.setAttribute("firstEntry", "true");
					  request.setAttribute("url", url);
					  request.setAttribute("address", address);
					  request.setAttribute("message", message);
					  request.setAttribute("name", name);
					  request.setAttribute("email", email);
					  request.setAttribute("secondEmail", secondEmail);
					  request.setAttribute("categoryList", categoryList);
					  request.setAttribute("categoryEmailList", categoryEmailList);
					  
					  request.setAttribute("errorCompanyList",errorCompanyList);
					  request.setAttribute("errorCategoryList",errorCategoryList);
					  request.setAttribute("errorSubmissionList",errorSubmissionList);
					  request.setAttribute("errorAccountList",errorAccountList);
					  
					  RequestDispatcher view = request.getRequestDispatcher("admin.jsp");
					  view.forward(request,response);
				  }

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
