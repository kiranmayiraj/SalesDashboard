package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.Country;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/CountryServlet" })
public class CountryServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public CountryServlet() 
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
		
		Country country = new Country();
		TableResult tr =  country.fetchCountries(cxt);
		String xmlResponse = "<Countries>";
		xmlResponse = xmlResponse + "<Country>OVERALL</Country>";
		for (FieldValueList row : tr.iterateAll()) 
		{
			String countryName = row.get("Country_Filter").getStringValue();
			if(!countryName.equalsIgnoreCase("Overall"))
			xmlResponse = xmlResponse + "<Country>" + countryName + "</Country>";
		}
		xmlResponse = xmlResponse + "</Countries>";
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}

}
