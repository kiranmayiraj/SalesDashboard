import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SummaryData")
public class SummaryData extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public SummaryData() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Date dt = new Date();
		int currentMonth = dt.getMonth();
		int currentDay = dt.getDay();
		int currentHour = dt.getHours();
		
		String period = request.getParameter("period");
		Connection con = null;
		ResultSet rs1 = null;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/salesdashboard","root","preethi");
			Statement stmt = con.createStatement();
			
			String queryString1 = "SELECT SUM(orders), SUM(sales), SUM(products), AVG(avg_price), SUM(noofchangeiprice), AVG(minimumprice), AVG(maximumprice), AVG(basketsize), AVG(basketvalue), SUM(noofdiscounts), AVG(avgdiscount), SUM(noofreturns), SUM(lostSales), SUM(O_thirdparty), SUM(O_onlineordering), SUM(O_foodpanda), SUM(O_catering), SUM(O_delivery), SUM(O_togo), SUM(O_eatin), AVG(S_thirdparty), AVG(S_onlineordering), AVG(S_foodpanda), AVG(S_catering), AVG(S_delivery), AVG(S_togo), AVG(S_eatin) FROM hourly where year=2018 and month>7 OR year=2019";
			String queryString2 = "SELECT SUM(orders), SUM(sales), SUM(products), AVG(avg_price), SUM(noofchangeiprice), AVG(minimumprice), AVG(maximumprice), AVG(basketsize), AVG(basketvalue), SUM(noofdiscounts), AVG(avgdiscount), SUM(noofreturns), SUM(lostSales), SUM(O_thirdparty), SUM(O_onlineordering), SUM(O_foodpanda), SUM(O_catering), SUM(O_delivery), SUM(O_togo), SUM(O_eatin), AVG(S_thirdparty), AVG(S_onlineordering), AVG(S_foodpanda), AVG(S_catering), AVG(S_delivery), AVG(S_togo), AVG(S_eatin) FROM hourly where year=2019 and month="+currentMonth;
			String queryString3 = "SELECT SUM(orders), SUM(sales), SUM(products), AVG(avg_price), SUM(noofchangeiprice), AVG(minimumprice), AVG(maximumprice), AVG(basketsize), AVG(basketvalue), SUM(noofdiscounts), AVG(avgdiscount), SUM(noofreturns), SUM(lostSales), SUM(O_thirdparty), SUM(O_onlineordering), SUM(O_foodpanda), SUM(O_catering), SUM(O_delivery), SUM(O_togo), SUM(O_eatin), AVG(S_thirdparty), AVG(S_onlineordering), AVG(S_foodpanda), AVG(S_catering), AVG(S_delivery), AVG(S_togo), AVG(S_eatin) FROM hourly where year=2019 and month="+currentMonth+" and day="+currentDay;
			String queryString4 = "SELECT SUM(orders), SUM(sales), SUM(products), AVG(avg_price), SUM(noofchangeiprice), AVG(minimumprice), AVG(maximumprice), AVG(basketsize), AVG(basketvalue), SUM(noofdiscounts), AVG(avgdiscount), SUM(noofreturns), SUM(lostSales), SUM(O_thirdparty), SUM(O_onlineordering), SUM(O_foodpanda), SUM(O_catering), SUM(O_delivery), SUM(O_togo), SUM(O_eatin), AVG(S_thirdparty), AVG(S_onlineordering), AVG(S_foodpanda), AVG(S_catering), AVG(S_delivery), AVG(S_togo), AVG(S_eatin) FROM hourly where year=2019 and month="+currentMonth+" and day="+currentDay+" and hour="+currentHour;
			
			if(period.equals("Yearly"))
			{
				rs1 = stmt.executeQuery(queryString1);
			}
			else if(period.equals("Monthly"))
			{
				rs1 = stmt.executeQuery(queryString2);
			}
			else if(period.equals("Daily"))
			{
				rs1 = stmt.executeQuery(queryString3);
			}
			else if(period.equals("Hourly"))
			{
				rs1 = stmt.executeQuery(queryString4);
			}
			
			int currentOrders = 0;
			int currentSales = 0;
			int currentProducts = 0;
			float currentAvgProductPrice = 0;
			int currentNoOfChangeInPrice = 0;
			int currentMinPrice = 0;
			float currentMaxPrice = 0;
			int currentBasketSize = 0;
			float currentBasketValue = 0;
			int currentNoOfDisc = 0;
			float currentAvgDisc = 0;
			int currentNoOfReturns = 0;
			float currentSalesLost = 0;
			int currentOThirdParty = 0;
			int currentOOnlineOrdering = 0;
			int currentOFoodPanda = 0;
			int currentOCatering = 0;
			int currentODelivery = 0;
			int currentOToGo = 0;
			int currentOEatIn = 0;
			float currentSThirdParty = 0;
			float currentSOnlineOrdering = 0;
			float currentSFoodPanda = 0;
			float currentSCatering = 0;
			float currentSDelivery = 0;
			float currentSToGo = 0;
			float currentSEatIn = 0;
			
			
			String xmlResponse = "";
			
			while(rs1.next())
			{
				currentOrders = rs1.getInt(1);
				currentSales = rs1.getInt(2);
				currentProducts = rs1.getInt(3);
				currentAvgProductPrice = rs1.getFloat(4);
				currentNoOfChangeInPrice = rs1.getInt(5);
				currentMinPrice = rs1.getInt(6);
				currentMaxPrice = rs1.getFloat(7);
				currentBasketSize = rs1.getInt(8);
				currentBasketValue = rs1.getFloat(9);
				currentNoOfDisc = rs1.getInt(10);
				currentAvgDisc = rs1.getFloat(11);
				currentNoOfReturns = rs1.getInt(12);
				currentSalesLost = rs1.getFloat(13);
				currentOThirdParty = rs1.getInt(14);
				currentOOnlineOrdering = rs1.getInt(15);
				currentOFoodPanda = rs1.getInt(16);
				currentOCatering = rs1.getInt(17);
				currentODelivery = rs1.getInt(18);
				currentOToGo = rs1.getInt(19);
				currentOEatIn = rs1.getInt(20);
				currentSThirdParty = rs1.getFloat(21);
				currentSOnlineOrdering = rs1.getFloat(22);
				currentSFoodPanda = rs1.getFloat(23);
				currentSCatering = rs1.getFloat(24);
				currentSDelivery = rs1.getFloat(25);
				currentSToGo = rs1.getFloat(26);
				currentSEatIn = rs1.getFloat(27);
				
				xmlResponse = "<Summary>"; 
				xmlResponse = xmlResponse + "<Orders>" + currentOrders + "</Orders>";
				xmlResponse = xmlResponse + "<Sales>" + currentSales + "</Sales>";
				xmlResponse = xmlResponse + "<Products>" + currentProducts + "</Products>";
				xmlResponse = xmlResponse + "<AvgProductPrice>" + currentAvgProductPrice + "</AvgProductPrice>";
				xmlResponse = xmlResponse + "<NoOfChangeInPrice>" + currentNoOfChangeInPrice + "</NoOfChangeInPrice>";
				xmlResponse = xmlResponse + "<MinPrice>" + currentMinPrice + "</MinPrice>";
				xmlResponse = xmlResponse + "<MaxPrice>" + currentMaxPrice + "</MaxPrice>";
				xmlResponse = xmlResponse + "<BasketSize>" + currentBasketSize + "</BasketSize>";
				xmlResponse = xmlResponse + "<BasketValue>" + currentBasketValue + "</BasketValue>";
				xmlResponse = xmlResponse + "<NoOfDisc>" + currentNoOfDisc + "</NoOfDisc>";
				xmlResponse = xmlResponse + "<AvgDisc>" + currentAvgDisc + "</AvgDisc>";
				xmlResponse = xmlResponse + "<NoOfReturns>" + currentNoOfReturns + "</NoOfReturns>";
				xmlResponse = xmlResponse + "<SalesLost>" + currentSalesLost + "</SalesLost>";
				xmlResponse = xmlResponse + "<OThirdParty>" + currentOThirdParty + "</OThirdParty>";
				xmlResponse = xmlResponse + "<OOnlineOrdering>" + currentOOnlineOrdering + "</OOnlineOrdering>";
				xmlResponse = xmlResponse + "<OFoodPanda>" + currentOFoodPanda + "</OFoodPanda>";
				xmlResponse = xmlResponse + "<OCatering>" + currentOCatering + "</OCatering>";
				xmlResponse = xmlResponse + "<ODelivery>" + currentODelivery + "</ODelivery>";
				xmlResponse = xmlResponse + "<OToGo>" + currentOToGo + "</OToGo>";
				xmlResponse = xmlResponse + "<OEatIn>" + currentOEatIn + "</OEatIn>";
				xmlResponse = xmlResponse + "<SThirdParty>" + currentSThirdParty + "</SThirdParty>";
				xmlResponse = xmlResponse + "<SOnlineOrdering>" + currentSOnlineOrdering + "</SOnlineOrdering>";
				xmlResponse = xmlResponse + "<SFoodPanda>" + currentSFoodPanda + "</SFoodPanda>";
				xmlResponse = xmlResponse + "<SCatering>" + currentSCatering + "</SCatering>";
				xmlResponse = xmlResponse + "<SDelivery>" + currentSDelivery + "</SDelivery>";
				xmlResponse = xmlResponse + "<SToGo>" + currentSToGo + "</SToGo>";
				xmlResponse = xmlResponse + "<SEatIn>" + currentSEatIn + "</SEatIn>";
				xmlResponse = xmlResponse + "</Summary>";
			}
			response.getWriter().append(xmlResponse);
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception!");
			e.printStackTrace();
		}
	}
}
