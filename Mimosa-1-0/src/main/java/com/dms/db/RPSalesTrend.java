package com.dms.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobException;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

public class RPSalesTrend 
{
	public TableResult fetchRPSalesTrend(String country, String state, String city, String department, String category, String period, String cxt)
	{
		String jsonPath = cxt + "/WEB-INF/classes/total-dreamer-274815-6bea838e893a.json";
		File file = new File(jsonPath);
		try 
		{
			InputStream is = new FileInputStream(file);
			GoogleCredentials credentials;
			String query = null;
			String query1 = "SELECT"
					+ " Sale_Month,"
					+ " Volume,"
					+ " Value"
					+ " FROM `total-dreamer-274815.Staging.MV_Mimosa_Trend`"
					+ " where Filter='"+period+"' and Country='"+country+"' and State='"+state+"' and City='"+city+"'"
					+ " and Department='"+department+"' and Product_Category='"+category+"'";
			
			String query2 = "SELECT"
					+ " Sale_Week,"
					+ " Volume,"
					+ " Value"
					+ " FROM `total-dreamer-274815.Staging.MV_Mimosa_Trend`"
					+ " where Filter='"+period+"' and Country='"+country+"' and State='"+state+"' and City='"+city+"'"
					+ " and Department='"+department+"' and Product_Category='"+category+"'";
			
			String query3 = "SELECT"
					+ " Sale_Date,"
					+ " Volume,"
					+ " Value"
					+ " FROM `total-dreamer-274815.Staging.MV_Mimosa_Trend`"
					+ " where Filter='"+period+"' and Country='"+country+"' and State='"+state+"' and City='"+city+"'"
					+ " and Department='"+department+"' and Product_Category='"+category+"'";
			
			String query4 = "SELECT"
					+ " Sale_Date,"
					+ " Volume,"
					+ " Value"
					+ " FROM `total-dreamer-274815.Staging.MV_Mimosa_Trend`"
					+ " where Filter='"+period+"' and Country='"+country+"' and State='"+state+"' and City='"+city+"'"
					+ " and Department='"+department+"' and Product_Category='"+category+"'";
			

			if(period.equals("2018"))
			{
				query = query1 + "  and Sale_Year=2018";
			}
			if(period.equals("2019"))
			{
				query = query1 + "  and Sale_Year=2019";
			}
			if(period.equals("YTD"))
			{
				query = query1 + "  and Sale_Year=2020";
			}
			if(period.equals("MTD"))
			{
				query = query2 + " and Sale_Year=2020 and Sale_Month='March'";
			}
			if(period.equals("WEEK"))
			{
				query = query3 + " and Sale_Year=2020 and Sale_Month='March' and Sale_Week='4'";
			}
			if(period.equals("DAY"))
			{
				query = query4 + " and Sale_Year=2020 and Sale_Month='March' and Sale_Week='4' and Sale_Date='2020-03-17'";
			}
			
			credentials = GoogleCredentials.fromStream(is);
			BigQuery bigquery = BigQueryOptions.newBuilder()
					.setCredentials(credentials)
					.build().getService();
			
			QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
					.setUseLegacySql(false)
					.build();
			
			JobId jobId = JobId.of(UUID.randomUUID().toString());
			Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
			queryJob = queryJob.waitFor();
			if (queryJob == null) 
			{
				throw new RuntimeException("Job no longer exists");
			} 
			else if (queryJob.getStatus().getError() != null) 
			{
				throw new RuntimeException(queryJob.getStatus().getError().toString());
			}
			
			TableResult result = queryJob.getQueryResults();
			return result;
		}
		catch (JobException | InterruptedException | IOException e) 
		{
	        e.printStackTrace();
		}
		return null;
	}
}
