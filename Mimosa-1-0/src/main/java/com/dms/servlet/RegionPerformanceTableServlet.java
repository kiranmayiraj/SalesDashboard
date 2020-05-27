package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.NumberFormatting;
import com.dms.db.RegionPerformanceTable;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/RegionPerformanceTableServlet" })
public class RegionPerformanceTableServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public RegionPerformanceTableServlet() 
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
		
		RegionPerformanceTable rpt = new RegionPerformanceTable();
		TableResult tr =  rpt.fetchRegionPerformanceTable(country, state, city, department, category, period, cxt);
		
		String xmlResponse = "<RegionPerformanceTbl>";
		NumberFormatting nf = new NumberFormatting();
		for (FieldValueList row : tr.iterateAll()) 
		{
			String item = row.get("Item").getStringValue();
			Float sales = (float) row.get("Sales").getDoubleValue();
			Integer orders = (int) row.get("Orders").getLongValue();
			Integer customers = (int) row.get("Total_Customers").getLongValue();
			Integer newCustomer = (int) row.get("New_customers").getLongValue();
			Integer rptCustomer = (int) row.get("Repeat_Customers").getLongValue();
			//System.out.println("Is Null: "+row.get("Return_orders").isNull());
			Integer rtnOrder = 0;
			if(!row.get("Return_Orders").isNull())
			{		
				rtnOrder = (int) row.get("Return_Orders").getLongValue();
			}
			
			xmlResponse = xmlResponse + "<ProductsRow>";
			xmlResponse = xmlResponse + "<Department>" + department + "</Department>";
			xmlResponse = xmlResponse + "<Category>" + category + "</Category>";
			xmlResponse = xmlResponse + "<Item>" + item + "</Item>";
			xmlResponse = xmlResponse + "<Sales>" + nf.formatNumber(sales) + "</Sales>";
			xmlResponse = xmlResponse + "<Orders>" + nf.formatNumber(orders) + "</Orders>";
			xmlResponse = xmlResponse + "<Customers>" + nf.formatNumber(customers) + "</Customers>";
			xmlResponse = xmlResponse + "<NewCustomers>" + nf.formatNumber(newCustomer) + "</NewCustomers>";
			xmlResponse = xmlResponse + "<RepeatCustomers>" + nf.formatNumber(rptCustomer) + "</RepeatCustomers>";
			xmlResponse = xmlResponse + "<ReturnOrders>" + nf.formatNumber(rtnOrder) + "</ReturnOrders>";
			xmlResponse = xmlResponse + "</ProductsRow>";
		}
		xmlResponse = xmlResponse + "</RegionPerformanceTbl>";
		xmlResponse = xmlResponse.replace("&", "&amp;");
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}

}
