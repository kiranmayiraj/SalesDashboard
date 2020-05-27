package com.dms.servlet;

import java.io.IOException;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.CrossUpSell;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;


@WebServlet(asyncSupported = true, urlPatterns = { "/CrossUpSellServlet" })
public class CrossUpSellServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public CrossUpSellServlet() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String level = request.getParameter("level");
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
		CrossUpSell cus = new CrossUpSell();
		TableResult tr = cus.fetchCrossUpSell(level, cxt, cxt1);
		TableResult tr2 = cus.fetchNonPurchasedItems(level, cxt, cxt1);
		TreeMap<String, Boolean> npi = new TreeMap<String, Boolean>();
		for (FieldValueList row : tr.iterateAll()) 
		{
			String notPurchasedItem = row.get("Not_Purchased_item").getStringValue();
			npi.put(notPurchasedItem, false);
		}
		String xmlResponse = "<CrossUpSell>";
		Set set = new HashSet();
		String temp = "";
		
		TreeMap<String, Integer> hm = new TreeMap<String, Integer>();
		
		for (FieldValueList row : tr.iterateAll()) 
		{
			String l = row.get("Level").getStringValue();
			String purchasedItem = row.get("Purchased_item").getStringValue();
			String notPurchasedItem = row.get("Not_Purchased_item").getStringValue();
			Integer customers = (int) row.get("No_of_Cust").getLongValue();
			
			String pnp = purchasedItem + "##" + notPurchasedItem;
			hm.put(pnp, customers);
			set.add(purchasedItem);
			
		}
		Iterator<String> iterator4 = set.iterator();
		while(iterator4.hasNext())
		{
			String piTemp = iterator4.next();
			
			Iterator<String> iterator3 = npi.keySet().iterator();
			while(iterator3.hasNext())
			{
				String npiTemp = iterator3.next().toString();
				String pot = piTemp + "##" + npiTemp;
				if(hm.containsKey(pot))
				{
					
				}
				else
				{
					hm.put(pot,0);
					System.out.println("Adjustment: "+pot);
				}
			}
			
		}
		
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext())
		{
			String curr = iterator.next();
			hm.put(curr+"##"+curr, 0);
		}
		Iterator iterator2 = hm.keySet().iterator();
		String temp2 = "";
		while(iterator2.hasNext())
		{
			String key = iterator2.next().toString();
			String []strArray = key.split("##");
			
			String purchasedItem = strArray[0];
			String notPurchasedItem = strArray[1];
			boolean toggle = false;
			if(temp2.equals(""))
			{
				temp2 = purchasedItem;
				xmlResponse = xmlResponse + "<PurchasedItem id='" + purchasedItem + "'>";
				
			}
			else
			{
				if(!temp2.equals(purchasedItem))
				{
					xmlResponse = xmlResponse + "</PurchasedItem>";
					temp2 = purchasedItem;
					xmlResponse = xmlResponse + "<PurchasedItem id='" + purchasedItem + "'>";
				}
			}	
			int customers = (int) hm.get(key);
			
			xmlResponse = xmlResponse + "<NotPurchasedItem>";
			xmlResponse = xmlResponse + "<Name>" + notPurchasedItem + "</Name>";
			xmlResponse = xmlResponse + "<Customers>" + customers + "</Customers>";
			xmlResponse = xmlResponse + "</NotPurchasedItem>";
		}
		
		
		xmlResponse = xmlResponse + "</PurchasedItem></CrossUpSell>";
		
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}
}
