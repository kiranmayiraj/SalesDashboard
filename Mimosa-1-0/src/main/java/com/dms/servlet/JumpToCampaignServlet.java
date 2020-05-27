package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = { "/JumpToCampaignServlet" })
public class JumpToCampaignServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JumpToCampaignServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String seg = request.getParameter("segment");
		String xmlResponse = "<JTCS>";
		xmlResponse = xmlResponse + "<Page>" + "campaignAnalysis.html" + "</Page>";
		xmlResponse = xmlResponse + "<Seg>" + seg + "</Seg>";
		xmlResponse = xmlResponse + "</JTCS>";

		response.getWriter().append(xmlResponse);
	}
}