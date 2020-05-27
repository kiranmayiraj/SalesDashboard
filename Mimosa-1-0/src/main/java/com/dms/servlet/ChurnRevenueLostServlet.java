package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.ChurnRevenueLost;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/ChurnRevenueLostServlet" })
public class ChurnRevenueLostServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public ChurnRevenueLostServlet() 
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
		ChurnRevenueLost crl = new ChurnRevenueLost();
		TableResult tr =  crl.fetchChurnRevenueLost(cxt, cxt1);
		String xmlResponse = "<ChurnRevLost>";
		String temp = "";
		for (FieldValueList row : tr.iterateAll()) 
		{
			String seg = row.get("Segments").getStringValue();
			seg = seg.substring(2);
			seg = seg.replace('_', ' ');
			Float churn = null, revenueLoss = null;
			if(row.get("Churned_Prc").isNull()) 
			{
				churn = 0.0f;
			}
			else
			{
				churn = (float) row.get("Churned_Prc").getDoubleValue();
			}
			if(row.get("Revenue_Lost_Prc").isNull())
			{
				revenueLoss = 0.0f;
			}
			else
			{
				revenueLoss = (float) row.get("Revenue_Lost_Prc").getDoubleValue();
			}
			
			if(temp.equals(""))
			{
				xmlResponse = xmlResponse + "<Segments id='"+seg+"'>";
			}	
			else
			{
				if(!temp.equals(seg))
				{
					xmlResponse = xmlResponse + "</Segments>";
					xmlResponse = xmlResponse + "<Segments id='"+seg+"'>";
				}
			}
			temp = seg;
			xmlResponse = xmlResponse + "<Series>";
			xmlResponse = xmlResponse + "<Churn>" + String.format("%.1f", churn*100) + "</Churn>";
			xmlResponse = xmlResponse + "<RevenueLoss>" + String.format("%.1f", revenueLoss*100) + "</RevenueLoss>";
			xmlResponse = xmlResponse + "</Series>";

		}
		xmlResponse = xmlResponse + "</Segments>";
		xmlResponse = xmlResponse + "</ChurnRevLost>";
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}
}
