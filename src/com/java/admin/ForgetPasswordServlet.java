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

public class ForgetPasswordServlet extends HttpServlet {
	private Pattern pattern;
	private Matcher matcher;
    private String host;
    private String port;
    private String user;
    private String pass;
    
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
     
	public ForgetPasswordServlet() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
    
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		  
		  //check if login match email format (backend check)
		  String email = request.getParameter("email").trim();
		  matcher = pattern.matcher(email);
		  boolean matchEmail = matcher.matches();
		  
		  if(matchEmail == true){
			  //Check if email exist in database
			  // connecting to database
			  Connection con = null;  
			  Statement stmt = null;
			  ResultSet rs = null;
			  try {
				  Class.forName("com.mysql.jdbc.Driver");
				  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
				  stmt = con.createStatement();
				  rs = stmt.executeQuery("SELECT admin_id,name FROM Admin WHERE email='"+email+"'");
				  
				  // displaying records
				  if(!rs.isBeforeFirst()){
					 request.setAttribute("errorMsg", "Email does not exist.");
			 		 RequestDispatcher view = request.getRequestDispatcher("forgetpassword.jsp");
					 view.forward(request,response);
		         }else{
		        	//Generate a random id
		 			String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		 			int N = alphabet.length();
		 			Random r = new Random();
		 			String id = "";
		 			for (int i = 0; i < 30; i++) {
		 				char random = alphabet.charAt(r.nextInt(N));
		 				id += Character.toString(random);
		 			}
		 			rs.next();
		 			String adminId = rs.getObject(1).toString();
		 			String name = rs.getObject(2).toString();
		 			stmt.executeUpdate("INSERT INTO ResetPassword (reset_id,admin_id_reset) VALUES ('"+id+"','"+adminId+"')");

		 			//outgoing message information
		 	        String subject = "Password Recovery";
		 	        //need to change link for the official version
		 	        String content = "<b>Dear " + name + ",</b><br>";
		 	        content += "Someone has requested a link to change your password, and you can do this through the link below.</br>";
		 	        content += "<a href='http://localhost:8081/notify/resetpassword.jsp?id="+id+"'>http://localhost:8081/notify/resetpassword.jsp?id=" + id + "</a>";
		 	        content += "<br><br> If you didn't request this, please ignore this email. Your password won't change until you access the link above and create a new one.<br><br>";
		 	        content += "Thank you, <br>";
		 	        content += "notify team";
		 	        
		 	        // reads SMTP server setting from web.xml file
					ServletContext context = getServletContext();
					host = context.getInitParameter("host");
					port = context.getInitParameter("port");
					user = context.getInitParameter("user");
					pass = context.getInitParameter("pass");
		 	        
	 	        	HtmlEmailSender.sendHtmlEmail(host, port, user, pass, email, subject,content);
	 	        	String successMsg = "An email with a reset password link has been sent to your email.";
	 	        	request.setAttribute("successMsg", successMsg);
	 	        	RequestDispatcher view = request.getRequestDispatcher("forgetpassword.jsp");
	 				view.forward(request,response);
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
		  }else{
			  request.setAttribute("errorMsg", "Email is not in the correct format.");
			  RequestDispatcher view = request.getRequestDispatcher("forgetpassword.jsp");
			  view.forward(request,response);
		  }
	  }
  }
