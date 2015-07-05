package com.java.user;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.io.FilenameUtils;

import com.java.model.HtmlEmailSender;
import com.java.model.MultiPartEmailSender;

public class WriteFormServlet extends HttpServlet {
	private String host;
    private String port;
    private String user;
    private String pass;
    
    private Pattern pattern;
	private Matcher matcher;
    private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    public WriteFormServlet() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException  {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			String adminId = "";
			String category = "";
			String othersDesciptions = "";
			String descriptions = "";
			String location = "";
			String name = "";
			String email = "";
			String telephone = "";
			String finalimage = "";
			String currentTime = "";
			InputStream photo = null;
			InputStream photoTemp = null; 
			
			//Convert image to byte
			FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = null;
			
			// connecting to database
			  Connection con = null;  
			  Statement stmt = null;
			  ResultSet rs = null;
			  try {
				  Class.forName("com.mysql.jdbc.Driver");
				  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
				  stmt = con.createStatement();
				  
				  items = upload.parseRequest(request);
				  Iterator itr = items.iterator();
		            while (itr.hasNext()) {
		            
		                FileItem item = (FileItem) itr.next();
		                if (item.isFormField()) {
		                	 // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
		                    String namePara = item.getFieldName();
		                    if(namePara.equals("adminId")){
		                    	adminId = item.getString();
		                    }else if(namePara.equals("categoryDD")){
		                    	category = item.getString();
		                    }else if(namePara.equals("otherCategory")){
		                    	othersDesciptions = item.getString();
		                    }else if(namePara.equals("descriptions")){
		                    	descriptions = item.getString();
		                    }else if(namePara.equals("location")){
		                    	location = item.getString();
		                    }else if(namePara.equals("datetimepicker")){
		                    	currentTime = item.getString();
		                    }else if(namePara.equals("name")){
		                    	name = item.getString();
		                    	if(name.trim().equals("")){
		                    		name = "Sir/Mdm";
		                    	}
		                    }else if(namePara.equals("email")){
		                    	email = item.getString();
		                    	if(email.trim().equals("")){
		                    		email = "-";
		                    	}
		                    }else if(namePara.equals("telephone")){
		                    	telephone = item.getString();
		                    	if(telephone.trim().equals("")){
		                    		telephone = "-";
		                    	}
		                    }
		                } else {
		                	// Process form file field (input type="file").
		                    try {
		                    	// Process form file field (input type="file").
			                    photo = item.getInputStream();
			                    photoTemp = item.getInputStream();
			                    
		                    }catch (StringIndexOutOfBoundsException e) {
		                    	//user has no image

		                    } catch (Exception e) {
		                        e.printStackTrace();
		                    }
		                }
		            }
		           
		            //Backend validation					
					boolean validForm = true;
					ArrayList<String> errorList = new ArrayList<String>();
					if(currentTime.trim().equals("")){
						validForm = false;
						errorList.add("Date and Time field cannot be empty");
					}
					if(category.trim().equals("Others")){
						if(othersDesciptions.trim().equals("")){
							validForm = false;
							errorList.add("Please specify a category or select one from the above dropdown list");
						}else if(othersDesciptions.length() > 46){
							validForm = false;
							errorList.add("Other category field must be less than 45 words");
						}
					}
					if(location.trim().equals("")){
						validForm = false;
						errorList.add("Location field cannot be empty");
					}else if(location.length() > 46){
						validForm = false;
						errorList.add("Location field must be less than 45 words");
					}
					if(descriptions.trim().equals("")){
						validForm = false;
						errorList.add("Description field cannot be empty");
					}else if(descriptions.length() > 1001){
						validForm = false;
						errorList.add("Description field must be equal or less than 1000 words");
					}

					if(!email.trim().equals("-")){
						matcher = pattern.matcher(email);
						boolean matchEmail = matcher.matches();
						if(email.trim().length() > 46){
							validForm = false;
							errorList.add("Email field must be less than 45 words");
						}else if(matchEmail==false){
							validForm = false;
							errorList.add("Email field is not in the correct format");
						}
					}
					if(!telephone.trim().equals("-")){
						try{
							int telephoneNum =  Integer.parseInt(telephone);
							if(telephone.trim().length() != 8){
								validForm = false;
								errorList.add("Telephone field must have 8 numbers");
							}
						}catch(NumberFormatException e){
							validForm = false;
							errorList.add("Telephone field must contain numbers only");
						}
					}
				
				if(validForm == true){
				  //Email to department category
		           String categoryToUser = "";
				  if(category.equals("0")){
					  stmt.executeUpdate("INSERT INTO Form (admin_id_form,category,description,location,datetime,photo,user_name,user_email,user_telephone) VALUES ('"+adminId+"','"+"Others: "+othersDesciptions+"','"+descriptions+"','"+location+"','"+currentTime+"','"+finalimage+"','"+name+"','"+email+"','"+telephone+"')");
					  
					  rs = stmt.executeQuery("SELECT email,name,secondEmail FROM Admin WHERE admin_id='"+adminId+"'"); 
					  rs.next();
					  
					  categoryToUser = "Others-"+ othersDesciptions;
					  
					// reads SMTP server setting from web.xml file
					ServletContext context = getServletContext();
					host = context.getInitParameter("host");
					port = context.getInitParameter("port");
					user = context.getInitParameter("user");
					pass = context.getInitParameter("pass");
					  
					//outgoing message information
					String subject = "notify Request";
					//need to change link for the official version
					String content = "<b>Dear " + rs.getString(2) + "</b><br>";
					content += "You have a notify request. The details of the issues is as follows:</br>";
					content += "Date & Time: "+ currentTime +"<br>";
					content += "Location: "+ location +"<br>";
					content += "Category: Others-"+ othersDesciptions +"<br>";
					content += "Descriptions: "+ descriptions +"<br><br>";
					content += "<b>Reporter's Contact</b><br>";
					content += "Name: "+ name +"<br>";
					content += "Email: "+ email +"<br>";
					
					//If no photo is uploaded
					if(photo.available() == 0){
						content += "Telephone: "+ telephone +"<br>";
						content += "Photo: None" + "<br><br>";
						content += "We would appreciate if you can follow up on the matter. <br><br>";
						content += "Thank you, <br>";
						content += "notify team";
						
						HtmlEmailSender.sendHtmlEmail(host, port, user, pass, rs.getString(1), subject,content);
						if(rs.getObject(3) != null){
							HtmlEmailSender.sendHtmlEmail(host, port, user, pass, rs.getString(3), subject,content);
						}
					}else{
						content += "Telephone: "+ telephone +"<br>";
						content += "Photo: "+ "<br>" +"<img src=\"cid:image\" width='200px'>" +"<br><br>";
						content += "We would appreciate if you can follow up on the matter. <br><br>";
						content += "Thank you, <br>";
						content += "notify team";
						
						MultiPartEmailSender.sendHtmlEmail(host, port, user, pass, rs.getString(1), subject, content, photo);
						if(rs.getObject(3) != null){
							MultiPartEmailSender.sendHtmlEmail(host, port, user, pass, rs.getString(3), subject, content, photo);
						}
					}
					
				  }else{
					  stmt.executeUpdate("INSERT INTO Form (admin_id_form,category,description,location,datetime,photo,user_name,user_email,user_telephone) VALUES ('"+adminId+"','"+category+"','"+descriptions+"','"+location+"','"+currentTime+"','"+finalimage+"','"+name+"','"+email+"','"+telephone+"')");
					  
					  ResultSet rs2 = stmt.executeQuery("SELECT name FROM Admin WHERE admin_id='"+adminId+"'"); 
					  rs2.next();
					  String adminName = rs2.getString(1);
					  
					  rs = stmt.executeQuery("SELECT category,email FROM Category WHERE admin_id_cat='"+adminId+"' AND category_order='"+category+"'"); 
					  rs.next();
					  categoryToUser = rs.getString(1);
					  
					// reads SMTP server setting from web.xml file
					ServletContext context = getServletContext();
					host = context.getInitParameter("host");
					port = context.getInitParameter("port");
					user = context.getInitParameter("user");
					pass = context.getInitParameter("pass");
					  
					//outgoing message information
					String subject = "notify Request";
					//need to change link for the official version
					String content = "<b>Dear " + adminName + "'s staff in charge of " + rs.getString(1) + "</b><br>";
					content += "You have a notify request. The details of the issues is as follows:</br>";
					content += "Date & Time: "+ currentTime +"<br>";
					content += "Location: "+ location +"<br>";
					content += "Category: "+ rs.getString(1) +"<br>";
					content += "Descriptions: "+ descriptions +"<br><br>";
					content += "<b>Reporter's Contact</b><br>";
					content += "Name: "+ name +"<br>";
					content += "Email: "+ email +"<br>";
					
					//If no photo is uploaded
					if(photoTemp.available() == 0){
						content += "Telephone: "+ telephone +"<br>";
						content += "Photo: None" + "<br><br>";
						content += "We would appreciate if you can follow up on the matter. <br><br>";
						content += "Thank you, <br>";
						content += "notify team";
						
						HtmlEmailSender.sendHtmlEmail(host, port, user, pass, rs.getString(2), subject,content);
					}else{
						content += "Telephone: "+ telephone +"<br>";
						content += "Photo: "+ "<br>" +"<img src=\"cid:image\" width='200px'>" +"<br><br>";
						content += "We would appreciate if you can follow up on the matter. <br><br>";
						content += "Thank you, <br>";
						content += "notify team";
						
						MultiPartEmailSender.sendHtmlEmail(host, port, user, pass, rs.getString(2), subject, content, photoTemp);
					}
				  }
				  
				  //Email to user if they left their contact
				//outgoing message information
				  if(!email.trim().equals("-")){
					  //Get admin name
					  ResultSet rs2 = stmt.executeQuery("SELECT name FROM Admin WHERE admin_id='"+adminId+"'"); 
					  rs2.next();
					  String adminName = rs2.getString(1); 
					  
					// reads SMTP server setting from web.xml file
					ServletContext context = getServletContext();
					host = context.getInitParameter("host");
					port = context.getInitParameter("port");
					user = context.getInitParameter("user");
					pass = context.getInitParameter("pass");
					  
					String subject = "notify Report Confirmation";
					//need to change link for the official version
					String content = "<b>Dear " + name + "</b><br>";
					content += "Thank you for your notify report. Your request had been sent and recorded successfully. Details are as follow:</br>";
					content += "Date & Time: "+ currentTime +"<br>";
					content += "Location: "+ location +"<br>";
					content += "Category: "+ categoryToUser +"<br>";
					
					//If no photo is uploaded
					if(photo.available() == 0){
						content += "Descriptions: "+ descriptions +"<br>";
						content += "Photo: None" + "<br><br>";
						content += "Thank you, <br>";
						content += "From: "+adminName;
						
						HtmlEmailSender.sendHtmlEmail(host, port, user, pass, email, subject,content);
					}else{
						content += "Descriptions: "+ descriptions +"<br>";
						content += "Photo: "+ "<br>" +"<img src=\"cid:image\" width='200px'>" +"<br><br>";
						content += "Thank you, <br>";
						content += "From: "+adminName;
						
						MultiPartEmailSender.sendHtmlEmail(host, port, user, pass, email, subject, content, photo);	
					}
					
				  }
				  
				  response.sendRedirect("thankyou.jsp");
				//If form has invalid fields
				}else{
					ArrayList<String> categoryOrderList = new ArrayList<String>();
					ArrayList<String> categoryList = new ArrayList<String>();
					
					stmt = con.createStatement();
					ResultSet rs2 = stmt.executeQuery("SELECT category_order,category FROM Category WHERE admin_id_cat='"+adminId+"'");
					  
					while(rs2.next()){
						categoryOrderList.add(rs2.getString(1));
						categoryList.add(rs2.getString(2));
					}
					
					request.setAttribute("firstEntry", "true");
					request.setAttribute("categoryOrderList", categoryOrderList);
					request.setAttribute("categoryList", categoryList);
					request.setAttribute("errorList", errorList);
					RequestDispatcher view = request.getRequestDispatcher("form.jsp");
					view.forward(request,response);
				}
			  }catch (FileUploadException e) {
				  throw new ServletException("Servlet Could not display records.", e);
			  }catch (SQLException e) {
				  RequestDispatcher view = request.getRequestDispatcher("internetError.jsp");
				  view.forward(request,response);
			  }catch (ClassNotFoundException e) {
				  RequestDispatcher view = request.getRequestDispatcher("internetError.jsp");
				  view.forward(request,response);
			  } catch (Exception e) {
				  e.printStackTrace();
				  request.setAttribute("errorMsg", "Unable to submit form. You are not connected to the internet or Email server cannot be contacted.");
				  RequestDispatcher view = request.getRequestDispatcher("internetError.jsp");
				  view.forward(request,response);
				  //throw new ServletException("Email cannot be sent.", e);
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
