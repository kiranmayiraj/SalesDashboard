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

@WebServlet("/SummaryData2")
public class SummaryData2 extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public SummaryData2() 
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
		ResultSet rs2 = null;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/salesdashboard","root","preethi");
			Statement stmt = con.createStatement();
			
			String queryString1 = "SELECT SUM(orders), SUM(sales), SUM(products), AVG(avg_price), SUM(noofchangeiprice), AVG(minimumprice), AVG(maximumprice), AVG(basketsize), AVG(basketvalue), SUM(noofdiscounts), AVG(avgdiscount), SUM(noofreturns), SUM(lostSales), SUM(O_thirdparty), SUM(O_onlineordering), SUM(O_foodpanda), SUM(O_catering), SUM(O_delivery), SUM(O_togo), SUM(O_eatin), AVG(S_thirdparty), AVG(S_onlineordering), AVG(S_foodpanda), AVG(S_catering), AVG(S_delivery), AVG(S_togo), AVG(S_eatin) FROM hourly where year=2018 and month>7 OR year=2019";
			String queryString2 = "SELECT SUM(orders), SUM(sales), SUM(products), AVG(avg_price), SUM(noofchangeiprice), AVG(minimumprice), AVG(maximumprice), AVG(basketsize), AVG(basketvalue), SUM(noofdiscounts), AVG(avgdiscount), SUM(noofreturns), SUM(lostSales), SUM(O_thirdparty), SUM(O_onlineordering), SUM(O_foodpanda), SUM(O_catering), SUM(O_delivery), SUM(O_togo), SUM(O_eatin), AVG(S_thirdparty), AVG(S_onlineordering), AVG(S_foodpanda), AVG(S_catering), AVG(S_delivery), AVG(S_togo), AVG(S_eatin) FROM hourly where year=2019 and month="+currentMonth;
			String queryString3 = "SELECT SUM(orders), SUM(sales), SUM(products), AVG(avg_price), SUM(noofchangeiprice), AVG(minimumprice), AVG(maximumprice), AVG(basketsize), AVG(basketvalue), SUM(noofdiscounts), AVG(avgdiscount), SUM(noofreturns), SUM(lostSales), SUM(O_thirdparty), SUM(O_onlineordering), SUM(O_foodpanda), SUM(O_catering), SUM(O_delivery), SUM(O_togo), SUM(O_eatin), AVG(S_thirdparty), AVG(S_onlineordering), AVG(S_foodpanda), AVG(S_catering), AVG(S_delivery), AVG(S_togo), AVG(S_eatin) FROM hourly where year=2019 and month="+currentMonth+" and day="+currentDay;
			String queryString4 = "SELECT SUM(orders), SUM(sales), SUM(products), AVG(avg_price), SUM(noofchangeiprice), AVG(minimumprice), AVG(maximumprice), AVG(basketsize), AVG(basketvalue), SUM(noofdiscounts), AVG(avgdiscount), SUM(noofreturns), SUM(lostSales), SUM(O_thirdparty), SUM(O_onlineordering), SUM(O_foodpanda), SUM(O_catering), SUM(O_delivery), SUM(O_togo), SUM(O_eatin), AVG(S_thirdparty), AVG(S_onlineordering), AVG(S_foodpanda), AVG(S_catering), AVG(S_delivery), AVG(S_togo), AVG(S_eatin) FROM hourly where year=2019 and month="+currentMonth+" and day="+currentDay+" and hour="+currentHour;
			
			String queryString5 = "SELECT SUM(orders), SUM(sales), SUM(products), AVG(avg_price), SUM(noofchangeiprice), AVG(minimumprice), AVG(maximumprice), AVG(basketsize), AVG(basketvalue), SUM(noofdiscounts), AVG(avgdiscount), SUM(noofreturns), SUM(lostSales), SUM(O_thirdparty), SUM(O_onlineordering), SUM(O_foodpanda), SUM(O_catering), SUM(O_delivery), SUM(O_togo), SUM(O_eatin), AVG(S_thirdparty), AVG(S_onlineordering), AVG(S_foodpanda), AVG(S_catering), AVG(S_delivery), AVG(S_togo), AVG(S_eatin) FROM hourly where year=2018 and month<7";
			String queryString6 = "SELECT SUM(orders), SUM(sales), SUM(products), AVG(avg_price), SUM(noofchangeiprice), AVG(minimumprice), AVG(maximumprice), AVG(basketsize), AVG(basketvalue), SUM(noofdiscounts), AVG(avgdiscount), SUM(noofreturns), SUM(lostSales), SUM(O_thirdparty), SUM(O_onlineordering), SUM(O_foodpanda), SUM(O_catering), SUM(O_delivery), SUM(O_togo), SUM(O_eatin), AVG(S_thirdparty), AVG(S_onlineordering), AVG(S_foodpanda), AVG(S_catering), AVG(S_delivery), AVG(S_togo), AVG(S_eatin) FROM hourly where year=2018 and month="+currentMonth;
			String queryString7 = "SELECT SUM(orders), SUM(sales), SUM(products), AVG(avg_price), SUM(noofchangeiprice), AVG(minimumprice), AVG(maximumprice), AVG(basketsize), AVG(basketvalue), SUM(noofdiscounts), AVG(avgdiscount), SUM(noofreturns), SUM(lostSales), SUM(O_thirdparty), SUM(O_onlineordering), SUM(O_foodpanda), SUM(O_catering), SUM(O_delivery), SUM(O_togo), SUM(O_eatin), AVG(S_thirdparty), AVG(S_onlineordering), AVG(S_foodpanda), AVG(S_catering), AVG(S_delivery), AVG(S_togo), AVG(S_eatin) FROM hourly where year=2018 and month="+currentMonth+" and day="+currentDay;
			String queryString8 = "SELECT SUM(orders), SUM(sales), SUM(products), AVG(avg_price), SUM(noofchangeiprice), AVG(minimumprice), AVG(maximumprice), AVG(basketsize), AVG(basketvalue), SUM(noofdiscounts), AVG(avgdiscount), SUM(noofreturns), SUM(lostSales), SUM(O_thirdparty), SUM(O_onlineordering), SUM(O_foodpanda), SUM(O_catering), SUM(O_delivery), SUM(O_togo), SUM(O_eatin), AVG(S_thirdparty), AVG(S_onlineordering), AVG(S_foodpanda), AVG(S_catering), AVG(S_delivery), AVG(S_togo), AVG(S_eatin) FROM hourly where year=2018 and month="+currentMonth+" and day="+currentDay+" and hour="+currentHour;
			
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
			
			int previousOrders = 0;
			int previousSales = 0;
			int previousProducts = 0;
			float previousAvgProductPrice = 0;
			int previousNoOfChangeInPrice = 0;
			int previousMinPrice = 0;
			float previousMaxPrice = 0;
			int previousBasketSize = 0;
			float previousBasketValue = 0;
			int previousNoOfDisc = 0;
			float previousAvgDisc = 0;
			int previousNoOfReturns = 0;
			float previousSalesLost = 0;
			int previousOThirdParty = 0;
			int previousOOnlineOrdering = 0;
			int previousOFoodPanda = 0;
			int previousOCatering = 0;
			int previousODelivery = 0;
			int previousOToGo = 0;
			int previousOEatIn = 0;
			float previousSThirdParty = 0;
			float previousSOnlineOrdering = 0;
			float previousSFoodPanda = 0;
			float previousSCatering = 0;
			float previousSDelivery = 0;
			float previousSToGo = 0;
			float previousSEatIn = 0;
			
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
				
			}
			rs1.close();
			if(period.equals("Yearly"))
			{
				rs2 = stmt.executeQuery(queryString5);
			}
			else if(period.equals("Monthly"))
			{
				rs2 = stmt.executeQuery(queryString6);
			}
			else if(period.equals("Daily"))
			{
				rs2 = stmt.executeQuery(queryString7);
			}
			else if(period.equals("Hourly"))
			{
				rs2 = stmt.executeQuery(queryString8);
			}
			while(rs2.next())
			{
				previousOrders = rs2.getInt(1);
				previousSales = rs2.getInt(2);
				previousProducts = rs2.getInt(3);
				previousAvgProductPrice = rs2.getFloat(4);
				previousNoOfChangeInPrice = rs2.getInt(5);
				previousMinPrice = rs2.getInt(6);
				previousMaxPrice = rs2.getFloat(7);
				previousBasketSize = rs2.getInt(8);
				previousBasketValue = rs2.getFloat(9);
				previousNoOfDisc = rs2.getInt(10);
				previousAvgDisc = rs2.getFloat(11);
				previousNoOfReturns = rs2.getInt(12);
				previousSalesLost = rs2.getFloat(13);
				previousOThirdParty = rs2.getInt(14);
				previousOOnlineOrdering = rs2.getInt(15);
				previousOFoodPanda = rs2.getInt(16);
				previousOCatering = rs2.getInt(17);
				previousODelivery = rs2.getInt(18);
				previousOToGo = rs2.getInt(19);
				previousOEatIn = rs2.getInt(20);
				previousSThirdParty = rs2.getFloat(21);
				previousSOnlineOrdering = rs2.getFloat(22);
				previousSFoodPanda = rs2.getFloat(23);
				previousSCatering = rs2.getFloat(24);
				previousSDelivery = rs2.getFloat(25);
				previousSToGo = rs2.getFloat(26);
				previousSEatIn = rs2.getFloat(27);
				
			}
			
			int liftOrders = calculateLift(currentOrders, previousOrders);
			int liftSales = calculateLift(currentSales, previousSales);
			int liftProducts = calculateLift(currentProducts, previousProducts);
			int liftAvgProductPrice = calculateLift(currentAvgProductPrice, previousAvgProductPrice);
			int liftNoOfChangeInPrice = calculateLift(currentNoOfChangeInPrice, previousNoOfChangeInPrice);
			int liftMinPrice = calculateLift(currentMinPrice, previousMinPrice);
			int liftMaxPrice = calculateLift(currentMaxPrice, previousMaxPrice);
			int liftBasketSize = calculateLift(currentBasketSize, previousBasketSize);
			int liftBasketValue = calculateLift(currentBasketValue, previousBasketValue);
			int liftNoOfDisc = calculateLift(currentNoOfDisc, previousNoOfDisc);
			int liftAvgDisc = calculateLift(currentAvgDisc, previousAvgDisc);
			int liftNoOfReturns = calculateLift(currentNoOfReturns, previousNoOfReturns);
			int liftSalesLost = calculateLift(currentSalesLost, previousSalesLost);
			int liftOThirdParty = calculateLift(currentOThirdParty, previousOThirdParty);
			int liftOOnlineOrdering = calculateLift(currentOOnlineOrdering, previousOOnlineOrdering);
			int liftOFoodPanda = calculateLift(currentOFoodPanda, previousOFoodPanda);
			int liftOCatering = calculateLift(currentOCatering, previousOCatering);
			int liftODelivery = calculateLift(currentODelivery, previousODelivery);
			int liftOToGo = calculateLift(currentOToGo, previousOToGo);
			int liftOEatIn = calculateLift(currentOEatIn, previousOEatIn);
			int liftSThirdParty = calculateLift(currentSThirdParty, previousSThirdParty);
			int liftSOnlineOrdering = calculateLift(currentSOnlineOrdering, previousSOnlineOrdering);
			int liftSFoodPanda = calculateLift(currentSFoodPanda, previousSFoodPanda);
			int liftSCatering = calculateLift(currentSCatering, previousSCatering);
			int liftSDelivery = calculateLift(currentSDelivery, previousSDelivery);
			int liftSToGo = calculateLift(currentSToGo, previousSToGo);
			int liftSEatIn = calculateLift(currentSEatIn, previousSEatIn);
			
			xmlResponse = xmlResponse + "<LiftOrders>" + liftOrders + "%</LiftOrders>";
			xmlResponse = xmlResponse + "<LiftSales>" + liftSales + "%</LiftSales>";
			xmlResponse = xmlResponse + "<LiftProducts>" + liftProducts + "%</LiftProducts>";
			xmlResponse = xmlResponse + "<LiftAvgProductPrice>" + liftAvgProductPrice + "%</LiftAvgProductPrice>";
			xmlResponse = xmlResponse + "<LiftNoOfChangeInPrice>" + liftNoOfChangeInPrice + "%</LiftNoOfChangeInPrice>";
			xmlResponse = xmlResponse + "<LiftMinPrice>" + liftMinPrice + "%</LiftMinPrice>";
			xmlResponse = xmlResponse + "<LiftMaxPrice>" + liftMaxPrice + "%</LiftMaxPrice>";
			xmlResponse = xmlResponse + "<LiftBasketSize>" + liftBasketSize + "%</LiftBasketSize>";
			xmlResponse = xmlResponse + "<LiftBasketValue>" + liftBasketValue + "%</LiftBasketValue>";
			xmlResponse = xmlResponse + "<LiftNoOfDisc>" + liftNoOfDisc + "%</LiftNoOfDisc>";
			xmlResponse = xmlResponse + "<LiftAvgDisc>" + liftAvgDisc + "%</LiftAvgDisc>";
			xmlResponse = xmlResponse + "<LiftNoOfReturns>" + liftNoOfReturns + "%</LiftNoOfReturns>";
			xmlResponse = xmlResponse + "<LiftSalesLost>" + liftSalesLost + "%</LiftSalesLost>";
			xmlResponse = xmlResponse + "<LiftOThirdParty>" + liftOThirdParty + "%</LiftOThirdParty>";
			xmlResponse = xmlResponse + "<LiftOOnlineOrdering>" + liftOOnlineOrdering + "%</LiftOOnlineOrdering>";
			xmlResponse = xmlResponse + "<LiftOFoodPanda>" + liftOFoodPanda + "%</LiftOFoodPanda>";
			xmlResponse = xmlResponse + "<LiftOCatering>" + liftOCatering + "%</LiftOCatering>";
			xmlResponse = xmlResponse + "<LiftODelivery>" + liftODelivery + "%</LiftODelivery>";
			xmlResponse = xmlResponse + "<LiftOToGo>" + liftOToGo + "%</LiftOToGo>";
			xmlResponse = xmlResponse + "<LiftOEatIn>" + liftOEatIn + "%</LiftOEatIn>";
			xmlResponse = xmlResponse + "<LiftSThirdParty>" + liftSThirdParty + "%</LiftSThirdParty>";
			xmlResponse = xmlResponse + "<LiftSOnlineOrdering>" + liftSOnlineOrdering + "%</LiftSOnlineOrdering>";
			xmlResponse = xmlResponse + "<LiftSFoodPanda>" + liftSFoodPanda + "%</LiftSFoodPanda>";
			xmlResponse = xmlResponse + "<LiftSCatering>" + liftSCatering + "%</LiftSCatering>";
			xmlResponse = xmlResponse + "<LiftSDelivery>" + liftSDelivery + "%</LiftSDelivery>";
			xmlResponse = xmlResponse + "<LiftSToGo>" + liftSToGo + "%</LiftSToGo>";
			xmlResponse = xmlResponse + "<LiftSEatIn>" + liftSEatIn + "%</LiftSEatIn>";
			
			xmlResponse = xmlResponse + "</Summary>";
			response.getWriter().append(xmlResponse);
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception!");
			e.printStackTrace();
		}
	}
	private int calculateLift(float a, float b) 
	{
		float lift = 0.0f;
	    if(b==0)
	    {
	    	lift = 0.0f;
	    }
	    else
	    {
	    	lift = (a/b);
	    	lift = lift*100;
	    }
	    int cLift = (int) lift;
	    return cLift;
	}
}
