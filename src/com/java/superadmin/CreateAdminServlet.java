package com.java.superadmin;

import javax.servlet.*;
import javax.servlet.http.*;

import org.jasypt.util.password.BasicPasswordEncryptor;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.java.model.HtmlEmailSender;

public class CreateAdminServlet extends HttpServlet {
	private String host;
    private String port;
    private String user;
    private String pass;
    
    private Pattern pattern;
	private Matcher matcher;
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public CreateAdminServlet() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
    
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException  {
		doPost(req,res);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			//encrypting the password
			BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
			
			//Email validation
			matcher = pattern.matcher(email);
			boolean matchEmail = matcher.matches();
			if(matchEmail==true){			
	 			//Insert to database
	 			Connection con = null;  
				  Statement stmt = null;
				  ResultSet rs = null;
				  try {
					  Class.forName("com.mysql.jdbc.Driver");
					  con =DriverManager.getConnection("jdbc:mysql://aaha26bpgdsl3g.c9jzdke6xxle.ap-southeast-1.rds.amazonaws.com:3306/ebdb","notifysg","yestifysg");
					  stmt = con.createStatement();
					  rs = stmt.executeQuery("SELECT admin_id FROM Admin WHERE email='"+email+"'");
					  
					  if(!rs.isBeforeFirst()){
						//Randomly generate a password, 8 characters long
						String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
			 			int N = alphabet.length();
			 			Random r = new Random();
			 			String password = "";
			 			for (int i = 0; i < 8; i++) {
			 				char random = alphabet.charAt(r.nextInt(N));
			 				password += Character.toString(random);
			 			}

			 			String encryptedPassword = passwordEncryptor.encryptPassword(password);
			 			stmt.executeUpdate("INSERT INTO Admin (name,email,password) VALUES ('"+name+"','"+email+"','"+encryptedPassword+"')");
			 			
						//outgoing message information
			 	        String subject = "notify Account Created";
			 	        //need to change link for the official version
			 	        String content = "<b>Dear " + name + "</b><br>";
			 	        content += "Thank you for registering with notify. Below is your login email and password:</br>";
			 	        content += "Email: "+ email +"<br>";
			 	        content += "Password: "+ password + "<br><br>";
			 	        content += "Please remember to change your password after you login. <br><br>";
			 	        content += "Thank you, <br>";
			 	        content += "notify team";
			 	        
			 	        // reads SMTP server setting from web.xml file
						ServletContext context = getServletContext();
						host = context.getInitParameter("host");
						port = context.getInitParameter("port");
						user = context.getInitParameter("user");
						pass = context.getInitParameter("pass");
			 	        
		 	        	HtmlEmailSender.sendHtmlEmail(host, port, user, pass, email, subject,content);
		 	        	String successMsg = "Registration successful.";
		 	        	request.setAttribute("successMsg", successMsg);
		 	        	RequestDispatcher view = request.getRequestDispatcher("register.jsp");
		 				view.forward(request,response);
					  
					  }else{
			        	 request.setAttribute("errorMsg", "Email already exist.");
				 		 RequestDispatcher view = request.getRequestDispatcher("register.jsp");
						 view.forward(request,response);
			         }
		 				
				  }catch (SQLException e) {
					  RequestDispatcher view = request.getRequestDispatcher("internetError.jsp");
					  view.forward(request,response);
				  }catch (ClassNotFoundException e) {
					  RequestDispatcher view = request.getRequestDispatcher("internetError.jsp");
					  view.forward(request,response);
				  }catch (Exception e) {
					  e.printStackTrace();
					  request.setAttribute("errorMsg", e.getMessage());
					  RequestDispatcher view = request.getRequestDispatcher("register.jsp");
					  view.forward(request,response);
				  }
			}else{
				request.setAttribute("errorMsg", "Email is of the wrong format");
				  RequestDispatcher view = request.getRequestDispatcher("register.jsp");
				  view.forward(request,response);
			}
	}
}
