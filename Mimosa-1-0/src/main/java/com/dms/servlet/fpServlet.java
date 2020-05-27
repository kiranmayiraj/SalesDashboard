package com.dms.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;

import com.dms.bean.User;
import com.dms.db.EmailIdChecking;
import com.dms.db.PasswordReset;

@WebServlet(asyncSupported = true, urlPatterns = { "/fpServlet" })
public class fpServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
 
	private String host;
	private String email;
    private String pass;
    
    static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	
    public void init() 
    {
        // reads SMTP server setting from web.xml file
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        email = context.getInitParameter("email");
        pass = context.getInitParameter("pass");
    }
    public fpServlet() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		 String page = "forgotPassword.jsp";
		 request.getRequestDispatcher(page).forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String recipient = request.getParameter("emailId");
		String cxt = this.getServletContext().getRealPath("/");
		EmailIdChecking eic = new EmailIdChecking();
		boolean bool = eic.checkEmailId(recipient, cxt);
		String message = "";
		if(!bool)
		{
	        boolean success;
			try 
			{
				success = generateAndSendEmail(recipient);
				if(success)
		        {
		        	message = "Your password has been reset successfully. Please check your e-mail.";
		    		request.setAttribute("message", message);
				}
		        else
				{
		        	message = "Password reset failed. Please verify the Email ID once.";
					request.setAttribute("message", message);
				}
		    	request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
		        
				System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
			} 
			catch (MessagingException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			message = "Password reset failed. Please verify the Email ID once.";
			request.setAttribute("message", message);
	    	request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
		}
	}
		
	public boolean generateAndSendEmail(String recipient) throws AddressException, MessagingException 
	{
		// Step1
		//System.out.println("\n 1st ===> setup Mail Server Properties..");
			
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
			
		System.out.println("Mail Server Properties have been setup successfully..");
	 
		// Step2
		//System.out.println("\n\n 2nd ===> get Mail Session..");
			
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			
		generateMailMessage = new MimeMessage(getMailSession);
			
			
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			
		//generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("recipient"));
			
		generateMailMessage.setSubject("Greetings from Shopisight..");
			
		String randomPassword = RandomStringUtils.randomAlphanumeric(10);
		PasswordReset pr = new  PasswordReset();
			
		User user = new User();
		user.setEmailId(recipient);
		user.setPassword(randomPassword);
		String cxt = this.getServletContext().getRealPath("/");
		boolean success = pr.resetPassword(user, cxt);
			
		if(success)
		{
			String emailBody = "Dear User, " +"<br><br> We have received your password change request. "
					+ "This email contains the information that you need to change your password. "
					+ "<br><br>Your new password is "+randomPassword +".<br><br> Best Regards, <br>Shopisight Admin.";
			generateMailMessage.setContent(emailBody, "text/html");
			Transport transport = getMailSession.getTransport("smtp");
			transport.connect(host, email, pass);
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
		}
		return success;
	}
}