package expenseManage.expenseManage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class accountController {
	
	@Autowired
	private accountService accountService;
	
	
	@RequestMapping(value= "/createaccount" ,method = RequestMethod.GET)
	public String createUser(@RequestParam(value="names",defaultValue="nothing")String names) throws ClassNotFoundException, SQLException, IOException
	{
		return "account created successfully for "+names+" with account id:"+accountService.createaccount(names);
	}
	
	
	@RequestMapping(value= "/deleteaccounts" ,method = RequestMethod.GET)
	public String deleteuser(@RequestParam(value="number")int number) throws ClassNotFoundException, SQLException, IOException
	{
		return "deleted account with name:"+ accountService.deleteaccount(number);
	} 
	
	@RequestMapping(value= "/viewaccounts" ,method = RequestMethod.GET)
	public  ArrayList<viewobject> viewUserAccount() throws ClassNotFoundException, SQLException, IOException
	{
		return accountService.viewAccount();
	} 
	
}
