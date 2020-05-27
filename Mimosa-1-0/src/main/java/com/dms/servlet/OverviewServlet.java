package com.dms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.NumberFormatting;
import com.dms.db.Overview;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/OverviewServlet" })

public class OverviewServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public OverviewServlet() 
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
			if(!(cxt1.endsWith("/") || cxt1.endsWith("\\")))
			{
				cxt1 = cxt1 + "/";
			}
		}	
		else
		{
			cxt1 = "/tmp/";
		}
		Overview o = new Overview();
		TableResult tr =  o.fetchOverviewKpi(period, cxt, cxt1);
		String xmlResponse = "<Overview>";
		NumberFormatting nf = new NumberFormatting();
		for (FieldValueList row : tr.iterateAll()) 
		{
			Float Sales = (float) row.get("Sales").getDoubleValue();
			Float Sales_InDc = (float) row.get("Sales_InDc").getDoubleValue();
			Integer Orders = (int) row.get("Orders").getLongValue();
			Float Orders_InDc = (float) row.get("Orders_InDc").getDoubleValue();
			Float Average_order_value = (float) row.get("Average_order_value").getDoubleValue();
			Float AOV_InDc = (float) row.get("AOV_InDc").getDoubleValue();
			Integer Item_Ordered = (int) row.get("Item_Ordered").getLongValue();
			Float IO_InDc = (float) row.get("IO_InDc").getDoubleValue();
			Float Shipping = (float) row.get("Shipping").getDoubleValue();
			Float Ship_InDc = (float) row.get("Ship_InDc").getDoubleValue();
			Float Discount = (float) row.get("Discount").getDoubleValue();
			Float Disc_InDc = (float) row.get("Disc_InDc").getDoubleValue();
			
			Integer New_Customer = (int) row.get("New_Customer").getLongValue();
			Float NC_ID = (float) row.get("NC_ID").getDoubleValue();
			Integer Repeat_Customer = (int) row.get("Repeat_Customer").getLongValue();
			Float RC_ID = (float) row.get("RC_ID").getDoubleValue();
			Integer Return_Order = (int) row.get("Return_Order").getLongValue();
			Float RO_ID = (float) row.get("RO_ID").getDoubleValue();
			//Integer Repeat_Order = (int) row.get("Repeat_Order").getLongValue();
			//Float Repeat_Order_ID = (float) row.get("Repeat_Order_ID").getDoubleValue();
			Float Repeat_Order_Value = (float) row.get("Repeat_Order_Value").getDoubleValue();
			Float ROV_ID = (float) row.get("ROV_ID").getDoubleValue();
			Float ARPC = (float) row.get("ARPC").getDoubleValue();
			Float ARPC_ID = (float) row.get("ARPC_ID").getDoubleValue();
			Float UPT = (float) row.get("UPT").getDoubleValue();
			Float UPT_ID = (float) row.get("UPT_ID").getDoubleValue();
			
			xmlResponse = xmlResponse + "<Kpi>";
			xmlResponse = xmlResponse + "<Period>" + period + "</Period>";
			xmlResponse = xmlResponse + "<Sales>" + nf.formatNumber(Sales) + "</Sales>";
			xmlResponse = xmlResponse + "<SalesInDc>" + String.format("%.2f", Sales_InDc) + "</SalesInDc>";
			xmlResponse = xmlResponse + "<Orders>" + nf.formatNumber(Orders) + "</Orders>";
			xmlResponse = xmlResponse + "<OrdersInDc>" + String.format("%.1f", Orders_InDc) + "</OrdersInDc>";
			xmlResponse = xmlResponse + "<AverageOrderValue>" + nf.formatNumber(Average_order_value) + "</AverageOrderValue>";
			xmlResponse = xmlResponse + "<AOVInDc>" + String.format("%.1f", AOV_InDc) + "</AOVInDc>";
			xmlResponse = xmlResponse + "<ItemOrdered>" + nf.formatNumber(Item_Ordered) + "</ItemOrdered>";
			xmlResponse = xmlResponse + "<IOInDc>" + String.format("%.1f", IO_InDc) + "</IOInDc>";
			xmlResponse = xmlResponse + "<Shipping>" + nf.formatNumber(Shipping) + "</Shipping>";
			xmlResponse = xmlResponse + "<ShipInDc>" + String.format("%.1f", Ship_InDc) + "</ShipInDc>";
			xmlResponse = xmlResponse + "<Discount>" + nf.formatNumber(Discount) + "</Discount>";
			xmlResponse = xmlResponse + "<DiscInDc>" + String.format("%.1f", Disc_InDc) + "</DiscInDc>";

			xmlResponse = xmlResponse + "<NewCustomer>" + nf.formatNumber(New_Customer) + "</NewCustomer>";
			xmlResponse = xmlResponse + "<NewCustomerInDc>" + String.format("%.1f", NC_ID) + "</NewCustomerInDc>";
			xmlResponse = xmlResponse + "<RepeatCustomer>" + nf.formatNumber(Repeat_Customer) + "</RepeatCustomer>";
			xmlResponse = xmlResponse + "<RepeatCustomerInDc>" + String.format("%.1f", RC_ID) + "</RepeatCustomerInDc>";
			xmlResponse = xmlResponse + "<ReturnOrder>" + nf.formatNumber(Return_Order) + "</ReturnOrder>";
			xmlResponse = xmlResponse + "<ReturnOrderInDc>" + String.format("%.1f", RO_ID) + "</ReturnOrderInDc>";
			//xmlResponse = xmlResponse + "<RepeatOrder>" + nf.formatNumber(Repeat_Order) + "</RepeatOrder>";
			//xmlResponse = xmlResponse + "<RepeatOrderInDc>" + String.format("%.2f", Repeat_Order_ID) + "</RepeatOrderInDc>";
			xmlResponse = xmlResponse + "<RepeatOrderValue>" + nf.formatNumber(Repeat_Order_Value) + "</RepeatOrderValue>";
			xmlResponse = xmlResponse + "<ROVInDc>" + String.format("%.1f", ROV_ID) + "</ROVInDc>";
			xmlResponse = xmlResponse + "<ARPC>" + nf.formatNumber(ARPC) + "</ARPC>";
			xmlResponse = xmlResponse + "<ARPCInDc>" + String.format("%.1f", ARPC_ID) + "</ARPCInDc>";
			xmlResponse = xmlResponse + "<UPT>" + nf.formatNumber(UPT) + "</UPT>";
			xmlResponse = xmlResponse + "<UPTInDc>" + String.format("%.1f", UPT_ID) + "</UPTInDc>";
			xmlResponse = xmlResponse + "</Kpi>";
		}
		xmlResponse = xmlResponse + "</Overview>";

		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}

}
