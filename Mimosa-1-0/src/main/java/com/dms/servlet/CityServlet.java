package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.City;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/CityServlet" })
public class CityServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public CityServlet() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String state = request.getParameter("state");
		String cxt = this.getServletContext().getRealPath("/");
		
		City city = new City();
		TableResult tr =  city.fetchCities(state, cxt);
		String xmlResponse = "<Cities>";
		xmlResponse = xmlResponse + "<City>OVERALL</City>";
		for (FieldValueList row : tr.iterateAll()) 
		{
			String cityName = row.get("City_Filter").getStringValue();
			if(!cityName.equalsIgnoreCase("Overall"))
			xmlResponse = xmlResponse + "<City>" + cityName + "</City>";
		}
		xmlResponse = xmlResponse + "</Cities>";
		
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}
}