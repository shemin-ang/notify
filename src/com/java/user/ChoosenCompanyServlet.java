package com.java.user;

//Loading required libraries
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class ChoosenCompanyServlet extends HttpServlet {
	  public void doPost(HttpServletRequest request, HttpServletResponse response) 
			  throws IOException, ServletException{
		  
		  //check if login and password match
		  String adminIdChoosen = request.getParameter("adminId").trim();
		  
		  //Store admin id into session
		  HttpSession session = request.getSession(); 
		  session.setAttribute("adminId",adminIdChoosen);
		  
		  response.sendRedirect("form.jsp");
	  }
  }
