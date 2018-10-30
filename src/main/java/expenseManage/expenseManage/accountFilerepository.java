package expenseManage.expenseManage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.springframework.stereotype.Service;

@Service
public class accountFilerepository implements accountRepository {
	

	String filename="/Users/Mubarak/Documents/workspa/accountmanagement/src/accountmanagement/accholders.txt";
	String accnum="/Users/Mubarak/Documents/workspa/accountmanagement/src/accountmanagement/userdetails.txt";
	//
	

	public void createUserTable(String username) throws SQLException, IOException 
	{
	    String path="/Users/Mubarak/Documents/"+username+".txt";
	    File file = new File(path);
       
	    if (!file.exists()) 
        {
	        file.createNewFile();
	    }
	}
	
	//
	public void insertIntoBasicTable(int id, String username) throws SQLException, IOException
	
	{
		
		
		FileWriter filewrite = new FileWriter(filename,true);
	    BufferedWriter bufferwrite = new BufferedWriter(filewrite);
	  
        bufferwrite.write(id+","+username+"\n");
		bufferwrite.close();
		
		BufferedWriter bs = new BufferedWriter(new FileWriter(accnum));
		bs.write(id+"");
		bs.close();
	 
	}

	//
   public int readMaxOfids(String username) throws SQLException, FileNotFoundException 
   {
		Scanner accnumget = new Scanner(new File(accnum));
		accnumget.useDelimiter(" |\n");
		int currentnumber;
		currentnumber=Integer.parseInt(accnumget.next());
		return currentnumber;
	}
	
   public int readMaxOfid(String username) throws SQLException, FileNotFoundException 
   {int counts=0;
	   int count=0;
	   String path="/Users/Mubarak/Documents/"+username+".txt";
		String a="0";
		Scanner read = new Scanner(new File(path));
		read.useDelimiter("\n|,");
		while(read.hasNext())
		{a=read.next();
		String b=read.next();
		String c=read.next();
		String d=read.next();
		String e=read.next();
		count++;
		}
		int m=Integer.parseInt(a);
		read.close();
		if(count<1)	
			counts = 0;
		else
		 counts = m;	
		return counts;
	}
	
