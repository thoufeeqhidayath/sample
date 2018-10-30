package expenseManage.expenseManage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class undoService  {
	@Autowired
	private commonServices commonServices;
  
String filename="/Users/Mubarak/Documents/workspace/expenseManage/config.txt";
	
	accountRepository repoMode=check();

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

public String undomanagement()
{
	return null;
}

public void undowithdrawal(String accountname,String amount) throws IOException, ClassNotFoundException, SQLException

{

  int id=commonServices.nextid(accountname);
    repoMode.deleteFromUserTable(accountname,id);
     repoMode.updatebal(accountname,amount,id);
     repoMode.updatedbalances(accountname);

}

public void undodeposit(String accountname,String amount) throws IOException, ClassNotFoundException, SQLException

{
	  int id=commonServices.nextid(accountname);

	  repoMode.deleteFromUserTable(accountname,id);
	  repoMode.updatebal(accountname,amount,id);
	  repoMode.updatedbalances(accountname);
}

public void undodelete(String is1,String amount1,String modes,String reason,String z,String accountnumber) throws IOException, ClassNotFoundException, SQLException

 {
	try
	
	{
	
	String id1=is1;
	int id=Integer.parseInt(id1);
	float amounts=Float.parseFloat(amount1);
    
	String mode1=modes;
	String reason1=reason;
	float bal=100;
	String undoaccount=accountnumber;
	
	if(mode1.equals("deposit"))
	
	{
		
		   
		   bal=(float) (commonServices.nextbal(undoaccount)+amounts);
		   repoMode.insertIntoUsertable(undoaccount,id,amount1,mode1,reason1,bal);
		   repoMode.sort(undoaccount);
		   repoMode.updatedbalances(accountnumber);

	}
	
	else if((mode1.indexOf("transferredto"))==0)
	
	{

		   bal=(float) (commonServices.nextbal(undoaccount)-amounts);
		   repoMode.insertIntoUsertable(undoaccount,id,amount1,mode1,reason1,bal);
		   repoMode.sort(undoaccount);
		   repoMode.updatedbalances(accountnumber);

		
	}
	
	else if(mode1.equals("withdraw"))
		
	{
		   bal=(float) (commonServices.nextbal(undoaccount)-amounts);
		   repoMode.insertIntoUsertable(undoaccount,id,amount1,mode1,reason1,bal);
		   repoMode.sort(undoaccount);
		   repoMode.updatedbalances(accountnumber);
	    
	}
	
	else if((mode1.indexOf("transferredfrom"))==0){
	
    {
			
		   bal=(float) (commonServices.nextbal(undoaccount)+amounts);
		   repoMode.insertIntoUsertable(undoaccount,id,amount1,mode1,reason1,bal);
		   repoMode.sort(undoaccount);
		   repoMode.updatedbalances(accountnumber);
        
    }
	
	
	
	
}
	}
catch(NumberFormatException e)

{
	System.out.println(e);
}

}

public void undoupdate(String is1,String amount1,String modes,String reason,String z,String accountnumber) throws ClassNotFoundException, SQLException
{
	try{
		
		String id1=is1;
		int id=Integer.parseInt(id1);
		
		String mode1=modes;
		
		String undoaccount=accountnumber;
		
		if(mode1.equals("deposit")){
		
			repoMode.updateTable(undoaccount,reason,amount1,id);
			repoMode.sort(undoaccount);
			repoMode.updatedbalances(accountnumber);

		}
		else if(mode1.equals("withdraw"))
		{
			repoMode. updateTable(undoaccount,reason,amount1,id);
			repoMode.sort(undoaccount);
			repoMode.updatedbalances(accountnumber);
				 
		}	 else if(mode1.indexOf("transferredto:")==0)
			{
				
			repoMode.updateTable(undoaccount,reason,amount1,id);
			repoMode.sort(undoaccount);
			repoMode.updatedbalances(accountnumber);

			}
		else if((mode1.indexOf("transferredfrom"))==0){
					{
						repoMode.updateTable(undoaccount,reason,amount1,id);
						repoMode.sort(undoaccount);
						repoMode.updatedbalances(accountnumber);

					}
		
		
	}}catch(NumberFormatException e)
	{
		e.printStackTrace();
	}
}

public void undotransfer(String a, String b, String c, String d, String f, String g, String h, String i, String i2) {
	// TODO Auto-generated method stub
	
}

}
