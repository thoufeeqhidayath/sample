package expenseManage.expenseManage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
@Service
public class commonServices  {
  

String filename="/Users/Mubarak/Documents/workspace/expenseManage/config.txt";
	
accountRepository object=check();

public accountRepository check()
{accountRepository object=new accountDBRepository();;
	Scanner accnumget;
	try {
		accnumget = new Scanner(new File(filename));
	
	accnumget.useDelimiter(" |\n");
	int currentnumber;
	currentnumber=Integer.parseInt(accnumget.next());
	if(currentnumber==1)
	{
		object=new accountDBRepository();
	}else if(currentnumber==2)
	{
		object=new accountFilerepository();
	}
	else
	{
		object=new accountFilerepository();
	}
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
	}
	return object;
	
}

	
	

    
	public  int nextids(String username) throws ClassNotFoundException, SQLException, FileNotFoundException
		{
		int value =object.readMaxOfids(username);
		return value;
		}
	
	public  int nextid(String username) throws ClassNotFoundException, SQLException, FileNotFoundException
	
	{
	int value =object.readMaxOfid(username);
	return value;
	}
	 
	public  String checkAccountExist(String tablename,int accountid) throws ClassNotFoundException, SQLException, FileNotFoundException
		
	{
		String returnStatus=object.readNameOfAccount(tablename,accountid);
	    return returnStatus;
	    
	}

	  
	
	public  double nextbal(String accountname) throws ClassNotFoundException, SQLException, FileNotFoundException
		
	   {
			
			
			int values = nextid(accountname);
			double value = object.getBalance(accountname,values);
		    return value;
			
		}
	
	
	  
	
	
	
	 public  void storedetails(String currentusernumber1,String currentusernumber2,String iddet1,String iddet2) throws IOException
		{      
		       String filename = "/Users/Mubarak/Documents/transferdetails1.txt";
		       String account1=currentusernumber1;
		       String account2=currentusernumber2;
		       String id1=iddet1;
		       String id2=iddet2; 
		       FileWriter f1 = new FileWriter(filename,true);
			   BufferedWriter b1 = new BufferedWriter(f1);
			   b1.write(account1+","+id1+","+account2+","+id2+"\n");
			   b1.close();
		 }
}
