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

public class ContactUsServlet extends HttpServlet {
	private String host;
    private String port;
    private String user;
    private String pass;
    
    private Pattern pattern;
	private Matcher matcher;
    private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    public ContactUsServlet() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException  {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String feedback = request.getParameter("feedback");
		           
            //Backend validation					
			boolean validContact = true;
			ArrayList<String> errorList = new ArrayList<String>();
			
			if(name.trim().equals("")){
				name = "anonymous";
			}else{
				if(name.length() > 45){
					validContact = false;
					errorList.add("Name field must be less than 45 words");
				}
			}

			if(!email.trim().equals("")){
				matcher = pattern.matcher(email);
				boolean matchEmail = matcher.matches();
				if(email.trim().length() > 46){
					validContact = false;
					errorList.add("Email field must be less than 45 words");
				}else if(matchEmail==false){
					validContact = false;
					errorList.add("Email field is not in the correct format");
				}
			}else{
				validContact = false;
				errorList.add("Email field cannot be empty");
			}
			
			if(feedback.trim().equals("")){
				validContact = false;
				errorList.add("Feedback field cannot be empty");
			}else if(feedback.length() > 100){
				validContact = false;
				errorList.add("Feedback field must be less than 100 words");
			}
			
			// connecting to database
			  Connection con = null;  
			  Statement stmt = null;
			  ResultSet rs = null;
			  try {
				  Class.forName("com.mysql.jdbc.Driver");
				  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
				  stmt = con.createStatement();
				  
				if(validContact == true){
					stmt.executeUpdate("INSERT INTO Contact (name,email,feedback) VALUES ('"+name+"','"+email+"','"+feedback+"')");
				 
					//Email to notify
					rs = stmt.executeQuery("SELECT email FROM Admin WHERE admin_id=2"); 
					rs.next();
					String notifyEmail = rs.getString(1);
					
					// reads SMTP server setting from web.xml file
					ServletContext context = getServletContext();
					host = context.getInitParameter("host");
					port = context.getInitParameter("port");
					user = context.getInitParameter("user");
					pass = context.getInitParameter("pass");
					
					//outgoing message information
					String subject = "notify Feedback";
					//need to change link for the official version
					String content = "<b>Hello notify team!</b><br>";
					content += "Someone had submitted a feedback as follow:<br>";
					content += "Name: "+ name +"<br>";
					content += "Email: "+ email +"<br>";
					content += "Feedback: "+ feedback +"<br>";
					
					HtmlEmailSender.sendHtmlEmail(host, port, user, pass, notifyEmail, subject,content);
				  
					//Email the user who submitted the feedback
					if(name.equals("anonymous")){
						name = "Sir/Mdm";
					}
					
					String userSubject = "Notify Feedback";
					//need to change link for the official version
					String userContent = "<b>Dear " + name + "</b><br>";
					userContent += "Your feedback has been received and our notify team will contact you as soon as possible. ";
					userContent += "Details of feedback are written below:</br>";
					userContent += "Name: "+ name +"<br>";
					userContent += "Email: "+ email +"<br>";
					userContent += "Feedback: "+ feedback +"<br><br>";
					userContent += "Thank you, <br>";
					userContent += "notify team";
					
					HtmlEmailSender.sendHtmlEmail(host, port, user, pass, email, userSubject,userContent);
					
					request.setAttribute("successMsg", "Your feedback has been recorded. We will try to contact you back ASAP.");
					RequestDispatcher view = request.getRequestDispatcher("contactus.jsp");
					view.forward(request,response);
					
				}else{
					//If form has invalid fields
					request.setAttribute("errorList", errorList);
					RequestDispatcher view = request.getRequestDispatcher("contactus.jsp");
					view.forward(request,response);
				}
			  
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