	public void depositInsert(String accountname, int id, String amount, String mode, String reason, float bal) throws SQLException
	 {
		
		try 
		{ 
			FileWriter fw;
			fw = new FileWriter("/Users/Mubarak/Documents/"+accountname+".txt",true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(id+","+amount+","+"deposit"+","+reason+","+bal+"\n");
			bw.close();
		} 
		catch (IOException e1)
		
		{
			e1.printStackTrace();
		}
				
		
	}
	
	public void withdrawInsert(String accountname, int id, String amount, String mode, String reason, float bal)
			throws SQLException 
	{
		try 
		
	{
			
		FileWriter fw;
		fw = new FileWriter("/Users/Mubarak/Documents/"+accountname+".txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(id+","+amount+","+mode+","+reason+","+bal+"\n");
		bw.close();
	} catch (IOException e1) 
	{
		e1.printStackTrace();
	}
		
	}

	public double getBalance(String accountname, int accountid) throws SQLException, FileNotFoundException 
	{
		Double balance=0.0;
		int count=0;
		String openaccount="/Users/Mubarak/Documents/"+accountname+".txt";
		String e="0";
		Scanner read = new Scanner(new File(openaccount));
		read.useDelimiter(" |\n|,");
		
		while(read.hasNext())
		{
		String a=read.next();
		String b=read.next();
		String c=read.next();
		String d=read.next();
		e=read.next();
		count++;
		}
		
		double ss=0.0;
		double m=Double.parseDouble(e);
		
		if(count<1)	
			balance = 0.0;
		else
		    balance=m;
		return balance;
		
	}

	public String readNameOfAccount(String tablename, int accountid) throws SQLException
	
	{
		
			HashMap<Integer,accountFilerepository> newmap = new HashMap<Integer, accountFilerepository>(100);
		
			 String returnName = "falseoutput";
			 String checkid=Integer.toString(accountid);
			 Scanner scan;
			try
			{
				scan = new Scanner(new File(filename));
				 scan.useDelimiter(",|\n");
					
					
					while(scan.hasNext())
					
					{
						String h = scan.next();
					    String z = scan.next();
					    
					    if(h.equals(checkid))
					    
					    {
					    	returnName=z;
					    	
					    }
					
					}
					scan.close();
			} 
			
		    catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			
			 
		return returnName;
	}

	public void dropUserTable(String tablename) throws SQLException
	
	{
	
		File file = new File("/Users/Mubarak/Documents/"+tablename+".txt");
		file.delete();
	}

	public void deleteFromUserTable(String accountname, int id) throws SQLException
	{try{
		int count = 0;
		
	    HashMap<Integer,undoModels> accountmap = new HashMap<Integer,undoModels>(100);
		undoModels[] copyobjects = new undoModels[100];
		String filename="/Users/Mubarak/Documents/"+accountname+".txt";
		Scanner scan = new Scanner(new File("/Users/Mubarak/Documents/"+accountname+".txt"));
		scan.useDelimiter(",|\n");
		undoModels[] s = new undoModels[100];
		while(scan.hasNext())
		{
		String h = scan.next();
		String z1 = scan.next();
		String x = scan.next();
		String c = scan.next();
		String p=scan.next();
		undoModels[] temp=new undoModels[100];
		temp[count]=new undoModels(h,z1,x,c,p);
		accountmap.put(count,temp[count]);
		count++;
		}
	
		
		FileWriter fw = new FileWriter(filename);
		BufferedWriter bw = new BufferedWriter(fw);
	
		
		
		
		int flag = 0;
		
		for(int i=0;i<count;i++)
		{
			s[i]=new undoModels(accountmap.get(i).a,accountmap.get(i).b,accountmap.get(i).c,accountmap.get(i).d,accountmap.get(i).e);
		if(Integer.parseInt(s[i].a)!=(id))
		{
			bw.write(s[i].a+","+s[i].b+","+s[i].c+","+s[i].d+","+s[i].e+"\n");
		}
		else
		{
			
		}
		
		}bw.close();
		}catch(FileNotFoundException e)
	{
			
	}catch(IOException e)
	{
		
	}
	}


	public void deleteFromUserTable(String accountname) throws SQLException {
		try {
			HashMap<Integer,accountFilerepositoryModel> newmap = new HashMap<Integer, accountFilerepositoryModel>(100);
		accountFilerepositoryModel[] saveobject=new accountFilerepositoryModel[10000];
		 String returnName = "falseoutput";
		 int count=0;
		 Scanner scan;
		
		 try {
			scan = new Scanner(new File(filename));
			scan.useDelimiter(",|\n");				
			 while(scan.hasNext())
				{
					String h = scan.next();
				    String z = scan.next();
				    
					saveobject[count]=new accountFilerepositoryModel(h,z);
					newmap.put(count,saveobject[count]);
					count++;
				
				}
			 scan.close();
		
		 } catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		 FileWriter fw;
		
			fw = new FileWriter(filename,true);
		
			BufferedWriter bw = new BufferedWriter(fw);
			
			accountFilerepositoryModel[] saveobject1=new accountFilerepositoryModel[10000];
			
			
			for(int i=0;i<count;i++)
			{
				saveobject1[i]=new accountFilerepositoryModel(newmap.get(i).id,newmap.get(i).name);
			if(!(saveobject1[i].name).equals(accountname))
			{
				bw.write(saveobject1[i].id+","+saveobject1[i].name+"\n");
			}
			else
			{
				
			}
			
			}
			
			bw.close();} catch(IOException e) {
				
				e.printStackTrace();
			}
		
	}


	public void updateTable(String accountname, float updbals, int id) throws SQLException 
	{
	
		try{
			int count = 0;
			
		    HashMap<Integer,undoModels> accountmap = new HashMap<Integer,undoModels>(100);
			undoModels[] copyobjects = new undoModels[100];
			String filename="/Users/Mubarak/Documents/"+accountname+".txt";
			Scanner scan = new Scanner(new File("/Users/Mubarak/Documents/"+accountname+".txt"));
			scan.useDelimiter(",|\n");
			undoModels[] s = new undoModels[100];
			while(scan.hasNext())
			{
			String h = scan.next();
			String z1 = scan.next();
			String x = scan.next();
			String c = scan.next();
			String p=scan.next();
			undoModels[] temp=new undoModels[100];
			temp[count]=new undoModels(h,z1,x,c,p);
			accountmap.put(count,temp[count]);
			count++;
			}
		
			
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);
		
			
			
			
			int flag = 0;
			
			for(int i=0;i<count;i++)
			{
				s[i]=new undoModels(accountmap.get(i).a,accountmap.get(i).b,accountmap.get(i).c,accountmap.get(i).d,accountmap.get(i).e);
			if(Integer.parseInt(s[i].a)!=(id))
			{
				bw.write(s[i].a+","+s[i].b+","+s[i].c+","+s[i].d+","+s[i].e+"\n");
			}
			else
			{
				bw.write(s[i].a+","+s[i].b+","+s[i].c+","+s[i].d+","+updbals+"\n");	
			}
			
			}bw.close();
			}catch(FileNotFoundException e)
		{
				
		}catch(IOException e)
		{
			
		}
	}


	public void updateTable(String accountname, String reason, String amounts, int id) throws SQLException
	{
		try{
			int count = 0;
			
		    HashMap<Integer,undoModels> accountmap = new HashMap<Integer,undoModels>(100);
			undoModels[] copyobjects = new undoModels[100];
			String filename="/Users/Mubarak/Documents/"+accountname+".txt";
			Scanner scan = new Scanner(new File("/Users/Mubarak/Documents/"+accountname+".txt"));
			scan.useDelimiter(",|\n");
			undoModels[] s = new undoModels[100];
			while(scan.hasNext())
			{
			String h = scan.next();
			String z1 = scan.next();
			String x = scan.next();
			String c = scan.next();
			String p=scan.next();
			undoModels[] temp=new undoModels[100];
			temp[count]=new undoModels(h,z1,x,c,p);
			accountmap.put(count,temp[count]);
			count++;
			}
		
			
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);
		
			
			
			
			int flag = 0;
			
			for(int i=0;i<count;i++)
			{
				s[i]=new undoModels(accountmap.get(i).a,accountmap.get(i).b,accountmap.get(i).c,accountmap.get(i).d,accountmap.get(i).e);
			if(Integer.parseInt(s[i].a)!=(id))
			{
				bw.write(s[i].a+","+s[i].b+","+s[i].c+","+s[i].d+","+s[i].e+"\n");
			}
			else
			{
				bw.write(s[i].a+","+amounts+","+s[i].c+","+reason+","+s[i].e+"\n");	
			}
			
			}bw.close();
			}catch(FileNotFoundException e)
		{
				
		}catch(IOException e)
		{
			
		}
		
	}


