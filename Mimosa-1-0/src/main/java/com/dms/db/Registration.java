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
import com.google.cloud.bigquery.JobException;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

public class Registration 
{
	public void RegisterUser(User user, String cxt)
	{
		String storeName = user.getStoreName();
		String password = user.getPassword();
		String emailId = user.getEmailId();
		String phoneNo = user.getPhoneNo();
		String jsonPath = cxt + "/WEB-INF/classes/total-dreamer-274815-6bea838e893a.json";
		
		
		
		File file = new File(jsonPath);
		try
		{
			InputStream is = new FileInputStream(file);
			GoogleCredentials credentials;
			String query = "insert into `total-dreamer-274815.E_rap.users` values ('"+storeName+"','"+emailId+"','"+phoneNo+"','"+password+"')";
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
			System.out.println("QJ Status Code: "+queryJob.getStatus());
			System.out.println("QJ is Done?: "+queryJob.isDone());
			TableResult result = queryJob.getQueryResults();
			System.out.println("Rows: "+result.getTotalRows());
			/*for (FieldValueList row : result.iterateAll()) 
			{
				String emailIdx = row.get(storeName);
				
			}*/
			//	return (int)result.getTotalRows();
			System.out.println("You are successfully registered...");  
		}
		catch (JobException | InterruptedException | IOException e) 
		{
			e.printStackTrace();
		}
	}
}