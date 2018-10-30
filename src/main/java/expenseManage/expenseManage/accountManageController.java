package expenseManage.expenseManage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class accountManageController {
	
	@Autowired
	private accountManageService accountManageService;
	@Autowired
	private commonServices commonServices;
	
	@RequestMapping(value= "/select" ,method = RequestMethod.GET)
	public String selectAccount(@RequestParam(value="number",defaultValue="00")int number) throws ClassNotFoundException, SQLException
		{
		return "Account selected "+accountManageService.selectAccount(number);
	    }
	
	
	@RequestMapping(value= "/deposit" ,method = RequestMethod.GET)
	public String deposit(@RequestParam(value="amount",defaultValue="00")double amount,@RequestParam(value="reason",defaultValue="nothing")String reason) throws ClassNotFoundException, SQLException, FileNotFoundException
    {  
		return accountManageService.deposit(amount,reason);
	}
	
	
	@RequestMapping(value= "/withdraw",method = RequestMethod.GET)
	public String withdraw(@RequestParam(value="amount",defaultValue="00")double amount,@RequestParam(value="reason",defaultValue="nothing")String reason) throws ClassNotFoundException, SQLException
    {    
	   return accountManageService.withdraw(amount,reason);
	}
	
	@RequestMapping(value= "/update" ,method = RequestMethod.GET)
	public String update(@RequestParam(value="idnumber",defaultValue="00")int idnumber,@RequestParam(value="amount",defaultValue="00")Double amount,@RequestParam(value="reason",defaultValue="nothing")String reason) throws ClassNotFoundException, SQLException
	{
		return accountManageService.update(idnumber,amount,reason);
	}
	
	
	
	@RequestMapping(value= "/delete" ,method = RequestMethod.GET)
	public String transfer(@RequestParam(value="idnumber",defaultValue="00")int idnumber) throws ClassNotFoundException, SQLException, IOException
	{
		return accountManageService.delete(idnumber);
		
	}
	
	
	@RequestMapping(value= "/transfer" ,method = RequestMethod.GET)
	public String transfertoAccount(@RequestParam(value="toaccount",defaultValue="00")int toaccount,@RequestParam(value="amount",defaultValue="00")double amount,@RequestParam(value="reason",defaultValue="nothing")String reason) throws ClassNotFoundException,SQLException, IOException
	{    
		return accountManageService.transfer(toaccount,amount,reason);
		
	}
	
	@RequestMapping(value= "/balance" ,method = RequestMethod.GET)
	public String balance() throws ClassNotFoundException, SQLException
	{     
		return "balance for the account is:"+accountManageService.bal();
		
	}
	
	@RequestMapping(value= "/undo")
	public String undo() throws ClassNotFoundException, IOException, SQLException
	{
	
		return accountManageService.undolast();
		
	}
	@RequestMapping(value= "/viewtransaction" ,method = RequestMethod.GET)
	public ArrayList<viewobject> viewtransactions() throws ClassNotFoundException, SQLException
	{
		return accountManageService.transcaction();
		
	}
	
	
	

}