	public void updatebal(String accountname, String amounts, int id) throws SQLException
	
	{
		
		
	}


	public ArrayList<viewobject> selectables(String tablename) throws SQLException, ClassNotFoundException
	{
	viewobject object=new viewobject();
	ArrayList<viewobject> sendArray=new ArrayList<viewobject>();
   
try{
	undoModels[] copyobjects = new undoModels[100];
	
	Scanner scan = new Scanner(new File("/Users/Mubarak/Documents/"+tablename+".txt"));
	scan.useDelimiter(",|\n");
   
	while(scan.hasNext())
	{
		
	String h = scan.next();
	String amount= scan.next();
	String mode = scan.next();
	String reason = scan.next();
	String p=scan.next();
	int idq=Integer.parseInt(h);
	float bal=Float.parseFloat(p);
	float amounts=Float.parseFloat(amount);
	   object=new viewobject(idq,amounts,mode,reason,bal);
       
       sendArray.add(object);
	}
	scan.close();
	
     }
    catch(FileNotFoundException e)
	{    }
	
	return sendArray;
	
	
	}
	
	 public void insertIntoUsertables(String accountname,int id,String amount,String mode,String reason,float bal) throws SQLException 
	  {	  
		  
	  }	
		
		public void insertIntoUsertable(String accountname,int id,String amount,String mode,String reason,float bal) throws SQLException 
		  {	 
			filename = "/Users/Mubarak/Documents/"+accountname+".txt";
			FileWriter f1;
			BufferedWriter b1;
			try
			
			{
				
				f1 = new FileWriter(filename,true);
				b1 = new BufferedWriter(f1);
				b1.write(id+","+amount+","+mode+","+reason+","+bal+"\n");
				b1.close();	
			}
			catch (IOException e) {
			e.printStackTrace();
			}
			
		  }

