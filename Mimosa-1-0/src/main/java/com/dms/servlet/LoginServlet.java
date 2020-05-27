package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dms.bean.User;
import com.dms.db.Login;

@WebServlet(asyncSupported = true, urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public LoginServlet() 
    {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String emailId = request.getParameter("lemailId");
		String password = request.getParameter("lpassword");
		User user = new User();
		user.setEmailId(emailId);
		user.setPassword(password);
		String cxt = this.getServletContext().getRealPath("/");
		
		Login log = new Login();
		int rows = log.authenticateUser(user, cxt);
		if(rows==1)
		{
			HttpSession session = request.getSession(true);
			session.setAttribute("emailId", emailId);
			session.removeAttribute("loginFailure");
			response.sendRedirect("executiveSummary.html");
		}
		else
		{
			HttpSession session = request.getSession(true);
			session.setAttribute("loginFailure", "Email ID or Password is incorrect. Please try again.");
			response.sendRedirect("Welcome.jsp");
		}
	}
}
