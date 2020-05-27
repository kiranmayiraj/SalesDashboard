package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.ProductAssociation;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/ProductAssociationNetworkServlet" })
public class ProductAssociationNetworkServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public ProductAssociationNetworkServlet() 
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
		ProductAssociation o = new ProductAssociation();
		TableResult tr =  o.fetchProductAssociation(cxt, cxt1);
		String xmlResponse = "<ProductAssociationNW>";
		for (FieldValueList row : tr.iterateAll()) 
		{
			String product1 = row.get("PRODUCT_1_Name").getStringValue();
			String product2 = row.get("PRODUCT_2_Name").getStringValue();
			
			xmlResponse = xmlResponse + "<NW>";
			xmlResponse = xmlResponse + "<Product1>" + product1 + "</Product1>";
			xmlResponse = xmlResponse + "<Product2>" + product2 + "</Product2>";
			xmlResponse = xmlResponse + "</NW>";
		}
		xmlResponse = xmlResponse + "</ProductAssociationNW>";
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}

}
