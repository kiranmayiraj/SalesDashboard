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

public class EmailIdChecking
{
	public static void main(String[] args)
	{

	}
	public boolean checkEmailId(String emailId, String cxt)
	{
		String jsonPath = cxt + "/WEB-INF/classes/total-dreamer-274815-6bea838e893a.json";
		File file = new File(jsonPath);
		
		try 
		{
			InputStream is = new FileInputStream(file);
			GoogleCredentials credentials;
			String query = "select * from `total-dreamer-274815.E_rap.users` where emailId='"+emailId+"'";
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
			long totalRows = result.getTotalRows();
			
			if(totalRows>0)
			{
				//System.out.println(""+emailId+" is already is use");
				return false;
			}
			else if(totalRows==0)
	        {
				//System.out.println(""+emailId+" is available");
				return true;
	        }
		}
		catch (JobException | InterruptedException | IOException e) 
		{
	        e.printStackTrace();
	        return false;
		}
		return false;
	}
}