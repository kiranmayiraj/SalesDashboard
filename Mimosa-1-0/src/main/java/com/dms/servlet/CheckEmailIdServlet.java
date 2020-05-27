package com.dms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dms.db.EmailIdChecking;

@WebServlet(asyncSupported = true, urlPatterns = { "/CheckEmailIdServlet" })
public class CheckEmailIdServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public CheckEmailIdServlet() 
    {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String emailId = request.getParameter("emailId");
		
		String cxt = this.getServletContext().getRealPath("/");
		EmailIdChecking eic = new EmailIdChecking();
		boolean bool = eic.checkEmailId(emailId, cxt);
		String xmlResponse = "";
		if(!bool)
		{
			//System.out.println(""+emailId+" is already is use");
			xmlResponse = "<Checking>";
	    	xmlResponse = xmlResponse + "<Message>" + "Email Id is already in use." + "</Message>";
	    	xmlResponse = xmlResponse + "</Checking>";
		}
		else
	    {
			//System.out.println(""+emailId+" is available");
	        xmlResponse = "<Checking>";
	    	xmlResponse = xmlResponse + "<Message>" + "Email Id is available." + "</Message>";
	    	xmlResponse = xmlResponse + "</Checking>";
	    }
		response.getWriter().append(xmlResponse);
	}
}