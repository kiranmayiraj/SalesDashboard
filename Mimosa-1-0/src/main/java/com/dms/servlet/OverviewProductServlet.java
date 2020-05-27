package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.NumberFormatting;
import com.dms.db.OverviewProduct;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;


@WebServlet(asyncSupported = true, urlPatterns = { "/OverviewProductServlet" })
public class OverviewProductServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public OverviewProductServlet() 
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
		OverviewProduct o = new OverviewProduct();
		TableResult tr =  o.fetchOverviewProduct(period, cxt, cxt1);
		String xmlResponse = "<Overview>";
		NumberFormatting nf = new NumberFormatting();
		for (FieldValueList row : tr.iterateAll()) 
		{
			String Category_Performance = row.get("Category_Performance").getStringValue();
			Float Sales = (float) row.get("Sales").getDoubleValue();
			Integer Orders = (int) row.get("Orders").getLongValue();
			Integer Customers = (int) row.get("Customer").getLongValue();
			Integer NewCustomer = (int) row.get("New_Customer").getLongValue();
			Integer RepeatCustomer = (int) row.get("Repeat_Customer").getLongValue();
			Integer ReturnOrder = (int) row.get("Return_Order").getLongValue();
			
			xmlResponse = xmlResponse + "<Product>";
			xmlResponse = xmlResponse + "<Period>" + period + "</Period>";
			xmlResponse = xmlResponse + "<CategoryPerformance>" + Category_Performance + "</CategoryPerformance>";
			xmlResponse = xmlResponse + "<Sales>" + nf.formatNumber(Sales) + "</Sales>";
			xmlResponse = xmlResponse + "<Orders>" + nf.formatNumber(Orders) + "</Orders>";
			xmlResponse = xmlResponse + "<Customers>" + nf.formatNumber(Customers) + "</Customers>";
			xmlResponse = xmlResponse + "<NewCustomer>" + nf.formatNumber(NewCustomer) + "</NewCustomer>";
			xmlResponse = xmlResponse + "<RepeatCustomer>" + nf.formatNumber(RepeatCustomer) + "</RepeatCustomer>";
			xmlResponse = xmlResponse + "<ReturnOrder>" + nf.formatNumber(ReturnOrder) + "</ReturnOrder>";
			xmlResponse = xmlResponse + "</Product>";
		}
		xmlResponse = xmlResponse + "</Overview>";
		
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}

}
