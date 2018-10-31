package expenseManage.expenseManage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;








@Service
public class accountManageService{
	@Autowired
	private commonServices commonServices;
	@Autowired
	undoService undoService=new undoService();
	
	static String accountName;
	
	Stack<undoModels> undo=new Stack<undoModels>();
 
	
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

	public String selectAccount(int number) throws ClassNotFoundException, SQLException
	{String returnString = null;
		try {
	
	String account = null;
	account = commonServices.checkAccountExist("userdetails",number);
	
	if(account.equals("falseoutput"))
	{
		returnString="account not found";
	}else
	{
    accountName="user_"+Integer.toString(number);
    returnString=account;
	}
	
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
	}
	
	return returnString;
    }
	

	public String bal() throws ClassNotFoundException, SQLException
	{
		float v=0;
		try 
		{
		repoMode.updatedbalances(accountName);
		
		
			v = (float) commonServices.nextbal(accountName);
		}
		catch (FileNotFoundException e) 
		{
			
			e.printStackTrace();
		}
	   return "balance of "+accountName+"is"+v;
	}

	
	public String deposit(double amount,String reason) throws ClassNotFoundException, SQLException, FileNotFoundException
	
	{
	
	String returnStatus;
	String reasons=reason;
	String mode="deposit";
	
	{
		
	int ids=commonServices.nextid(accountName)+1;
	
	if((amount)<0)
	
	{
		returnStatus="Enter a proper amount";
	
	}
	
	else
	
	{
	
	float bl=(float) (commonServices.nextbal(accountName)+(float)(amount));
	String amounts=Double.toString(amount);
	repoMode.depositInsert(accountName,ids,amounts,mode,reason,bl);
	returnStatus="deposited";
	undoModels object=new undoModels(accountName,"deposit",amounts);
	undo.push(object);
	
	}
	}
	return returnStatus;
	}
	
	public String withdraw(double amount,String reason) throws ClassNotFoundException
	{
		String ret = null;
		
		try{ String name=accountName; 
   
    String mode="withdraw";
	String reasons=reason;
	{
	int ids=commonServices.nextid(accountName)+1;
	
	if(commonServices.nextbal(name)<(amount)||(amount)<0)
	{ret="do not have enough balance";}
	else
	{
	double bl=commonServices.nextbal(name)-(amount);
	String amounts=Double.toString(amount);
	
		ret="withdraw";
		repoMode.withdrawInsert(accountName,ids,amounts,mode,reason,(float)bl);
		undoModels object=new undoModels(accountName,"withdraw",amounts);
		undo.push(object);
		
	}
	}
	}
		catch(SQLException e)
	{
		e.printStackTrace();
	} 
		catch (FileNotFoundException e) 
		{
		
		e.printStackTrace();
	}
	return ret;
	}
	
	
	public String update(int ids,double amount,String reason) throws SQLException, ClassNotFoundException
	{
	
	int id=ids;
	String amountss=Double.toString(amount);
	String amountnew=amountss;
	String reasons=reason;
	undoModels recieveObject=repoMode.getCompleteResultsets(accountName,ids);
   
	   updatemanage(id,recieveObject,accountName,amountnew,reasons);
		return "Updated ";
	}
	
	public void updatemanage(int idr,undoModels deleteobject,String accountname,String amounts,String reasons) throws SQLException, ClassNotFoundException
	{
		try
    {
			
	String amount=null;
	String mode = null;
	String reason=null;
	int id = 0;
	float bal=0;
	accountname=accountName;
	id=deleteobject.id;
	amount=deleteobject.amounts;
	mode=deleteobject.modes;
	reason=deleteobject.reasons;
    bal = deleteobject.bal;
		
	 String ids;
	undoModels s3;
	if(mode.equals("deposit"))
			 {ids = Integer.toString(id);
			  String bals = Float.toString(bal);
		      
		       repoMode.updateTable(accountName,reasons,amounts,idr);
		       repoMode.sort(accountName);
		         
		       s3 = new undoModels(ids,amount,mode,reason,"update",accountName);
		       undo.push(s3);
			  }
	 
	 else if(mode.equals("withdraw"))
	 {ids = Integer.toString(id);
	  String bals = Float.toString(bal);
      s3 = new undoModels(ids,amount,mode,reason,"update",accountname);
      undo.push(s3);
      repoMode.updateTable(accountName,reasons,amounts,id);
      repoMode.sort(accountname);
     }
	 else if(mode.indexOf("transferredfrom")==0)
	 {
	  ids = Integer.toString(id);
	  String bals = Float.toString(bal);
      s3 = new undoModels(ids,amount,mode,reason,"update",accountname);
      undo.push(s3); 
      repoMode.updateTable(accountName,reasons,amounts,id);
      repoMode.sort(accountname);
      
	 }
	 else if(mode.indexOf("transferredto")==0)
	 {
      ids = Integer.toString(id);
	  String bals = Float.toString(bal);
      s3 = new undoModels(ids,amount,mode,reason,"update",accountname);
      undo.push(s3); 
      repoMode.updateTable(accountName,reasons,amounts,id);
      repoMode.sort(accountname);
     }
		 }catch(NullPointerException e)
	{
		 
	}catch(NumberFormatException e)
	{
	}
		 repoMode.updatedbalances(accountName);
	}
	
	
	
	
	
