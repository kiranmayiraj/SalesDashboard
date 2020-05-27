package com.dms;

public class NumberFormatting 
{
	public static void main(String[] args) 
	{	
		
	}
	public String formatNumber(long num)
	{
		int count = -1;
		double temp = num;
		while(temp>1)
		{
			temp = temp / 1000;
			count++;
		}
		temp = temp * 1000;
		String str = String.format("%.1f", temp); 
		if(count==0)
		{
			int temp2 = (int) temp;
			return temp2+""; 
		}
		if(count == 1)
		{
			return str+"K";
		}
		else if(count == 2)
		{
			return str+"M";
		}
		else if(count == 3)
		{
			return str+"B";
		}
		return ""+num;
	}
	
	public String formatNumber(float num)
	{
		int count = -1;
		double temp = num;
		while(temp>1)
		{
			temp = temp / 1000;
			count++;
		}
		temp = temp * 1000;
		String str = String.format("%.2f", temp); 
		if(count==0)
		{
			float temp2 = (float) temp;
			return String.format("%.2f", temp2)+""; 
		}
		if(count == 1)
		{
			return str+"K";
		}
		else if(count == 2)
		{
			return str+"M";
		}
		else if(count == 3)
		{
			return str+"B";
		}
		return ""+num;
	}
}
