package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.bean.User;
import com.dms.db.EmailIdChecking;
import com.dms.db.Registration;

@WebServlet(asyncSupported = true, urlPatterns = { "/RegistrationServlet" })
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegistrationServlet() 
    {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String storeName = request.getParameter("storeName");
		String password = request.getParameter("password");
		String emailId = request.getParameter("emailId");
		String phoneNo = request.getParameter("phoneNo");
		
		User user = new User();
		user.setStorename(storeName);
		user.setPassword(password);
		user.setEmailId(emailId);
		user.setPhoneNo(phoneNo);
		String cxt = this.getServletContext().getRealPath("/");
		EmailIdChecking eic = new EmailIdChecking();
		boolean bool = eic.checkEmailId(emailId, cxt);
		String xmlResponse = "<Registration>";
		if(!bool)
		{
			xmlResponse = xmlResponse + "<Message>" + "Registration failed. Email ID already exists." + "</Message>";
			xmlResponse = xmlResponse + "</Registration>";
		}
		else
		{
			Registration reg = new Registration();
			reg.RegisterUser(user, cxt);
			xmlResponse = xmlResponse + "<Message>" + "Registration is successful" + "</Message>";
			xmlResponse = xmlResponse + "</Registration>";
		}	
		response.getWriter().append(xmlResponse);
	}
}