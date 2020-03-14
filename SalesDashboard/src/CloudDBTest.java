import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//@WebServlet("/SummaryData")
public class CloudDBTest 
{
    
    public CloudDBTest() 
    {
        super();
    }
    public static void main(String args[])
	{
		Connection con = null;
		ResultSet rs = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://35.240.205.35/testdb","root","root");
			
			System.out.println("Connection: "+con);
			Statement stmt = con.createStatement();
			
			String queryString1 = "SHOW tables";
			rs = stmt.executeQuery(queryString1);
			System.out.println(rs);
			String xmlResponse = "";
			while(rs.next())
			{
				
			}
			con.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception!");
			e.printStackTrace();
		}
	}
}