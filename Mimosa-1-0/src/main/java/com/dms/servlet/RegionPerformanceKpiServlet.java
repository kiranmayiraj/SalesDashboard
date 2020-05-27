package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.NumberFormatting;
import com.dms.db.RegionPerformanceKpi;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/RegionPerformanceKpiServlet" })
public class RegionPerformanceKpiServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public RegionPerformanceKpiServlet() 
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
		
		RegionPerformanceKpi rpkpi = new RegionPerformanceKpi();
		TableResult tr =  rpkpi.fetchRegionPerformanceKpi(period, country, state, city, department, category, cxt);
		String xmlResponse = "<RegionPerformance>";
		NumberFormatting nf = new NumberFormatting();
		for (FieldValueList row : tr.iterateAll()) 
		{
			Float sales = (float) row.get("Sales").getDoubleValue();
			Float salesindc = (float) row.get("Sales_IN_DC").getDoubleValue();
			Integer orders = (int) row.get("Orders").getLongValue();
			Float ordersindc = (float) row.get("Orders_IN_DC").getDoubleValue();
			Integer totalCustomers = (int) row.get("Total_Customers").getLongValue();
			Float totalCustindc = (float) row.get("Customers_IN_DC").getDoubleValue();
			Float arpc = (float) row.get("ARPC").getDoubleValue();
			Float arpcindc = (float) row.get("ARPC_IN_DC").getDoubleValue();
			Float upt = (float) row.get("UPT").getDoubleValue();
			Float uptindc = (float) row.get("UPT_IN_DC").getDoubleValue();
			Integer rptOrders = (int) row.get("Repeat_Orders").getLongValue();
			Float rptOrdersindc = (float) row.get("Repeat_Orders_IN_DC").getDoubleValue();
			Float rptOrderVal = (float) row.get("Repeat_order_value").getDoubleValue();
			Float rptOrderValindc = (float) row.get("Repeat_order_value_IN_DC").getDoubleValue();
			Integer rtnOrders = (int) row.get("Return_Orders").getLongValue();
			Float rtnOrdersindc = (float) row.get("Return_orders_IN_DC").getDoubleValue();
			
			xmlResponse = xmlResponse + "<Kpi>";
			xmlResponse = xmlResponse + "<Period>" + period + "</Period>";
			xmlResponse = xmlResponse + "<Sales>" + nf.formatNumber(sales) + "</Sales>";
			xmlResponse = xmlResponse + "<SalesInDc>" + String.format("%.1f", salesindc) + "</SalesInDc>";
			xmlResponse = xmlResponse + "<Orders>" + nf.formatNumber(orders) + "</Orders>";
			xmlResponse = xmlResponse + "<OrdersInDc>" + String.format("%.1f", ordersindc) + "</OrdersInDc>";
			xmlResponse = xmlResponse + "<TotalCustomer>" + nf.formatNumber(totalCustomers) + "</TotalCustomer>";
			xmlResponse = xmlResponse + "<TotalCustomerInDc>" + String.format("%.1f", totalCustindc) + "</TotalCustomerInDc>";
			xmlResponse = xmlResponse + "<ARPC>" + nf.formatNumber(arpc) + "</ARPC>";
			xmlResponse = xmlResponse + "<ARPCInDc>" + String.format("%.1f", arpcindc) + "</ARPCInDc>";
			xmlResponse = xmlResponse + "<UPT>" + nf.formatNumber(upt) + "</UPT>";
			xmlResponse = xmlResponse + "<UPTInDc>" + String.format("%.1f", uptindc) + "</UPTInDc>";
			xmlResponse = xmlResponse + "<RepeatOrder>" + nf.formatNumber(rptOrders) + "</RepeatOrder>";
			xmlResponse = xmlResponse + "<RepeatOrderInDc>" + String.format("%.2f", rptOrdersindc) + "</RepeatOrderInDc>";
			xmlResponse = xmlResponse + "<RepeatOrderValue>" + nf.formatNumber(rptOrderVal) + "</RepeatOrderValue>";
			xmlResponse = xmlResponse + "<ROVInDc>" + String.format("%.1f", rptOrderValindc) + "</ROVInDc>";
			xmlResponse = xmlResponse + "<ReturnOrder>" + nf.formatNumber(rtnOrders) + "</ReturnOrder>";
			xmlResponse = xmlResponse + "<ReturnOrderInDc>" + String.format("%.1f", rtnOrdersindc) + "</ReturnOrderInDc>";
			
			xmlResponse = xmlResponse + "</Kpi>";
		}
		xmlResponse = xmlResponse + "</RegionPerformance>";
		System.out.println(xmlResponse);
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}

}