		public void sort(String accountname) throws SQLException, ClassNotFoundException {
			try{
			String number="/Users/Mubarak/Documents/"+accountname+".txt";;
			FileReader f2 = new FileReader(number);
		    Scanner scan = new Scanner(f2);
		    scan.useDelimiter(" |\n|,");
		    HashMap<Integer,undoModels> accountmap = new HashMap<Integer,undoModels>(100);
			undoModels[] copyobj=new undoModels[100];

		int counts = 0;
		undoModels[] saveobjects= new undoModels[100];
		while(scan.hasNext())
		{
		String id = scan.next();
		String amount = scan.next();
		String mode = scan.next();
		String reason = scan.next();
		String bal=scan.next();
		copyobj[counts]=new undoModels(id,amount,mode,reason,bal);
		accountmap.put(counts,copyobj[counts]);
		counts++;
		} 
		FileWriter fw = new FileWriter(number);
		BufferedWriter bw = new BufferedWriter(fw);

		saveobjects[0]=new undoModels(accountmap.get(0).a,accountmap.get(0).b,accountmap.get(0).c,accountmap.get(0).d,accountmap.get(0).e);
		bw.write(saveobjects[0].a+","+saveobjects[0].b+","+saveobjects[0].c+","+saveobjects[0].d+","+saveobjects[0].e+"\n");
		undoModels[] m=new undoModels[100];
		int i;
		for(i=0;i<counts;i++)
		{
		saveobjects[i]=new undoModels(accountmap.get(i).a,accountmap.get(i).b,accountmap.get(i).c,accountmap.get(i).d,accountmap.get(i).e);
		}
		for(i=1;i<counts-1;i++)
		{int j=i+1;
			int a1=Integer.parseInt(saveobjects[i].a);
		    int a2=Integer.parseInt(saveobjects[j].a);
			if(a1<a2)
		    {
			bw.write(saveobjects[i].a+","+saveobjects[i].b+","+saveobjects[i].c+","+saveobjects[i].d+","+saveobjects[i].e+"\n");
			}
			else
			{
			bw.write(saveobjects[j].a+","+saveobjects[j].b+","+saveobjects[j].c+","+saveobjects[j].d+","+saveobjects[j].e+"\n");
			undoModels temp;
		    temp=saveobjects[j];
		    saveobjects[j]=saveobjects[i];
		    saveobjects[i]=temp;
			}
			
		}
		if(saveobjects[i]!=null)
		bw.write(saveobjects[i].a+","+saveobjects[i].b+","+saveobjects[i].c+","+saveobjects[i].d+","+saveobjects[i].e+"\n");
		bw.close();
			}catch(IOException e)
			{
				
			}
		}

