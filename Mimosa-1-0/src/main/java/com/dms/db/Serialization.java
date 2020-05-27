package com.dms.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Serialization 
{
	public static void main(String[] args) 
	{
	
	}
	public void writeObjectToFile(String filepath, Object serObj) 
	{	
	    try 
	    {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            File file = new File(filepath);
            System.out.println("The Object  was succesfully written to a file. It is modified at "+file.lastModified());
        } 
	    catch (Exception ex) 
	    {
            ex.printStackTrace();
        }
    }
	public Object readObjectFromFile(String filepath) 
	{
		try 
	    {
            FileInputStream fileOut = new FileInputStream(filepath);
            ObjectInputStream objectOut = new ObjectInputStream(fileOut);
            Object obj = objectOut.readObject();
            objectOut.close();
            System.out.println("The Object was read succesfully.");
            return obj;
        } 
	    catch (Exception ex) 
	    {
            ex.printStackTrace();
        }
		return null;
    }
	public HashMap<String, Boolean> deleteSFiles(String cxt)
	{
		System.out.println("Cxt: " + cxt);
		HashMap<String, Boolean> hm = new HashMap<String, Boolean>();
		String path = cxt;
		File dir = new File(path);
		if(dir.exists())
		{
			if(dir.isDirectory())
			{
				File [] files = dir.listFiles();
				for(int i=0; i<files.length; i++)
				{
					if(files[i].getName().startsWith("SOB#"))
					{
						String temp = files[i].getName();
						boolean delStatus = files[i].delete();
						hm.put(temp, delStatus);
					}
				}
			}
		}
		return hm;		
	}
}