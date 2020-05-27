package com.dms.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

@WebServlet(asyncSupported = true, urlPatterns = { "/ExportSummary2Excel" })
public class ExportSummary2Excel extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
     
    public ExportSummary2Excel() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String localName = request.getLocalName();
		String summ = "You made INR 374.6K on 17 orders after INR8.4K in discounts from 9 unique customers in the last month, "
				+ "with key metrics of INR41.6K ARPC, INR22.0K AOV, and INR1.8 UPT.";
		String attr = "You had 27 churned users this period (customers or signups who have lapsed > 12 months without orders). "
				+ "Your acquisition to churn rate (ACR) is 0.33, which means you have a 'leaky bucket'.";
		String newCust = "You had 6 newly activated customers this period. "
				+ "They spent INR249.70K after INR5.6K in discounts for an AOV of INR14.67K.";
		String rptCust = "You had 3 repeat customers this period. "
				+ "They spent INR124.87K after INR2.8K in discounts for an AOV of INR7.3K.";
		
		String cxt = this.getServletContext().getRealPath("/");
		

		String cfPath = createExcel(cxt, localName, summ, attr, newCust, rptCust);
		File cf = new File(cfPath);
		FileInputStream inStream = new FileInputStream(cf);
		String mimeType = this.getServletContext().getMimeType(cfPath);
		response.setContentType(mimeType);
        response.setContentLength((int) cf.length());
        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=ExecutiveSummary.xls", cf.getName());
        response.setHeader(headerKey, headerValue);
        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inStream.read(buffer)) != -1) 
        {
            outStream.write(buffer, 0, bytesRead);
        }
        inStream.close();
        outStream.close();   
	}
	public void writeToLog(String text)
	{
		 final Logger log = Logger.getLogger(this.getClass().getName());
		 log.info(text);
	}
	public String createExcel(String cxt, String localName, String summ, String attr, String newCust, String rptCust)
	{
		if(localName.contains("127.0.0.1"))
		{	
			if(!(cxt.endsWith("/") || cxt.endsWith("\\")))
			{
				cxt = cxt + "/";
			}
		}	
		else
		{
			cxt = "/tmp/";
		}
		String fileLocation = cxt + "ExecutiveSummary.xls";
        WritableWorkbook book = null;
        File file = null;
        try 
        {
        	file =  new File(fileLocation);
        	if(file.exists())
        	{
        		file.delete();
        	}
        	WritableCellFormat cFormat1 = new WritableCellFormat();
        	WritableCellFormat cFormat2 = new WritableCellFormat();
        	WritableFont font = new WritableFont(WritableFont.ARIAL, 16, WritableFont.NO_BOLD);
            WritableFont font2 = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD);
            cFormat1.setFont(font);
            cFormat2.setFont(font2);
            file = new File(fileLocation);
            file.setReadable(true);
            file.setWritable(true);
            book = Workbook.createWorkbook(file);
            WritableSheet sheet1 = book.createSheet("Sheet 1", 0);
            Label lab1 = new Label(0, 0, "Summary", cFormat1);
            Label lab2 = new Label(0, 1, "Attrition", cFormat1);
            Label lab3 = new Label(0, 2, "New Customer", cFormat1);
            Label lab4 = new Label(0, 3, "Repeat Customer", cFormat1);
            Label lab7 = new Label(1, 0, summ, cFormat2);
            Label lab8 = new Label(1, 1, attr, cFormat2);
            Label lab9 = new Label(1, 2, newCust, cFormat2);
            Label lab10 = new Label(1, 3, rptCust, cFormat2);
            sheet1.addCell(lab1);
            sheet1.addCell(lab2);
            sheet1.addCell(lab3);
            sheet1.addCell(lab4);
            sheet1.addCell(lab7);
            sheet1.addCell(lab8);
            sheet1.addCell(lab9);
            sheet1.addCell(lab10);
            book.write();
        } 
        catch (WriteException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            if (book != null) 
            {
                try 
                {
                	book.close();
                } 
                catch (IOException e) 
                {
                	
                    e.printStackTrace();
                } catch (WriteException e) {
                	
                    e.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
	}

}
