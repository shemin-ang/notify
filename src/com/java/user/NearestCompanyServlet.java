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

public class NearestCompanyServlet extends HttpServlet {
	  public void doPost(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		  
		  //Remove user session in case the user submit the form again
		  HttpSession session = request.getSession(); 
		  session.setAttribute("adminId", null);
		  session.invalidate();
		  
		  //check if login and password match
		  String currentLat = request.getParameter("currentLat").trim();
		  String currentLng = request.getParameter("currentLng").trim();
		  
		  ArrayList<String> adminList = new ArrayList<String>();
		  ArrayList<String> distanceWithCompany = new ArrayList<String>();
		  
		  // connecting to database
		  Connection con = null;  
		  Statement stmt = null;
		  ResultSet rs = null;
		  try {
			  Class.forName("com.mysql.jdbc.Driver");
			  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
			  stmt = con.createStatement();
			  rs = stmt.executeQuery("SELECT latitude,longitude,admin_id_co FROM Company"); 
			  
			  // loop all company's coordinates and retrieve those nearest to user
			  while(rs.next()){
				  double companyLat = rs.getDouble(1);
				  double companyLng = rs.getDouble(2);
				  String companyId = rs.getString(3);
				  
				  //Check if distance is near user
				  //Distance is in kilometer
				  if(!companyId.equals("2")){
					  double distance = haversine(Double.parseDouble(currentLat),Double.parseDouble(currentLng),companyLat,companyLng);
					  //Dont take notify's logo
					  distanceWithCompany.add(distance+"_"+companyId);
				  }
			  }
			  
			  	//sort the list
				Collections.sort(distanceWithCompany);
				
			  for(int i = 0; i<distanceWithCompany.size(); i++){
				  String[] admins = distanceWithCompany.get(i).split("_");
				  adminList.add(admins[1]);
			  }
			  
			  request.setAttribute("firstEntry", "false");
			  request.setAttribute("adminList", adminList);
			  request.setAttribute("errorMsg", "");
			  RequestDispatcher view = request.getRequestDispatcher("home.jsp");
			  view.forward(request,response);
			
		  } catch (SQLException e) {
			  //throw new ServletException("Servlet Could not display records.", e);
			  request.setAttribute("firstEntry", "false");
			  request.setAttribute("errorMsg", "You are not connected to the internet.");
			  RequestDispatcher view = request.getRequestDispatcher("home.jsp");
			  view.forward(request,response);
		  } catch (ClassNotFoundException e) {
			  request.setAttribute("firstEntry", "false");
			  request.setAttribute("errorMsg", "You are not connected to the internet.");
			  RequestDispatcher view = request.getRequestDispatcher("home.jsp");
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
	  
	  //Calculate distance from user using haversine formula
	  public static final double R = 6372.8; // In kilometers
	  public static double haversine(double lat1, double lon1, double lat2, double lon2) {
		  double dLat = Math.toRadians(lat2 - lat1);
		  double dLon = Math.toRadians(lon2 - lon1);
		  lat1 = Math.toRadians(lat1);
		  lat2 = Math.toRadians(lat2);
 
		  double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		  double c = 2 * Math.asin(Math.sqrt(a));
		  return R * c;
	  }
  }
