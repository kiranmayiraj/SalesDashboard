package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.ProductPerformance;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/ProductPerformanceServlet" })
public class ProductPerformanceServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public ProductPerformanceServlet() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String period = request.getParameter("period1");
		String cxt = this.getServletContext().getRealPath("/");
		String localName = request.getLocalName();
		String cxt1 = this.getServletContext().getRealPath("/");
		if(localName.contains("127.0.0.1"))
		{	
			if(!(cxt.endsWith("/") || cxt.endsWith("\\")))
			{
				cxt1 = cxt + "/";
			}
		}	
		else
		{
			cxt1 = "/tmp/";
		}
		ProductPerformance pp = new ProductPerformance();
		TableResult tr = pp.fetchProductPerformance(period, cxt, cxt1);
		String xmlResponse = "<Overview>";
		for (FieldValueList row : tr.iterateAll()) 
		{
			String performanceSelector = row.get("Top_Bottom").getStringValue();
			String product = row.get("Product").getStringValue();
			Float revenue = (float) row.get("Revenue").getDoubleValue();
			
			xmlResponse = xmlResponse + "<ProductPerformance>";
			xmlResponse = xmlResponse + "<Period>" + period + "</Period>";
			xmlResponse = xmlResponse + "<PerformanceSelector>" + performanceSelector + "</PerformanceSelector>";
			xmlResponse = xmlResponse + "<Product>" + product + "</Product>";
			xmlResponse = xmlResponse + "<Revenue>" + revenue + "</Revenue>";
			xmlResponse = xmlResponse + "</ProductPerformance>";
		}
		xmlResponse = xmlResponse + "</Overview>";
		
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}
}