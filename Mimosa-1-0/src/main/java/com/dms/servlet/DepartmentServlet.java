package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.Department;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/DepartmentServlet" })
public class DepartmentServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public DepartmentServlet() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String cxt = this.getServletContext().getRealPath("/");
		
		Department dept = new Department();
		TableResult tr =  dept.fetchDepartment(cxt);
		String xmlResponse = "<Departments>";
		xmlResponse = xmlResponse + "<Department>OVERALL</Department>";
		for (FieldValueList row : tr.iterateAll()) 
		{
			String deptName = row.get("Department_Filter").getStringValue();
			if(!deptName.equalsIgnoreCase("Overall"))
			xmlResponse = xmlResponse + "<Department>" + deptName + "</Department>";
		}
		xmlResponse = xmlResponse + "</Departments>";
		
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}

}