		public void updatedbalances(String tablename) throws SQLException, ClassNotFoundException {
			try
			
		{
			
		 String number="/Users/Mubarak/Documents/"+tablename+".txt";;
		 FileReader f2 = new FileReader(number);
		 Scanner scan = new Scanner(f2);
		 scan.useDelimiter("\n|,");
		 HashMap<Integer,undoModels> accountmap1 = new HashMap<Integer,undoModels>(100);
			undoModels[] copyobj=new undoModels[100];
		copyobj = new undoModels[100];
		int counts = 0;
		accountmap1 = new HashMap<Integer,undoModels>(100);
		undoModels[] copyobj1s = new undoModels[100];

		double updbal=0;
		undoModels[] saveobject = new undoModels[100];
		while(scan.hasNext())
		 {
		 String b = scan.next();
		 String z1 = scan.next();
		 String x = scan.next();
		 String c = scan.next();
		 String q=scan.next();
		 
		 if(x.equals("deposit"))
		 {
	     double z4=Double.parseDouble(z1);
		 updbal=updbal+z4;
		 String newbal=Double.toString(updbal);
		 copyobj1s[counts]=new undoModels(b,z1,x,c,newbal);
		 accountmap1.put(counts,copyobj1s[counts]);
		 counts++;
		 }
		 
		 else if(x.equals("withdraw"))
		 {
		double z4=Double.parseDouble(z1);
		updbal=updbal-z4;
		String newbal=Double.toString(updbal);
		copyobj1s[counts]=new undoModels(b,z1,x,c,newbal);
		accountmap1.put(counts,copyobj1s[counts]);
		counts++;
		}
		 else if((x.indexOf("transferrfromaccount:"))==0)
		
		 {
	    
	    double z4=Double.parseDouble(z1);
		updbal=updbal+z4;
		String newbal=Double.toString(updbal);
		copyobj1s[counts]=new undoModels(b,z1,x,c,newbal);
		accountmap1.put(counts,copyobj1s[counts]);
		counts++;
			
		}
		 else if((x.indexOf("transferredtoaccount:"))==0)
		{
	    double z4=Double.parseDouble(z1);
		updbal=updbal-z4;
		String newbal=Double.toString(updbal);
		copyobj1s[counts]=new undoModels(b,z1,x,c,newbal);
		accountmap1.put(counts,copyobj1s[counts]);
		counts++;	
		}
		 else
		{	 
		copyobj1s[counts]=new undoModels(b,z1,x,c,z1);
		accountmap1.put(counts,copyobj1s[counts]);
		counts++;
		}
		} 
		
		FileWriter fw = new FileWriter(number);
		BufferedWriter bw = new BufferedWriter(fw);

		saveobject[0]=new undoModels(accountmap1.get(0).a,accountmap1.get(0).b,accountmap1.get(0).c,accountmap1.get(0).d,accountmap1.get(0).e);
		bw.write(saveobject[0].a+","+saveobject[0].b+","+saveobject[0].c+","+saveobject[0].d+","+saveobject[0].e+"\n");
		
		if(counts>0)
		
			for(int i=1;i<counts;i++)
		{
			saveobject[i]=new undoModels(accountmap1.get(i).a,accountmap1.get(i).b,accountmap1.get(i).c,accountmap1.get(i).d,accountmap1.get(i).e);

			bw.write(saveobject[i].a+","+saveobject[i].b+","+saveobject[i].c+","+saveobject[i].d+","+saveobject[i].e+"\n");
		}

		bw.close();	
			
		}
			catch(FileNotFoundException e)
			{
			
			}
			catch(IOException e)
			{
				
			}
		

		}

		public undoModels getResultsets(String tablename, int id) throws SQLException {
			undoModels returnobject=new undoModels(); 
			
			try
			{
			
	
		    String checkid=Integer.toString(id);
		    HashMap<Integer,undoModels> accountmap = new HashMap<Integer,undoModels>(100);
			undoModels[] copyobjects = new undoModels[100];
			
			Scanner scan = new Scanner(new File("/Users/Mubarak/Documents/"+tablename+".txt"));
			scan.useDelimiter(",|\n");
		   
			while(scan.hasNext())
			{
				
			String h = scan.next();
			String z1 = scan.next();
			String x = scan.next();
			String c = scan.next();
			String p=scan.next();
			int idq=Integer.parseInt(h);
			float bal=Float.parseFloat(p);
			if(h.equals(checkid))
			{
				returnobject=new undoModels(idq,z1,x,c,bal);
			}
			}
			
			
			scan.close();
			}catch(FileNotFoundException e)
			{
				
			}
			
		
			return returnobject;
		}
		public undoModels getCompleteResultsets(String currentusernumber,int id) throws SQLException
		{ 
			undoModels returnobject=new undoModels(); 
		
		try
		{
		

	    String checkid=Integer.toString(id);
	    HashMap<Integer,undoModels> accountmap = new HashMap<Integer,undoModels>(100);
		undoModels[] copyobjects = new undoModels[100];
		
		Scanner scan = new Scanner(new File("/Users/Mubarak/Documents/"+currentusernumber+".txt"));
		scan.useDelimiter(",|\n");
	   
		while(scan.hasNext())
		{
			
		String h = scan.next();
		String z1 = scan.next();
		String x = scan.next();
		String c = scan.next();
		String p=scan.next();
		int idq=Integer.parseInt(h);
		float bal=Float.parseFloat(p);
		if(h.equals(checkid))
		{
			returnobject=new undoModels(idq,z1,x,c,bal);
		}
		}
		
		
		scan.close();
		}
		catch(FileNotFoundException e)
		{
			
		}
		
	
		return returnobject;
		   
			
		}

		public undoModels getResultsetss(String accountname, int deleteid) throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}
	
}
