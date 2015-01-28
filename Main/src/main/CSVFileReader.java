package main;

/**
 * Source code from:
 * http://www.daniweb.com/software-development/java/threads/17262/reading-in-a-.csv-file-and-loading-the-data-into-an-array
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
public class CSVFileReader {
	String fileName;

	ArrayList <String>storeValues = new ArrayList<String>();
	public CSVFileReader(String FileName)
	{
		this.fileName=FileName;
	}

	public void ReadFile()
	{
		try {
			//storeValues.clear();//just in case this is the second call of the ReadFile Method./
			BufferedReader br = new BufferedReader( new FileReader(fileName));
			@SuppressWarnings("unused")
			StringTokenizer st = null;
			while( (fileName = br.readLine()) != null)
			{
				storeValues.add(fileName);
				//break comma separated line using ","
				st = new StringTokenizer(fileName, ",");

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void setFileName(String newFileName)
	{
		this.fileName=newFileName;
	}
	public String getFileName()
	{
		return fileName;
	}
	public ArrayList<String> getFileValues()
	{
		return this.storeValues;
	}

	@SuppressWarnings("deprecation")
	public ArrayList<String> displayOne(Date BeginDate, Date EndDate) throws ParseException
	{
		ArrayList<String> mylist=new ArrayList<String>();
		String [][] numbers= new String [storeValues.size()+2][7];
		int j=0;
		for(int x=0;x<this.storeValues.size();x++)
		{
			StringTokenizer st = null;
			st = new StringTokenizer(storeValues.get(x), ",");
			while(st.hasMoreElements())
			{
				numbers[x][j]=st.nextToken();
				j++;
			}
			j=0;
		}
		for(int i=1;i<storeValues.size()-2;i++)
		{
			Date mydate=new Date(numbers[i][0]);
			if(mydate.getDate()>=BeginDate.getDate()&& mydate.getMonth()>=BeginDate.getMonth() && mydate.getYear()>=BeginDate.getYear() && mydate.getDate()<EndDate.getDate()&& mydate.getMonth()<EndDate.getMonth() && mydate.getYear()<=EndDate.getYear())
			{
				mylist.add(storeValues.get(i));
				System.out.println("Date :" +storeValues.get(i));
			}
		}
		return mylist;
	}

	public void displayArrayList()
	{
		for(int x=0;x<this.storeValues.size();x++)
		{
			System.out.println(storeValues.get(x));
		}
	}

}