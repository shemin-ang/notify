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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SaveCategoryServlet extends HttpServlet {
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public SaveCategoryServlet() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException  {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			String adminId = request.getParameter("adminId");		
			String category = request.getParameter("category");			
			String categoryEmail = request.getParameter("categoryEmail");
			String category2 = request.getParameter("category2");
			String categoryEmail2 = request.getParameter("categoryEmail2");
			String category3 = request.getParameter("category3");
			String categoryEmail3 = request.getParameter("categoryEmail3");
			String category4 = request.getParameter("category4");
			String categoryEmail4 = request.getParameter("categoryEmail4");
			String category5 = request.getParameter("category5");
			String categoryEmail5 = request.getParameter("categoryEmail5");
			
			//Backend validation
			ArrayList<String> categoryList = new ArrayList<String>();
			ArrayList<String> categoryEmailList = new ArrayList<String>();
			ArrayList<String> errorCategoryList = new ArrayList<String>();
			boolean validCategory = true;
			
			matcher = pattern.matcher(categoryEmail);
			boolean matchCategoryEmail = matcher.matches();
			matcher = pattern.matcher(categoryEmail2);
			boolean matchCategoryEmail2 = matcher.matches();
			matcher = pattern.matcher(categoryEmail3);
			boolean matchCategoryEmail3 = matcher.matches();
			matcher = pattern.matcher(categoryEmail4);
			boolean matchCategoryEmail4 = matcher.matches();
			matcher = pattern.matcher(categoryEmail5);
			boolean matchCategoryEmail5 = matcher.matches();
			
			if(category.trim().equals("")){
				if(!categoryEmail.trim().equals("")){
					validCategory = false;
					errorCategoryList.add("First Category field must be provided for this email.");
				}
			}else{
				if(categoryEmail.trim().equals("")){
					validCategory = false;
					errorCategoryList.add("First Email field must be provided for this category.");
				}else if(matchCategoryEmail == false){
					validCategory = false;
					errorCategoryList.add("First Email field is in the wrong format.");
				}
			}
			if(category2.trim().equals("")){
				if(!categoryEmail2.trim().equals("")){
					validCategory = false;
					errorCategoryList.add("Second Category field must be provided for this email.");
				}
			}else{
				if(categoryEmail2.trim().equals("")){
					validCategory = false;
					errorCategoryList.add("Second Email field must be provided for this category.");
				}else if(matchCategoryEmail2 == false){
					validCategory = false;
					errorCategoryList.add("Second Email field is in the wrong format.");
				}
			}
			if(category3.trim().equals("")){
				if(!categoryEmail3.trim().equals("")){
					validCategory = false;
					errorCategoryList.add("Third Category field must be provided for this email.");
				}
			}else{
				if(categoryEmail3.trim().equals("")){
					validCategory = false;
					errorCategoryList.add("Third Email field must be provided for this category.");
				}else if(matchCategoryEmail3 == false){
					validCategory = false;
					errorCategoryList.add("Third Email field is in the wrong format.");
				}
			}
			if(category4.trim().equals("")){
				if(!categoryEmail4.trim().equals("")){
					validCategory = false;
					errorCategoryList.add("Fourth Category field must be provided for this email.");
				}
			}else{
				if(categoryEmail4.trim().equals("")){
					validCategory = false;
					errorCategoryList.add("Fourth Email field must be provided for this category.");
				}else if(matchCategoryEmail4 == false){
					validCategory = false;
					errorCategoryList.add("Fourth Email field is in the wrong format.");
				}
			}
			if(category5.trim().equals("")){
				if(!categoryEmail5.trim().equals("")){
					validCategory = false;
					errorCategoryList.add("Fifth Category field must be provided for this email.");
				}
			}else{
				if(categoryEmail5.trim().equals("")){
					validCategory = false;
					errorCategoryList.add("Fifth Email field must be provided for this category.");
				}else if(matchCategoryEmail5 == false){
					validCategory = false;
					errorCategoryList.add("Fifth Email field is in the wrong format.");
				}
			}
			
			if(validCategory){
			//Get rid of any category that is empty
			if(!category.trim().equals("")){
				categoryList.add(category);
				categoryEmailList.add(categoryEmail);
			}
			if(!category2.trim().equals("")){
				categoryList.add(category2);
				categoryEmailList.add(categoryEmail2);
			}
			if(!category3.trim().equals("")){
				categoryList.add(category3);
				categoryEmailList.add(categoryEmail3);
			}
			if(!category4.trim().equals("")){
				categoryList.add(category4);
				categoryEmailList.add(categoryEmail4);
			}
			if(!category5.trim().equals("")){
				categoryList.add(category5);
				categoryEmailList.add(categoryEmail5);
			}
			
			// connecting to database
			  Connection con = null;  
			  Statement stmt = null;
			  ResultSet rs = null;
			  try {
				  Class.forName("com.mysql.jdbc.Driver");
				  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
				  stmt = con.createStatement();
				  rs = stmt.executeQuery("SELECT category FROM Category WHERE admin_id_cat='"+adminId+"'");
				  
				  //Check if user have existing category in database		  
				  if(!rs.isBeforeFirst()){
					  for(int i = 0; i<categoryList.size();i++){
						  stmt.executeUpdate("INSERT INTO Category (admin_id_cat,category_order,category,email) VALUES ('"+adminId+"','"+(i+1)+"','"+categoryList.get(i)+"','"+categoryEmailList.get(i)+"')");    
					  }		  
				  }else{
					  ArrayList<String> dbCategoryList = new ArrayList<String>();
					  while(rs.next()){
						  dbCategoryList.add(rs.getString(1));
					  }
					  if(dbCategoryList.size()>categoryList.size()){
						  //Update category to DB
						  for(int i = 0; i<categoryList.size();i++){
							  stmt.executeUpdate("UPDATE Category SET category='"+ categoryList.get(i) + "',email='"+ categoryEmailList.get(i) + "' WHERE admin_id_cat='"+adminId+"' AND category_order='"+(i+1)+"'");
						  }
						  //Delete extras from DB
						  int oldCatergoryCount = dbCategoryList.size() - categoryList.size();
						  for(int i = 0; i<oldCatergoryCount;i++){
							  stmt.executeUpdate("DELETE FROM Category WHERE admin_id_cat='"+adminId+"' AND category_order='"+(dbCategoryList.size()-i)+"'");
						  }
						  
					  }else if(dbCategoryList.size()==categoryList.size()){
						  for(int i = 0; i<categoryList.size();i++){
							  stmt.executeUpdate("UPDATE Category SET category='"+ categoryList.get(i) + "',email='"+ categoryEmailList.get(i) + "' WHERE admin_id_cat='"+adminId+"' AND category_order='"+(i+1)+"'");
						  }
					  }else{
						  int newCatergoryCount = categoryList.size() - dbCategoryList.size();
						//Update category to DB
						  for(int i = 0; i<dbCategoryList.size();i++){
							  stmt.executeUpdate("UPDATE Category SET category='"+ categoryList.get(i) + "',email='"+ categoryEmailList.get(i) + "' WHERE admin_id_cat='"+adminId+"' AND category_order='"+(i+1)+"'");
						  }
						//Add extras from DB
						  for(int i = 0; i<newCatergoryCount;i++){
							  stmt.executeUpdate("INSERT INTO Category (admin_id_cat,category_order,category,email) VALUES ('"+adminId+"','"+(dbCategoryList.size()+i+1)+"','"+categoryList.get(dbCategoryList.size()+i)+"','"+categoryEmailList.get(dbCategoryList.size()+i)+"')");    
						  }	
					  } 
				  }
				  request.setAttribute("successMsg2", "Category details successfully changed");
				  RequestDispatcher view = request.getRequestDispatcher("admin.jsp");
				  view.forward(request,response);
				  
			  }catch (SQLException e) {
				  throw new ServletException("Servlet Could not display records.", e);
			  } catch (ClassNotFoundException e) {
				  throw new ServletException("JDBC Driver not found.", e);
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
			
			}else{//If there are invalid fields
				Connection con = null;  
				  Statement stmt = null;
				  ResultSet rs = null;
				try {
					  Class.forName("com.mysql.jdbc.Driver");
					  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
					  stmt = con.createStatement();
					  rs = stmt.executeQuery("SELECT name,email,secondEmail FROM Admin WHERE admin_id='"+adminId+"'");
					  
					  String name = "";
					  String email = "";
					  String secondEmail = "";
					  String url = "";
					  String address = "";
					  String message = "";
					  
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
					  
					  ResultSet rs2 = stmt.executeQuery("SELECT url,address,logo FROM Company WHERE admin_id_co='"+adminId +"'");
					  if(rs2.isBeforeFirst()){
						  rs2.next();
						  url = rs2.getObject(1).toString();
						  address = rs2.getObject(2).toString();
					  }
						
					  ResultSet rs3 = stmt.executeQuery("SELECT message,incentive FROM Submission WHERE admin_id_sub='"+adminId +"'");
					  if(rs3.isBeforeFirst()){
						  rs3.next();
						  message = rs3.getObject(1).toString();
					  }
					  
					  categoryList.add(category);
					  categoryList.add(category2);
					  categoryList.add(category3);
					  categoryList.add(category4);
					  categoryList.add(category5);
					  categoryEmailList.add(categoryEmail);
					  categoryEmailList.add(categoryEmail2);
					  categoryEmailList.add(categoryEmail3);
					  categoryEmailList.add(categoryEmail4);
					  categoryEmailList.add(categoryEmail5);
					  
					  ArrayList<String> errorCompanyList = new ArrayList<String>();
					  ArrayList<String> errorSubmissionList = new ArrayList<String>();
					  ArrayList<String> errorAccountList = new ArrayList<String>();
					  
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
}
