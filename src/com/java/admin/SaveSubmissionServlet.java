package com.java.admin;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;


public class SaveSubmissionServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException  {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			String adminId = "";			
			String message = "";
			InputStream incentive = null;
			
			//Convert image to byte
			FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = null;
			
			// connecting to database
			  Connection con = null;  
			  ResultSet rs = null;
			  PreparedStatement stmt = null;
			  PreparedStatement stmt2 = null;
			  PreparedStatement stmt3 = null;
			  Statement stmt4 = null;
			  try {
				  Class.forName("com.mysql.jdbc.Driver");
				  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
				  
				  items = upload.parseRequest(request);
				  Iterator itr = items.iterator();
		            while (itr.hasNext()) {
		            
		                FileItem item = (FileItem) itr.next();
		                if (item.isFormField()) {
		                	 // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
		                    String namePara = item.getFieldName();
		                    if(namePara.equals("adminId")){
		                    	adminId = item.getString();
		                    }else if(namePara.equals("thankMessage")){
		                    	message = item.getString();
		                    }
		                } else {
		                	// Process form file field (input type="file").
		                    try {
		                    	incentive=item.getInputStream();

		                    }catch (StringIndexOutOfBoundsException e) {
		                    	//admin has no image
		                    	
		                    } catch (Exception e) {
		                        e.printStackTrace();
		                    }
		                }
		            }
		            
		            //Backend validation
		            ArrayList<String> errorSubmissionList = new ArrayList<String>();
		            boolean validSubmission = true;
		            if(message.trim().equals("")){
		            	validSubmission = false;
		            	errorSubmissionList.add("Message field cannot be empty");
		            }
		            
		            if(validSubmission){
			            stmt = con.prepareStatement("SELECT admin_id_sub FROM Submission WHERE admin_id_sub='"+adminId+"'");
			            rs = stmt.executeQuery();
			            if(!rs.isBeforeFirst()){
			            	if(incentive.available()!=0){
			            		//If doesn't exist in DB and user uploads a submission image
			            		stmt2 = con.prepareStatement("INSERT INTO Submission (admin_id_sub,message,incentive) VALUES (?,?,?)");  
					        	stmt2.setString(1, adminId);
						        stmt2.setString(2, message);
						        stmt2.setBinaryStream(3, incentive);
						        stmt2.execute();
			            	}else{
			            		//Get default submission image from notify
			            		//Insert into admin
			            		stmt3 = con.prepareStatement("SELECT incentive FROM Submission WHERE admin_id_sub=2");
						    	ResultSet resultSet2 = stmt3.executeQuery(); 
						    	
						    	resultSet2.next();
	
							    Blob image2 = resultSet2.getBlob(1);
			            		
			            		stmt2 = con.prepareStatement("INSERT INTO Submission (admin_id_sub,message,incentive) VALUES (?,?,?)");  
					        	stmt2.setString(1, adminId);
						        stmt2.setString(2, message);
						        stmt2.setBinaryStream(3, image2.getBinaryStream());
						        stmt2.execute();
			            	} 
			            }else{
			            	if(incentive.available()!=0){
			            		//If exist in DB and user uploads a submission image
			            		stmt2 = con.prepareStatement("UPDATE Submission SET message=?,incentive=? WHERE admin_id_sub='"+adminId+"'");
					        	stmt2.setString(1, message);
						        stmt2.setBinaryStream(2, incentive);
						        stmt2.execute();
			            	}else{
			            		stmt2 = con.prepareStatement("UPDATE Submission SET message=? WHERE admin_id_sub='"+adminId+"'");
					        	stmt2.setString(1, message);
						        stmt2.execute();
			            	}
				       }
			            request.setAttribute("successMsg3", "Submision details successfully changed");
						RequestDispatcher view = request.getRequestDispatcher("admin.jsp");
						view.forward(request,response);
		          }else{
		        	  Class.forName("com.mysql.jdbc.Driver");
					  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
		        	  stmt4 = con.createStatement();
		        	  rs = stmt4.executeQuery("SELECT name,email,secondEmail FROM Admin WHERE admin_id='"+adminId+"'");
					  
					  String name = "";
					  String email = "";
					  String secondEmail = "";
					  String url = "";
					  String address = "";
					  //String message = "";
					  
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
					  
					  ResultSet rs2 = stmt4.executeQuery("SELECT url,address,logo FROM Company WHERE admin_id_co='"+adminId +"'");
					  if(rs2.isBeforeFirst()){
						  rs2.next();
						  url = rs2.getObject(1).toString();
						  address = rs2.getObject(2).toString();
					  }
					  /*
					  ResultSet rs3 = stmt.executeQuery("SELECT message FROM Submission WHERE admin_id_sub='"+adminIdForAdmin +"'");
					  if(rs3.isBeforeFirst()){
						  rs3.next();
						  message = rs3.getObject(1).toString();
					  }else{
						  ResultSet rs4 = stmt.executeQuery("SELECT message FROM Submission WHERE admin_id_sub=2");
						  rs4.next();
						  message = rs4.getObject(1).toString();
					  }
					  */
					  ArrayList<String> categoryList = new ArrayList<String>();
					  ArrayList<String> categoryEmailList = new ArrayList<String>();
					  ResultSet rs5 = stmt4.executeQuery("SELECT category,email FROM Category WHERE admin_id_cat='"+adminId +"'");
					  while(rs5.next()){
						  categoryList.add(rs5.getString(1));
						  categoryEmailList.add(rs5.getString(2));
					  }
					  
					  ArrayList<String> errorCompanyList = new ArrayList<String>();
					  ArrayList<String> errorCategoryList = new ArrayList<String>();
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
		          }
			  }catch (FileUploadException e) {
				  throw new ServletException("Servlet Could not display records.", e);
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
						  stmt2.close();
						  stmt2 = null;
					  }
					  if(con != null) {
						  con.close();
						  con = null;
					  }
				  } catch (SQLException e) {}	  
			  }
	}
	
}
