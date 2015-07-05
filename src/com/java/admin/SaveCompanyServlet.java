package com.java.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SaveCompanyServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException  {
		doPost(req,res);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			String adminId = "";			
			String url = "";
			InputStream logo = null;
			String address = "";
			
			//Convert image to byte
			FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = null;
			
			// connecting to database
			  Connection con = null;  
			  PreparedStatement  stmt = null;
			  ResultSet rs = null;
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
		                    }else if(namePara.equals("preferredAdd")){
		                    	url = item.getString();
		                    }else if(namePara.equals("companyLocation")){
		                    	address = item.getString();
		                    }
		                } else {
		                	// Process form file field (input type="file").
		                    try {
	                        	logo = item.getInputStream();

		                    }catch (StringIndexOutOfBoundsException e) {
		                    	//admin has no image
		                    	
		                    } catch (Exception e) {
		                        e.printStackTrace();
		                    }
		                }
		            }
		            
		            //Backend validation
		            ArrayList<String> errorCompanyList = new ArrayList<String>();
		            boolean validCompany = true;
		            if(address.trim().equals("")){
		            	validCompany = false;
		            	errorCompanyList.add("Postal Code field cannot be empty");
		            }else if(address.trim().length() != 6){
		            	validCompany = false;
		            	errorCompanyList.add("Postal Code field can contain only 6 numbers");
		            }else{
		            	 try{
			            	int addressNum = Integer.parseInt(address);
		            	 }catch(NumberFormatException e){
			            	validCompany = false;
			            	errorCompanyList.add("Postal Code field can contain only 6 numbers");
			            }
		            }
		            
		            if(!url.trim().equals("")){
	            	stmt = con.prepareStatement("SELECT admin_id_co FROM Company WHERE url='"+url+"'");
		        	  ResultSet rs4 = stmt.executeQuery();
		        	  if(rs4.isBeforeFirst()){
		        		  rs4.next();
		        		  if(!rs4.getString(1).equals(adminId)){
			        		validCompany = false;
			        		url="";
				            errorCompanyList.add("Private Address id being used by another user");
		        		  }
		        	  }
		            }  
		        	  
		            //If new admin check if logo is uploaded
		            stmt = con.prepareStatement("SELECT logo FROM Company WHERE admin_id_co='"+adminId+"'");
		            ResultSet rs4 = stmt.executeQuery();
		            if(!rs4.isBeforeFirst()){
	        		  if(logo.available() == 0){
	        			  validCompany = false;
				          errorCompanyList.add("Logo field cannot be empty.");
	        		  }
		            }
		            
		        	if(validCompany == true){
			          //Insert or update into database 
			            stmt = con.prepareStatement("SELECT admin_id_co,address FROM Company WHERE admin_id_co='"+adminId+"'");
			            ResultSet rs2 = stmt.executeQuery();
			          if(!rs2.isBeforeFirst()){
			        	  //Convert postal code to coordinates
			        	  String lat = "";
			        	  String lng = "";
			        	  try{
								URL xmlUrl = new URL("https://maps.googleapis.com/maps/api/geocode/xml?address=Singapore"+address+"&sensor=false");
								InputStream in = xmlUrl.openStream();
								Document doc = parse(in);
								
								NodeList nodesFirst = doc.getElementsByTagName("result");
								Element elementFirst = (Element) nodesFirst.item(0);
								NodeList addressListFirst = elementFirst.getElementsByTagName("geometry");  
								Element elementSecond = (Element) addressListFirst.item(0);
						        NodeList locationList = elementSecond.getElementsByTagName("location");
						        Element elementThird = (Element) locationList.item(0);
						        NodeList latList = elementThird.getElementsByTagName("lat");
						        NodeList lngList = elementThird.getElementsByTagName("lng");
						        
						        lat = latList.item(0).getFirstChild().getTextContent();
						        lng = lngList.item(0).getFirstChild().getTextContent();
								
							}catch(Throwable t){
								lat = "0";
								lng = "0";
							}
			        	  
			        	  //Check if company's url is taken by others
			        	  stmt = con.prepareStatement("SELECT admin_id_co FROM Company WHERE url='"+url+"'");
			        	  ResultSet rs3 = stmt.executeQuery();
			        	  if(!rs3.isBeforeFirst()){
			        		  stmt = con.prepareStatement("INSERT INTO Company (admin_id_co,url,address,logo,latitude,longitude) VALUES (?,?,?,?,?,?)");  
			        		  stmt.setString(1, adminId);
					          stmt.setString(2, url);
					          stmt.setString(3, address);
					          stmt.setBinaryStream(4, logo);
					          stmt.setString(5, lat);
					          stmt.setString(6, lng);
					          stmt.execute();
					          
			        	  }
		  
			          }else{
			        	  rs2.next();
			        	  String postalCode = rs2.getString(2);
			        	  if(!postalCode.equals(address)){
			        		  String lat = "";
				        	  String lng = "";
			        		  //If user change postal code, recalculate lat lng
			        		  try{
								URL xmlUrl = new URL("https://maps.googleapis.com/maps/api/geocode/xml?address=Singapore"+address+"&sensor=false");
								InputStream in = xmlUrl.openStream();
								Document doc = parse(in);
								
								NodeList nodesFirst = doc.getElementsByTagName("result");
								Element elementFirst = (Element) nodesFirst.item(0);
								NodeList addressListFirst = elementFirst.getElementsByTagName("geometry");  
								Element elementSecond = (Element) addressListFirst.item(0);
						        NodeList locationList = elementSecond.getElementsByTagName("location");
						        Element elementThird = (Element) locationList.item(0);
						        NodeList latList = elementThird.getElementsByTagName("lat");
						        NodeList lngList = elementThird.getElementsByTagName("lng");
						        
						        lat = latList.item(0).getFirstChild().getTextContent();
						        lng = lngList.item(0).getFirstChild().getTextContent();	
								}catch(Throwable t){
									lat = "0";
									lng = "0";
								}
			        		  if(logo.available()==0){
			        			  stmt = con.prepareStatement("UPDATE Company SET url=?,address=?,latitude=?,longitude=? WHERE admin_id_co='"+adminId+"'");
			        			  stmt.setString(1, url);
						          stmt.setString(2, address);
						          stmt.setString(3, lat);
						          stmt.setString(4, lng);
						          stmt.execute();
			        		  }else{
			        			  stmt = con.prepareStatement("UPDATE Company SET url=?,address=?,logo=?,latitude=?,longitude=? WHERE admin_id_co='"+adminId+"'");
			        			  stmt.setString(1, url);
						          stmt.setString(2, address);
						          stmt.setBinaryStream(3, logo);
						          stmt.setString(4, lat);
						          stmt.setString(5, lng);
						          stmt.execute();
			        		  }
			        		
			        	  }else{
			        		  if(logo.available()==0){
				        		  stmt = con.prepareStatement("UPDATE Company SET url=?,address=? WHERE admin_id_co='"+adminId+"'");
						          stmt.setString(1, url);
						          stmt.setString(2, address);
						          stmt.execute();
			        		  }else{
			        			  stmt = con.prepareStatement("UPDATE Company SET url=?,address=?,logo=? WHERE admin_id_co='"+adminId+"'");
						          stmt.setString(1, url);
						          stmt.setString(2, address);
						          stmt.setBinaryStream(3, logo);
						          stmt.execute();
			        		  }
			        	  } 
			          }
			          request.setAttribute("successMsg1","Company details successfully changed");
			          RequestDispatcher view = request.getRequestDispatcher("admin.jsp");
			          view.forward(request,response);
						 
		        	 }else{//If there are invalid fields
		        		 rs = stmt.executeQuery("SELECT name,email,secondEmail FROM Admin WHERE admin_id='"+adminId+"'");
		   			  
			   			  String name = "";
			   			  String email = "";
			   			  String secondEmail = "";
			   			  //String url = "";
			   			  //String address = "";
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
			   			  /*
			   			  ResultSet rs2 = stmt.executeQuery("SELECT url,address,logo FROM Company WHERE admin_id_co='"+adminId +"'");
			   			  if(rs2.isBeforeFirst()){
			   				  rs2.next();
			   				  url = rs2.getObject(1).toString();
			   				  address = rs2.getObject(2).toString();
			   			  }*/
			   				
			   			  ResultSet rs3 = stmt.executeQuery("SELECT message,incentive FROM Submission WHERE admin_id_sub='"+adminId +"'");
			   			  if(rs3.isBeforeFirst()){
			   				  rs3.next();
			   				  message = rs3.getObject(1).toString();
			   			  }
			   			  
			   			  ArrayList<String> categoryList = new ArrayList<String>();
			   			  ArrayList<String> categoryEmailList = new ArrayList<String>();
			   			  ResultSet rs5 = stmt.executeQuery("SELECT category,email FROM Category WHERE admin_id_cat='"+adminId +"'");
			   			  while(rs5.next()){
			   				  categoryList.add(rs5.getString(1));
			   				  categoryEmailList.add(rs5.getString(2));
			   			  }
			   			  
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
					  }
					  if(con != null) {
						  con.close();
						  con = null;
					  }
				  } catch (SQLException e) {}	  
			  }
	}
	
	/**
	* Constructs a Document object by reading from an input stream.
	*/
    public static Document parse (InputStream is) {
        Document ret = null;
        DocumentBuilderFactory domFactory;
        DocumentBuilder builder;
 
        try {
            domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(false);
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();
 
            ret = builder.parse(is);
        }
        catch (Exception ex) {
            System.out.println("unable to load XML: " + ex);
        }
        return ret;
    }
}
