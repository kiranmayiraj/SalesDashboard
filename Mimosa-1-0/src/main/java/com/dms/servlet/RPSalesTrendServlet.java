package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.RPSalesTrend;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/RPSalesTrendServlet" })
public class RPSalesTrendServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
     
    public RPSalesTrendServlet() 
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
		
		System.out.println("State: " + state +" City: " + city + " Department: " + department + " Category: " + category + " Period: " + period);
		
		RPSalesTrend st = new RPSalesTrend();
		TableResult tr =  st.fetchRPSalesTrend(country, state, city, department, category, period, cxt);
		
		String xmlResponse = "<SalesTrend>";
		
		for (FieldValueList row : tr.iterateAll()) 
		{
			xmlResponse = xmlResponse + "<Trend>";
			
			if(period.equals("2018"))
			{
				String sm = row.get("Sale_Month").getStringValue();
				Float value = (float) row.get("Value").getDoubleValue();
				Integer volume = (int) row.get("Volume").getLongValue();
				xmlResponse = xmlResponse + "<Sale>" + sm + "</Sale>";
				xmlResponse = xmlResponse + "<Value>" + value + "</Value>";
				xmlResponse = xmlResponse + "<Volume>" + volume + "</Volume>";
			}
			if(period.equals("2019"))
			{
				String sm = row.get("Sale_Month").getStringValue();
				Float value = (float) row.get("Value").getDoubleValue();
				Integer volume = (int) row.get("Volume").getLongValue();
				xmlResponse = xmlResponse + "<Sale>" + sm + "</Sale>";
				xmlResponse = xmlResponse + "<Value>" + value + "</Value>";
				xmlResponse = xmlResponse + "<Volume>" + volume + "</Volume>";
			}
			if(period.equals("YTD"))
			{
				String sm = row.get("Sale_Month").getStringValue();
				Float value = (float) row.get("Value").getDoubleValue();
				Integer volume = (int) row.get("Volume").getLongValue();
				xmlResponse = xmlResponse + "<Sale>" + sm + "</Sale>";
				xmlResponse = xmlResponse + "<Value>" + value + "</Value>";
				xmlResponse = xmlResponse + "<Volume>" + volume + "</Volume>";
			}
			else if(period.equals("MTD"))
			{
				String sw = row.get("Sale_Week").getStringValue();
				Float value = (float) row.get("Value").getDoubleValue();
				Integer volume = (int) row.get("Volume").getLongValue();
				xmlResponse = xmlResponse + "<Sale>" + sw + "</Sale>";
				xmlResponse = xmlResponse + "<Value>" + value + "</Value>";
				xmlResponse = xmlResponse + "<Volume>" + volume + "</Volume>";
			}
			else if(period.equals("WEEK"))
			{
				String sd = row.get("Sale_Date").getStringValue();
				Float value = (float) row.get("Value").getDoubleValue();
				Integer volume = (int) row.get("Volume").getLongValue();
				xmlResponse = xmlResponse + "<Sale>" + sd + "</Sale>";
				xmlResponse = xmlResponse + "<Value>" + value + "</Value>";
				xmlResponse = xmlResponse + "<Volume>" + volume + "</Volume>";
			}
			else if(period.equals("DAY"))
			{
				String sd = row.get("Sale_Date").getStringValue();
				Float value = (float) row.get("Value").getDoubleValue();
				Integer volume = (int) row.get("Volume").getLongValue();
				xmlResponse = xmlResponse + "<Sale>" + sd + "</Sale>";
				xmlResponse = xmlResponse + "<Value>" + value + "</Value>";
				xmlResponse = xmlResponse + "<Volume>" + volume + "</Volume>";
			}
			xmlResponse = xmlResponse + "</Trend>";
		}
		xmlResponse = xmlResponse + "</SalesTrend>";
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}

}
