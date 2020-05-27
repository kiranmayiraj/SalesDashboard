package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.bean.User;
import com.dms.db.ChangePassword;

@WebServlet(asyncSupported = true, urlPatterns = { "/ChangePasswordServlet" })
public class ChangePasswordServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public ChangePasswordServlet() 
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
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmNewPassword = request.getParameter("confirmNewPassword");
		
		if(newPassword.equals(confirmNewPassword))
		{
			User user = new User();
			user.setEmailId(emailId);
			user.setPassword(newPassword);
			
			String cxt = this.getServletContext().getRealPath("/");
			ChangePassword cp = new ChangePassword();
			
			boolean success = cp.changePassword(user, currentPassword, cxt);
			if(success)
			{
				request.setAttribute("message","Password is changed successfully.");
				String page = "settings.jsp";				
				request.getRequestDispatcher(page).forward(request, response);
			}
			else
			{
				request.setAttribute("message","Password couldn't be changed.");
				String page = "settings.jsp";
				request.getRequestDispatcher(page).forward(request, response);
			}
		}
		else
		{
			request.setAttribute("message","Your new password & confirm new password don't match.");
			String page = "settings.jsp";				
			request.getRequestDispatcher(page).forward(request, response);
		}
	}
}
