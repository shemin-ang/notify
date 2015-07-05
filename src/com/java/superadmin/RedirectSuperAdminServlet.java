package com.java.superadmin;

//Loading required libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class RedirectSuperAdminServlet extends HttpServlet {
	  
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		  
		  doPost(request,response);
	  }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		  
		  response.sendRedirect("create.jsp");
	  }
  }
