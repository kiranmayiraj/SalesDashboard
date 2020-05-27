package com.dms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.NumberFormatting;
import com.dms.db.Segmentation;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/SegmentationServlet" })
public class SegmentationServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public SegmentationServlet() 
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
		Segmentation s = new Segmentation();
		TableResult tr =  s.fetchSegmentationKpi(cxt, cxt1);
		
		String xmlResponse = "<Segmentation>";
		NumberFormatting nf = new NumberFormatting();
		
		for (FieldValueList row : tr.iterateAll()) 
		{
			String segment = row.get("Segment").getStringValue();
			Float sales = (float) row.get("Value").getDoubleValue();
			Integer customers = (int) row.get("Customers").getLongValue();
			Integer tenure = (int) row.get("Tenure").getDoubleValue();	
			Float arpc = (float) row.get("ARPC").getDoubleValue();
			Float aov = (float) row.get("AOV").getDoubleValue();
			Integer upt = (int) row.get("UPT").getDoubleValue();
			String clv = row.get("CLV").getStringValue();
			Integer orderTimes = (int) row.get("Avg_Orders").getDoubleValue();
			Float valueTimes = (float) row.get("X_Value").getDoubleValue();
			
			if(clv.equals("ONGOING"))
			{
				clv = null;
			}
			
			xmlResponse = xmlResponse + "<SegmentationDetails>";
			xmlResponse = xmlResponse + "<Segment>" + segment + "</Segment>";
			xmlResponse = xmlResponse + "<Sales>" + nf.formatNumber(sales) + "</Sales>";
			xmlResponse = xmlResponse + "<Customers>" + customers + "</Customers>";
			xmlResponse = xmlResponse + "<Tenure>" + tenure + "</Tenure>";
			xmlResponse = xmlResponse + "<ARPC>" + arpc + "</ARPC>";
			xmlResponse = xmlResponse + "<AOV>" + aov + "</AOV>";
			xmlResponse = xmlResponse + "<UPT>" + upt + "</UPT>";
			xmlResponse = xmlResponse + "<CLV>" + clv + "</CLV>";
			xmlResponse = xmlResponse + "<OrderTimes>" + orderTimes + "</OrderTimes>";
			xmlResponse = xmlResponse + "<ValueTimes>" + valueTimes + "</ValueTimes>";
			xmlResponse = xmlResponse + "</SegmentationDetails>";
		}
		xmlResponse = xmlResponse + "</Segmentation>";
		
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}
}