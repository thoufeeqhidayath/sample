package expenseManage.expenseManage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class accountService 
  {
	@Autowired
	private commonServices commonServices;
	
	
    String filename="/Users/Mubarak/Documents/workspace/expenseManage/config.txt";
	
	accountRepository repoMode=check();

	public accountRepository check()
	{
		accountRepository object=new accountDBRepository();;
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
 public ArrayList<viewobject>  viewAccount() throws SQLException
 {
	 ArrayList<viewobject> send= repoMode.selectAccounts();
		return send;
 }
     
    
	public int createaccount(String username) throws ClassNotFoundException, SQLException, IOException
	{
	   int nextid=commonServices.nextids("userdetails")+1;
		  
	   try
		  {
			  
		    repoMode.insertIntoBasicTable(nextid,username);
			String userTableName="user_"+Integer.toString(nextid);
			repoMode.createUserTable(userTableName);
		     
		  } catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return nextid;
	}
	
	
	public  String deleteaccount(int accountnumber) throws ClassNotFoundException, SQLException, IOException 
	{String returnStatus = null;
		try{
			
			int delete=accountnumber;
			String accountname = commonServices.checkAccountExist("userdetails",delete);
			if(accountname.equals("falseoutput"))
			{
				 returnStatus= "account not exists";
				
			}else
			{
				String tablename="user_"+Integer.toString(delete);
				repoMode.dropUserTable(tablename);
				repoMode.deleteFromUserTable(accountname);
                returnStatus=accountname;
			}}
               catch(NumberFormatException e){
            	   returnStatus="Error due to numberFormatException";
            	  
            	   }
		return returnStatus;
	}
}
