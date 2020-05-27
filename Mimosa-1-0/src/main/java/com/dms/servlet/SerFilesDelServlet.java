package com.dms.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.Serialization;

@WebServlet(asyncSupported = true, urlPatterns = { "/SerFilesDelServlet" })
public class SerFilesDelServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public SerFilesDelServlet() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cxt = request.getServletContext().getRealPath("/");
		String localName = request.getLocalName();
		if(localName.contains("127.0.0.1"))
		{	
			if(!(cxt.endsWith("/") || cxt.endsWith("\\")))
			{
				cxt = cxt + "/";
			}
		}	
		else
		{
			cxt = "/tmp/";
		}
		Serialization ser = new Serialization();
		HashMap<String, Boolean> hm = ser.deleteSFiles(cxt);
		Iterator<String> iterator = hm.keySet().iterator();
		while(iterator.hasNext())
		{
			String key = iterator.next();
			System.out.println("File: " + key + " Deleted? " + hm.get(key));
		}
		
		String xmlResponse = "<SerFilesDeletion>";
		xmlResponse = xmlResponse + "<DelStatus>" + "Success" + "</DelStatus>";
		xmlResponse = xmlResponse + "</SerFilesDeletion>";
		response.getWriter().append(xmlResponse);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
