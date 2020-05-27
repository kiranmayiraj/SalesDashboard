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

public class RegionPerformanceKpi 
{
	public TableResult fetchRegionPerformanceKpi(String period, String country, String state, String city, String department, String category, String cxt)
	{
		String jsonPath = cxt + "/WEB-INF/classes/total-dreamer-274815-6bea838e893a.json";
		File file = new File(jsonPath);
		try 
		{
			InputStream is = new FileInputStream(file);
			GoogleCredentials credentials;
			String query = "SELECT"
					+ " Sales,"
					+ " Sales_IN_DC,"
					+ " Orders,"
					+ " Orders_IN_DC,"
					+ " Total_Customers,"
					+ " Customers_IN_DC,"
					+ " ARPC,"
					+ " ARPC_IN_DC,"
					+ " UPT,"
					+ " UPT_IN_DC,"
					+ " Repeat_Orders,"
					+ " Repeat_Orders_IN_DC,"
					+ " Repeat_order_value,"
					+ " Repeat_order_value_IN_DC,"
					+ " Return_Orders,"
					+ " Return_orders_IN_DC"
					+ " FROM `total-dreamer-274815.Staging.MV_Mimosa_RP_Summarized_kpis`"
					+ " where Date_Filter='"+period+"' and Country_Filter='"+country+"' and State_Filter='"+state+"' and City_Filter='"+city+"'"
					+ " and Department_Filter='"+department+"' and Category_Filter='"+category+"'"
					+ " ORDER BY Sales DESC";
			
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
