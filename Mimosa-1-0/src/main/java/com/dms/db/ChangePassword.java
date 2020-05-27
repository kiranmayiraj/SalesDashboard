package com.dms.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.dms.bean.User;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

public class ChangePassword 
{
	public boolean	changePassword(User user, String currentPassword, String cxt)
	{
		String emailId = user.getEmailId();
		String password = user.getPassword();
		
		String jsonPath = cxt + "/WEB-INF/classes/total-dreamer-274815-6bea838e893a.json";
		File file = new File(jsonPath);
		
		try 
		{
			boolean valid = validatePassword(emailId, currentPassword, cxt);
			if(valid)
			{
				InputStream is = new FileInputStream(file);
				GoogleCredentials credentials;
				String query = "update `total-dreamer-274815.E_rap.users` set password='"+password+"' where emailId='"+emailId+"'";
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
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (IOException | InterruptedException e) 
		{
	        e.printStackTrace();
		}
		return false;
	}
	private boolean validatePassword(String emailId, String currentPassword, String cxt)
	{
		String jsonPath = cxt + "/WEB-INF/classes/total-dreamer-274815-6bea838e893a.json";
		File file = new File(jsonPath);
		
		try 
		{
			InputStream is = new FileInputStream(file);
			GoogleCredentials credentials;
			String query = "select * from `total-dreamer-274815.E_rap.users` where emailId='"+emailId+"' and password='"+currentPassword+"'";
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
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (IOException | InterruptedException e) 
		{
	        e.printStackTrace();
		}
		return false;
	}
}