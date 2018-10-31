package expenseManage.expenseManage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface accountRepository {
	public undoModels getCompleteResultsets(String currentusernumber,int id) throws SQLException;
	
	public void insertIntoBasicTable(int id,String username) throws SQLException, IOException;
	
	public void createUserTable(String username) throws SQLException, IOException;
	
	public void depositInsert(String accountname,int id,String amount,String mode,String reason,float bal) throws SQLException;
	
	public int readMaxOfid(String accountname) throws SQLException, FileNotFoundException ;
	
	public void insertIntoUsertable(String accountname,int id,String amount,String mode,String reason,float bal) throws SQLException;
	
	public int readMaxOfids(String accountname) throws SQLException, FileNotFoundException ;
	
	public void withdrawInsert(String accountname,int id,String amount,String mode,String reason,float bal) throws SQLException;
	
	public double getBalance(String accountname,int accountid) throws SQLException, FileNotFoundException;
	
	public String readNameOfAccount(String tablename,int accountid) throws SQLException;
	
	public void dropUserTable(String tablename) throws SQLException;
	
	public void deleteFromUserTable(String accountname,int id) throws SQLException;
	
	public void deleteFromUserTable(String accountname) throws SQLException;
	
	public void updateTable(String tablename,float updbals,int id) throws SQLException;
	 
    public void updateTable(String accountname,String reason,String amounts,int id) throws SQLException;
	  
	public void updatebal(String accountname,String amounts,int id) throws SQLException;
	  
	public  ArrayList<viewobject> selectables(String tablename) throws SQLException, ClassNotFoundException;
	  
	public  void sort(String accountname) throws SQLException, ClassNotFoundException;
	
	public void updatedbalances(String tablename) throws SQLException, ClassNotFoundException;
	
	public undoModels getResultsets(String tablename,int id) throws SQLException;

	public undoModels getResultsetss(String accountname, int deleteid) throws SQLException;

	public ArrayList<viewobject> selectAccounts() throws SQLException;
}
