package com.dms.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.ChurnSegmentation;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/ChurnSegmentationServlet" })
public class ChurnSegmentationServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
     
    public ChurnSegmentationServlet() 
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
		ChurnSegmentation cu = new ChurnSegmentation();
		TableResult tr = cu.fetchChurnSegmentation(cxt, cxt1);
		
		String xmlResponse ="<FetchChurnRate>";
		Set set = new HashSet();
		String temp = "";
		for (FieldValueList row : tr.iterateAll()) 
		{
			String tenureBucket = row.get("Tenure_Bucket").getStringValue();
			Object o = tenureBucket;
			if(set.contains(o))
			{
				String segments = row.get("Segments").getStringValue();
				Float churnRate = (float) row.get("Churn_Rate").getDoubleValue();
				Float perCustomer = (float) row.get("Per_Total_Customer").getDoubleValue();
				Float perChurned = (float) row.get("Per_Total_Churned").getDoubleValue();
				xmlResponse = xmlResponse + "<Data>";
				xmlResponse = xmlResponse + "<Segments>" + segments + "</Segments>";
				xmlResponse = xmlResponse + "<ChurnRate>" + String.format("%.1f", churnRate) + "</ChurnRate>";
				xmlResponse = xmlResponse + "<PerCustomer>" + String.format("%.1f", perCustomer) + "</PerCustomer>";
				xmlResponse = xmlResponse + "<PerChurned>" + String.format("%.1f", perChurned) + "</PerChurned>";
				xmlResponse = xmlResponse + "</Data>";
			}
			else
			{
				if(set.size()>0)
				{
					xmlResponse = xmlResponse + "</TenureBucket>";
				}
				set.add(o);
				
				xmlResponse = xmlResponse + "<TenureBucket id='" + tenureBucket + "'>";
				String segments = row.get("Segments").getStringValue();
				Float churnRate = (float) row.get("Churn_Rate").getDoubleValue();
				Float perCustomer = (float) row.get("Per_Total_Customer").getDoubleValue();
				Float perChurned = (float) row.get("Per_Total_Churned").getDoubleValue();
				xmlResponse = xmlResponse + "<Data>";
				xmlResponse = xmlResponse + "<Segments>" + segments + "</Segments>";
				xmlResponse = xmlResponse + "<ChurnRate>" + String.format("%.1f", churnRate) + "</ChurnRate>";
				xmlResponse = xmlResponse + "<PerCustomer>" + String.format("%.1f", perCustomer) + "</PerCustomer>";
				xmlResponse = xmlResponse + "<PerChurned>" + String.format("%.1f", perChurned) + "</PerChurned>";
				xmlResponse = xmlResponse + "</Data>";
				
			}
			temp = tenureBucket;
		}
		xmlResponse = xmlResponse + "</TenureBucket></FetchChurnRate>";	
		
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}

}
