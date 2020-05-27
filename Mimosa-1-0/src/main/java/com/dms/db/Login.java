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
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobException;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

public class Login 
{
	public int authenticateUser(User user, String cxt)
	{
		String emailId = user.getEmailId();
		String password = user.getPassword();
		
		String jsonPath = cxt + "/WEB-INF/classes/total-dreamer-274815-6bea838e893a.json";
		File file = new File(jsonPath);
		
		try 
		{
			InputStream is = new FileInputStream(file);
			GoogleCredentials credentials;
			String query = "select * from `total-dreamer-274815.E_rap.users` where emailId='"+emailId+ "' and password='"+password+"'";
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
			for (FieldValueList row : result.iterateAll()) 
			{
				String emailIdx = row.get("emailId").getStringValue();
			}
			System.out.println("Rows: "+result.getTotalRows());
			return (int)result.getTotalRows();
			
		}
		catch (JobException | InterruptedException | IOException e) 
		{
	        e.printStackTrace();
		}
		return 0;
	}
}
