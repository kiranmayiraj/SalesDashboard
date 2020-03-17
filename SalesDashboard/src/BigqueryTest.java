import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.activation.DataSource;

//@WebServlet("/BigqueryTest")
public class BigqueryTest 
{
    
    public BigqueryTest() 
    {
        super();
    }
    public static void main(String... args)
    {
        // Instantiates a client
        BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

        // The name for the new dataset
        String datasetName = "bigquery-public-data:hacker_news.comments";

        // Prepares a new dataset
        Dataset dataset = null;
        DatasetInfo datasetInfo = DatasetInfo.newBuilder(datasetName).build();

        // Creates the dataset
        dataset = bigquery.create(datasetInfo);

        System.out.printf("Dataset %s created.%n", dataset.getDatasetId().getDataset());
  } 
    /*public static void main(String args[])
	{
		Connection con = null;
		ResultSet rs = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:bigquery://35.240.205.35;ProjectId=Trail;OAuthType=0;");

			System.out.println("Connection: "+con);
			Statement stmt = con.createStatement();
			
			String queryString1 = "SHOW tables";
			rs = stmt.executeQuery(queryString1);
			System.out.println(rs);
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
	}*/
}