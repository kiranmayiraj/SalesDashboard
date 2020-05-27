package com.dms.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dms.db.State;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

@WebServlet(asyncSupported = true, urlPatterns = { "/StateServlet" })
public class StateServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public StateServlet() 
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
		String cxt = this.getServletContext().getRealPath("/");
		
		State state = new State();
		TableResult tr =  state.fetchStates(country, cxt);
		String xmlResponse = "<States>";
		xmlResponse = xmlResponse + "<State>OVERALL</State>";
		for (FieldValueList row : tr.iterateAll()) 
		{
			String stateName = "";
			if(row.get("State_Filter").isNull())
			{
				stateName = "OTHERS";
			}
			else
			{
				stateName = ""+row.get("State_Filter").getStringValue();
			}
			if(!stateName.equalsIgnoreCase("Overall"))
			xmlResponse = xmlResponse + "<State>" + stateName + "</State>";
		}
		xmlResponse = xmlResponse + "</States>";
		
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(xmlResponse);
	}
}