	public  String delete(int deleteid) throws SQLException, ClassNotFoundException, IOException
	{  String returnString="deleted";
		try{
		String amount=null;
	String mode = null;
	String reason=null;
	int id = 0;
	float bal=0;
	String accountname=accountName;
	undoModels deleteobject=new undoModels();
		
	deleteobject= repoMode.getResultsetss(accountname,deleteid);
		
		id=deleteobject.id;
		amount=deleteobject.amounts;
		mode=deleteobject.modes;
		reason=deleteobject.reasons;
	    bal = deleteobject.bal;

		
	 String ids;
    undoModels s3;
	String sql;
	if(mode.equals("deposit"))
			 {ids = Integer.toString(id);
			  String bals = Float.toString(bal);
		      s3 = new undoModels(ids,amount,mode,reason,"delete",accountname);
		      undo.push(s3);
		      repoMode.deleteFromUserTable(accountname,id);
		      repoMode.sort(accountname);
		      repoMode.updatedbalances(accountname);
			  }
	 
	 else if(mode.equals("withdraw"))
	 {
		 ids = Integer.toString(id);
		  String bals = Float.toString(bal);
	      s3 = new undoModels(ids,amount,mode,reason,"delete",accountname);
	      undo.push(s3); 
	      repoMode.deleteFromUserTable(accountname,id);
	      repoMode.sort(accountname);
	      repoMode.updatedbalances(accountname);
	 }
	 //edit completly from here
	 else if(mode.indexOf("transferredfrom")==0)
	 
	 {
     ids=Integer.toString(id);
	
	 repoMode.deleteFromUserTable(accountname,deleteid);
	 repoMode.sort(accountname);
	 repoMode.updatedbalances(accountname); 
	 }
	
	 else if(mode.indexOf("transferredto")==0)
	 
	 {
		
		 
		 repoMode.deleteFromUserTable(accountname,deleteid);
	       ids=Integer.toString(id);
	       String bals=Float.toString(bal);
	  	   s3 = new undoModels(ids,amount,mode,reason,"delete",accountname);
	  	   undo.push(s3); 
	       repoMode.sort(accountname);
	       repoMode.updatedbalances(accountname);
	       
	 }}catch(NullPointerException e)
	{
		 returnString="i doesnt exist for delete";
	}
	return returnString;
	
	}

	
	public String transfer(int toaccount,Double amount,String reasons) throws  IOException, ClassNotFoundException, SQLException {
		
		String fromaccount=accountName;
		String returnString = "ss";
		String accountname=commonServices.checkAccountExist("userdetails",toaccount);
		
		if(accountname.equals("falseoutput"))
		{
			returnString ="account number entered didn't exist";
		}
		else
		{
			accountname="user_"+Integer.toString(toaccount);
		
		if(commonServices.nextbal(fromaccount)<amount||amount<0)
			{
			returnString="no balance";
			}
		else
			{
			String reason="'"+reasons+"'";
		    String name=fromaccount;
		    String mode="transferredto:"+toaccount;
		try {
		
		    int n=commonServices.nextid(name)+1;
		    String k2 = Integer.toString(n);
		    String amountss=Double.toString(amount);
		    repoMode.updatedbalances(name);
		    float bl=(float) (commonServices.nextbal(name)-amount);
		    String amounts="'"+amountss+"'";
		    repoMode.insertIntoUsertable(name,n,amountss,mode,reasons,bl);
			
		    n=commonServices.nextid(accountname)+1;
		    repoMode.updatedbalances(name);
			mode="transferredfrom";
			repoMode.updatedbalances(accountname);
			bl=(float) (commonServices.nextbal(name)+(amount));
			 
			 repoMode.insertIntoUsertable(accountname,n,amountss,mode,reasons,bl);
			 repoMode.updatedbalances(accountname);
		    undoModels undomodel= new undoModels(name,amountss,"transfer",accountname,amountss);
			undo.push(undomodel);
			String k3=Integer.toString(n);
			commonServices.storedetails(fromaccount,accountname,k2,k3);
			returnString="successful transmission";
		
		}catch (NumberFormatException e) 
		{
		     e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}catch (SQLException e)
		{
			returnString="account not exist";
		}

		}}
		return returnString;
	}	
	
	//undoOperations direct to undo class:::::::
	
	public  String undolast() throws ClassNotFoundException, IOException, SQLException {
		
		String returns="undone performed";
			
		try{
			undoModels object = undo.pop();
			if(object.b.equals("withdraw"))
			{
				undoService.undowithdrawal(object.a,object.c);
			}
			
		   else if(object.b.equals("deposit"))
		    {
			    undoService.undodeposit(object.a,object.c);    
		    }
		
		   else if (object.e.equals("delete"))
		    {
			   
		    String a1=object.a;
			String b1=object.b;
			String c1=object.c;
			String d1=object.d;
			String e1=object.e;
			String f1=object.f;
			undoService.undodelete(a1, b1, c1, d1, e1, f1);
		   }
		   else if (object.e.equals("update"))
		    
		   {
			   
			String a1=object.a;
			String b1=object.b;
			String c1=object.c;
			String d1=object.d;
			String e1=object.e;
			String f1=object.f;
			
			undoService.undoupdate(a1, b1, c1, d1, e1, f1);
			
		    }
			
		    else if(object.c.equals("transfer"))
			
		    {
	        undoService.undodeposit(object.a,object.b);
			undoService.undowithdrawal(object.d, object.e);
			}
		   else if(object.c.equals("transferdelete"))
			{	
		    undoService.undotransfer(object.a,object.b,object.c,object.d,object.f,object.g,object.h,object.i,object.i);
			}
		 
			}catch(EmptyStackException e)
			{
				returns="no more operations left to perform";
			}
			return returns;
		    }
	
	public ArrayList<viewobject> transcaction() throws ClassNotFoundException, SQLException
	{
		
			
		 repoMode.sort(accountName);
			ArrayList<viewobject> send= repoMode.selectables(accountName);
			return send;
	}
	
	
}
