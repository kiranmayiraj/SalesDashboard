package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(asyncSupported = true, urlPatterns = { "/LogoutServlet" })
public class LogoutServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public LogoutServlet() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//System.out.println("Logout Servlet");
		HttpSession session = request.getSession();
	    session.invalidate();
	    response.sendRedirect("Welcome.jsp");
	}

}
