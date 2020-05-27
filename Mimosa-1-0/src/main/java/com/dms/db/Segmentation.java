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

public class Segmentation 
{
	public TableResult fetchSegmentationKpi(String cxt, String cxt1)
	{
		String jsonPath = cxt + "/WEB-INF/classes/total-dreamer-274815-6bea838e893a.json";
		File file = new File(jsonPath);
		String className = this.getClass().getSimpleName();
		String filepath = cxt1 + "SOB#" + className;
		File segFile = new File(filepath);
		Serialization ser = new Serialization();
		if(!segFile.exists())
		{
			try 
			{
				InputStream is = new FileInputStream(file);
				GoogleCredentials credentials;
				String query = "SELECT"
						+ " Segment,"
						+ " Value,"
						+ " Customers,"
						+ " Tenure,"
						+ " ARPC,"
						+ " AOV,"
						+ " UPT,"
						+ " CLV,"
						+ " Avg_Orders,"
						+ " X_Value"
						+ " FROM `total-dreamer-274815.Staging.MV_Mimosa_segmentation`";
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
				ser.writeObjectToFile(filepath, result);
				return result;
			}
			catch (JobException | InterruptedException | IOException e) 
			{
		        e.printStackTrace();
			}
		} 
		else 
		{ 
			Object obj = ser.readObjectFromFile(filepath); 
			return (TableResult)obj; 
		}
		return null;
	}
}