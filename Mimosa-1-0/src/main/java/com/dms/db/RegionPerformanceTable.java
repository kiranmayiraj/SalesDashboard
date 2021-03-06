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

public class RegionPerformanceTable 
{
	public TableResult fetchRegionPerformanceTable(String country, String state, String city, String department, String category, String period, String cxt)
	{
		String jsonPath = cxt + "/WEB-INF/classes/total-dreamer-274815-6bea838e893a.json";
		File file = new File(jsonPath);
		try 
		{
			InputStream is = new FileInputStream(file);
			GoogleCredentials credentials;
			String query = "SELECT"
					+ " Item,"
					+ " Sales,"
					+ " Orders,"
					+ " Total_Customers,"
					+ " New_customers,"
					+ " Repeat_Customers,"
					+ " Return_Orders"
					+ " FROM `total-dreamer-274815.Staging.MV_Mimosa_Regional_Performance_Table`"
					+ " where Date_filter='"+period+"'and Country_filter='"+country+"' and State_filter='"+state+"' and City_filter='"+city+"'"
					+ " and Department_filter='"+department+"' and Category_filter='"+category+"'"
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
