package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.NumberFormatting;
import com.dms.db.CustomerDetails;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/CustomerDetailsServlet" })
public class CustomerDetailsServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public CustomerDetailsServlet() 
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
		String seg = request.getParameter("segment");
		CustomerDetails cd = new CustomerDetails();
		TableResult tr =  cd.fetchCustomerDetails(cxt, seg);
		
		String xmlResponse = "<CustomerDetails>";
		NumberFormatting nf = new NumberFormatting();
		
		for (FieldValueList row : tr.iterateAll()) 
		{
			String name = "";
			if(row.get("Billing_Name").isNull())
			{
				name = "name";
			}
			else
			{
				name = row.get("Billing_Name").getStringValue();
			}
			String email = row.get("Email").getStringValue();
			String address1 = "";
			if(row.get("Address1").isNull())
			{
				address1 = "";
			}
			else
			{
				address1 = ""+row.get("Address1").getStringValue();
			}
			String address2 = "";
			if(row.get("Address2").isNull())
			{
				address2 = "";
			}
			else
			{
				address2 = ""+row.get("Address2").getStringValue();
			}
			String city = "";
			if(row.get("City").isNull())
			{
				city = "";
			}
			else
			{
				city = ""+row.get("City").getStringValue();
			}
			String provinceCode = "";
			if(row.get("Province_Code").isNull())
			{
				provinceCode = "";
			}
			else
			{
				provinceCode = ""+row.get("Province_Code").getStringValue();
			}
			String countryCode = "";
			if(row.get("Country_Code").isNull())
			{
				countryCode = "";
			}
			else
			{
				countryCode = ""+row.get("Country_Code").getStringValue();
			}
			String phone = "";
			if(row.get("Phone").isNull())
			{
				phone = "";
			}
			else
			{
				phone = ""+row.get("Phone").getStringValue();
			}
			Float totalSpent = (float) row.get("Total_Spent").getDoubleValue();
			Integer orders = (int) row.get("Total_Orders").getLongValue();
			Float upt = (float) row.get("upt_customer").getDoubleValue();
			Float clv = (float) row.get("CLV").getDoubleValue();
			String churnSegment = row.get("Churn_segment").getStringValue();
			
			xmlResponse = xmlResponse + "<Details>";
			xmlResponse = xmlResponse + "<Segment>" + seg + "</Segment>";
			xmlResponse = xmlResponse + "<Name>" + name + "</Name>";
			xmlResponse = xmlResponse + "<Email>" + email + "</Email>";
			xmlResponse = xmlResponse + "<Address1>" + address1 + "</Address1>";
			xmlResponse = xmlResponse + "<Address2>" + address2 + "</Address2>";
			xmlResponse = xmlResponse + "<City>" + city + "</City>";
			xmlResponse = xmlResponse + "<ProvinceCode>" + provinceCode + "</ProvinceCode>";
			xmlResponse = xmlResponse + "<CountryCode>" + countryCode + "</CountryCode>";
			xmlResponse = xmlResponse + "<Phone>" + phone + "</Phone>";
			xmlResponse = xmlResponse + "<TotalSpent>" + nf.formatNumber(totalSpent) + "</TotalSpent>";
			xmlResponse = xmlResponse + "<Orders>" + orders + "</Orders>";
			xmlResponse = xmlResponse + "<UPT>" + upt + "</UPT>";
			xmlResponse = xmlResponse + "<CLV>" + nf.formatNumber(clv) + "</CLV>";
			xmlResponse = xmlResponse + "<ChurnSegment>" + churnSegment + "</ChurnSegment>";
			xmlResponse = xmlResponse + "</Details>";
		}
		xmlResponse = xmlResponse + "</CustomerDetails>";
		xmlResponse = xmlResponse.replace("&", "&amp;");
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}
}