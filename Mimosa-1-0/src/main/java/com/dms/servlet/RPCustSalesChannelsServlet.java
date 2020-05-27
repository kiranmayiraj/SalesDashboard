package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.RPCustSalesChannels;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/RPCustSalesChannelsServlet" })
public class RPCustSalesChannelsServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public RPCustSalesChannelsServlet() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String country = request.getParameter("country");
		String state = request.getParameter("state");
		String city = request.getParameter("city");
		String department = request.getParameter("department");
		String category = request.getParameter("category");
		String period = request.getParameter("period2");
		String cxt = this.getServletContext().getRealPath("/");
		
		//System.out.println("State: " + state +" City: " + city + " Department: " + department + " Category: " + category + " Period: " + period);
		
		RPCustSalesChannels rpt = new RPCustSalesChannels();
		TableResult tr =  rpt.fetchRPCustSalesChannels(country, state, city, department, category, period, cxt);
		
		String xmlResponse = "<RegionPerformanceChart>";
		for (FieldValueList row : tr.iterateAll()) 
		{
			String source = row.get("Source").getStringValue();
			Integer customers = (int) row.get("Total_Customers").getLongValue();
			
			xmlResponse = xmlResponse + "<Chart>";
			xmlResponse = xmlResponse + "<Source>" + source + "</Source>";
			xmlResponse = xmlResponse + "<Customers>" + customers + "</Customers>";
			xmlResponse = xmlResponse + "</Chart>";
		}
		xmlResponse = xmlResponse + "</RegionPerformanceChart>";
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}

}
