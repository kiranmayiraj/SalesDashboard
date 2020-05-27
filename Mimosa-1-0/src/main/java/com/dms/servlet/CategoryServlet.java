package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.Category;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/CategoryServlet" })
public class CategoryServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    
    public CategoryServlet() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String department = request.getParameter("department");
		String cxt = this.getServletContext().getRealPath("/");
		
		Category cat = new Category();
		TableResult tr =  cat.fetchCategory(cxt, department);
		String xmlResponse = "<Categories>";
		xmlResponse = xmlResponse + "<Category>OVERALL</Category>";
		for (FieldValueList row : tr.iterateAll()) 
		{
			String catName = row.get("Category_Filter").getStringValue();
			if(!catName.equalsIgnoreCase("Overall"))
			xmlResponse = xmlResponse + "<Category>" + catName + "</Category>";
		}
		xmlResponse = xmlResponse + "</Categories>";
		
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}

}
