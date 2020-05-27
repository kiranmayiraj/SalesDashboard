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
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;
import com.dms.NumberFormatting;
import com.dms.bean.Campaign;
import com.dms.db.CustomerDetails;

@WebServlet(asyncSupported = true, urlPatterns = { "/ExportCampaign2Excel" })
public class ExportCampaign2Excel extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public ExportCampaign2Excel() 
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
		String seg = request.getParameter("segment");
		String name = request.getParameter("campaignName");
		String date = request.getParameter("date");
		String test = request.getParameter("test");
		String control = request.getParameter("control");
		String communication = request.getParameter("communication");
		String message = request.getParameter("message");
		
		Campaign campaign = new Campaign();
		campaign.setCommunication(communication);
		campaign.setControl(control);
		campaign.setDate(date);
		campaign.setMessage(message);
		campaign.setTest(test);
		campaign.setName(name);
		
		String cxt = this.getServletContext().getRealPath("/");
		CustomerDetails cd = new CustomerDetails();
		TableResult tr =  cd.fetchCustomerDetails(cxt, seg);
		String cfPath = createExcel(cxt, tr, campaign, localName);
		File cf = new File(cfPath);
		FileInputStream inStream = new FileInputStream(cf);
		String mimeType = this.getServletContext().getMimeType(cfPath);
		response.setContentType(mimeType);
        response.setContentLength((int) cf.length());
        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=CampaignPlan.xls", cf.getName());
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
	public String createExcel(String cxt, TableResult tr, Campaign campaign, String localName)
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
		String fileLocation = cxt + "campaign.xls";
		System.out.println("File Location: "+fileLocation);
        WritableWorkbook book = null;
        File file = null;
        try 
        {
        	file =  new File(fileLocation);
        	if(file.exists())
        	{
        		file.delete();
        		System.out.println("File is deleted.");
        	}
        	WritableCellFormat cFormat1 = new WritableCellFormat();
        	WritableCellFormat cFormat2 = new WritableCellFormat();
        	WritableFont font = new WritableFont(WritableFont.ARIAL, 16, WritableFont.NO_BOLD);
            WritableFont font2 = new WritableFont(WritableFont.TIMES, 14, WritableFont.NO_BOLD);
            cFormat1.setFont(font);
            cFormat2.setFont(font2);
            file = new File(fileLocation);
            file.setReadable(true);
            file.setWritable(true);
            book = Workbook.createWorkbook(file);
            WritableSheet sheet1 = book.createSheet("Sheet 1", 0);
            Label lab1 = new Label(0, 0, "Name", cFormat1);
            Label lab2 = new Label(0, 1, "Date", cFormat1);
            Label lab3 = new Label(0, 2, "Test", cFormat1);
            Label lab4 = new Label(0, 3, "Control", cFormat1);
            Label lab5 = new Label(0, 4, "Communication", cFormat1);
            Label lab6 = new Label(0, 5, "Message", cFormat1);
            Label lab7 = new Label(1, 0, campaign.getName(), cFormat2);
            Label lab8 = new Label(1, 1, campaign.getDate(), cFormat2);
            Label lab9 = new Label(1, 2, campaign.getTest(), cFormat2);
            Label lab10 = new Label(1, 3, campaign.getControl(), cFormat2);
            Label lab11= new Label(1, 4, campaign.getCommunication(), cFormat2);
            Label lab12 = new Label(1, 5, campaign.getMessage(), cFormat2);
            System.out.println("Date: "+campaign.getDate());
            sheet1.addCell(lab1);
            sheet1.addCell(lab2);
            sheet1.addCell(lab3);
            sheet1.addCell(lab4);
            sheet1.addCell(lab5);
            sheet1.addCell(lab6);
            sheet1.addCell(lab7);
            sheet1.addCell(lab8);
            sheet1.addCell(lab9);
            sheet1.addCell(lab10);
            sheet1.addCell(lab11);
            sheet1.addCell(lab12);
            
            WritableSheet sheet2 = book.createSheet("Sheet 2", 1);
           
            Label label1 = new Label(0, 0, "Name", cFormat1);
            Label label2 = new Label(1, 0, "Total Amount", cFormat1);
            Label label3 = new Label(2, 0, "UPT", cFormat1);
            Label label4 = new Label(3, 0, "Recency", cFormat1);
            Label label5 = new Label(4, 0, "Frequency", cFormat1);
            Label label6 = new Label(5, 0, "Monetary", cFormat1);
            Label label7 = new Label(6, 0, "CLV", cFormat1);
            Label label8 = new Label(7, 0, "Churn Segment", cFormat1);
            sheet2.addCell(label1);
            sheet2.addCell(label2);
            sheet2.addCell(label3);
            sheet2.addCell(label4);
            sheet2.addCell(label5);
            sheet2.addCell(label6);
            sheet2.addCell(label7);
            sheet2.addCell(label8);
            int rowC = 1;
            NumberFormatting nf = new NumberFormatting();
            for (FieldValueList row : tr.iterateAll()) 
    		{
    			String name = "";
    			if(row.get("Billing_Name").isNull())
    			{
    				name = "Null";
    			}
    			else
    			{
    				name = row.get("Billing_Name").getStringValue();
    			}
    			//Integer daysSince = (int) row.get("days_since").getLongValue();
    			Float totalAmount = (float) row.get("total_amount").getDoubleValue();
    			Float upt = (float) row.get("upt_customer").getDoubleValue();
    			Integer rfmRec = (int) row.get("rfm_recency").getLongValue();
    			Integer rfmFre = (int) row.get("rfm_frequency").getLongValue();
    			Integer rfmMon = (int) row.get("rfm_monetary").getLongValue();
    			Float clv = (float) row.get("CLV").getDoubleValue();
    			String churnSegment = row.get("Churn_segment").getStringValue();
    				Label l1 = new Label(0, rowC, name, cFormat2);
    	            Label l2 = new Label(1, rowC, ""+nf.formatNumber(totalAmount), cFormat2);
    	            Label l3 = new Label(2, rowC, ""+upt, cFormat2);
    	            Label l4 = new Label(3, rowC, ""+rfmRec, cFormat2);
    	            Label l5 = new Label(4, rowC, ""+rfmFre, cFormat2);
    	            Label l6 = new Label(5, rowC, ""+rfmMon, cFormat2);
    	            Label l7 = new Label(6, rowC, ""+nf.formatNumber(clv), cFormat2);
    	            Label l8 = new Label(7, rowC, churnSegment, cFormat2);
    	            sheet2.addCell(l1);
    	            sheet2.addCell(l2);
    	            sheet2.addCell(l3);
    	            sheet2.addCell(l4);
    	            sheet2.addCell(l5);
    	            sheet2.addCell(l6);
    	            sheet2.addCell(l7);
    	            sheet2.addCell(l8);	
    	        rowC++; 
    		}
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