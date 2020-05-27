package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.ChurnEvolution;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;


@WebServlet(asyncSupported = true, urlPatterns = { "/ChurnEvolutionServlet" })
public class ChurnEvolutionServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public ChurnEvolutionServlet() 
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
		ChurnEvolution ce = new ChurnEvolution();
		TableResult tr =  ce.fetchChurnEvolution(cxt, cxt1);
		String xmlResponse = "<ChurnEvolution>";
		String temp = "";
		for (FieldValueList row : tr.iterateAll()) 
		{
			String monthEnd = row.get("Monthend").getStringValue();
			String tenureBucket = row.get("Tenure_Bucket").getStringValue();
			Float churnRate = (float) row.get("Churn_Rate").getDoubleValue();
			
			if(temp.equals(""))
			{
				xmlResponse = xmlResponse + "<TenureBucket id='"+tenureBucket+"'>";
			}	
			else
			{
				if(!temp.equals(tenureBucket))
				{
					xmlResponse = xmlResponse + "</TenureBucket>";
					xmlResponse = xmlResponse + "<TenureBucket id='"+tenureBucket+"'>";
				}
			}
			temp = tenureBucket;
			xmlResponse = xmlResponse + "<Series>";
			xmlResponse = xmlResponse + "<Monthend>" + monthEnd + "</Monthend>";
			xmlResponse = xmlResponse + "<ChurnRate>" + String.format("%.1f", churnRate) + "</ChurnRate>";
			xmlResponse = xmlResponse + "</Series>";

		}
		xmlResponse = xmlResponse + "</TenureBucket>";
		xmlResponse = xmlResponse + "</ChurnEvolution>";
		
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}

}
