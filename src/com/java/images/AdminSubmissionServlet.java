package com.java.images;

//Loading required libraries
import java.io.*;
import java.util.*;

import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jasypt.util.password.BasicPasswordEncryptor;

public class AdminSubmissionServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		doPost(request,response);
	}  
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		  
		  String adminId = request.getParameter("id");
		  
		  // connecting to database
		  Connection con = null;  
		  PreparedStatement stmt = null;
		  PreparedStatement stmt2 = null;
		  ResultSet rs = null;
		  try {			  
			  Class.forName("com.mysql.jdbc.Driver");
				
				Connection conn =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
			    
			    stmt = conn.prepareStatement("SELECT incentive FROM Submission WHERE admin_id_sub="+adminId);
			    ResultSet resultSet = stmt.executeQuery();
			    
			    if(resultSet.isBeforeFirst()){
				    resultSet.next();
				    
				    Blob image = resultSet.getBlob(1);
	
				    OutputStream output = response.getOutputStream();
				    response.setContentType("image/jpg");
				    
				    byte[] imgData = null;
				    if(image.length()==0){
				    	stmt2 = conn.prepareStatement("SELECT incentive FROM Submission WHERE admin_id_sub=2");
				    	ResultSet resultSet2 = stmt2.executeQuery(); 
				    	
				    	resultSet2.next();

					    Blob image2 = resultSet2.getBlob(1);

					    imgData = image2.getBytes(1, (int) image2.length());
					    
				    }else{
				    	imgData = image.getBytes(1, (int) image.length());
				    }
				    
				    output.write(imgData); // even here we got the same as below.
				    output.flush();
				    output.close();
				    
				    conn.close();
			    }else{
			    	stmt2 = conn.prepareStatement("SELECT incentive FROM Submission WHERE admin_id_sub=2");
			    	ResultSet resultSet2 = stmt2.executeQuery(); 
			    	resultSet2.next();

				    Blob image = resultSet2.getBlob(1);
	
				    OutputStream output = response.getOutputStream();
				    response.setContentType("image/jpg");
				    
				    byte[] imgData = image.getBytes(1, (int) image.length());
				    
				    output.write(imgData); // even here we got the same as below.
				    output.flush();
				    output.close();
				    
				    conn.close();
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
