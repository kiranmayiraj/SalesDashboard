package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.NumberFormatting;
import com.dms.db.OverviewProductPerformance;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/OverviewProductPerformanceServlet" })
public class OverviewProductPerformanceServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public OverviewProductPerformanceServlet() 
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
		OverviewProductPerformance opp = new OverviewProductPerformance();
		TableResult tr = opp.fetchOverviewProductPerformance(period, cxt, cxt1);
		String xmlResponse = "<Overview>";
		NumberFormatting nf = new NumberFormatting();
		for (FieldValueList row : tr.iterateAll()) 
		{
			String performanceSelector = row.get("Top_Bottom").getStringValue();
			String product = row.get("Product").getStringValue();
			Float sales = (float) row.get("Sales").getDoubleValue();
			Float customers = (float) row.get("Customer").getDoubleValue();
			Float orders = (float) row.get("Orders").getDoubleValue();
			
			xmlResponse = xmlResponse + "<ProductPerformance>";
			xmlResponse = xmlResponse + "<Period>" + period + "</Period>";
			xmlResponse = xmlResponse + "<PerformanceSelector>" + performanceSelector + "</PerformanceSelector>";
			xmlResponse = xmlResponse + "<Product>" + product + "</Product>";
			xmlResponse = xmlResponse + "<Sales>" + nf.formatNumber(sales) + "</Sales>";
			xmlResponse = xmlResponse + "<Customers>" + customers + "</Customers>";
			xmlResponse = xmlResponse + "<Orders>" + orders + "</Orders>";
			xmlResponse = xmlResponse + "</ProductPerformance>";
		}
		xmlResponse = xmlResponse + "</Overview>";
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}
}