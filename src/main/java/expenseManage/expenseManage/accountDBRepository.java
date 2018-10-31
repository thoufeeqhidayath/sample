package expenseManage.expenseManage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class accountDBRepository implements accountRepository{
	final static  String JDBC_DRIVER = "com.mysql.cj.jdbc.driver";  
    final static String DB_URL = "jdbc:mysql://localhost:3306/application";
    
   //  Database credentials
  final static String USER = "root";
   final static String PASS = "password";
  @Autowired
  protected static Statement stmt=null;
  @Autowired
  protected static  Connection conn=null;

	
	public Connection createConnection() throws SQLException
	{
		 conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/application?autoReconnect=true&useSSL=false&maxReconnects=10 ",USER,PASS);
		 return conn;
	}
	
	public Statement createStatement() throws SQLException
	{   
		 conn=createConnection();
         stmt = conn.createStatement();
		 return stmt;
	}
	
	public void insertIntoBasicTable(int id,String username) throws SQLException
	{
	 stmt=createStatement();
	 String sql = "INSERT INTO userdetails VALUES("+id+","+"'"+username+"'"+")";
	 stmt.execute(sql);
	}
	
	
	public void createUserTable(String username) throws SQLException
	{
		  stmt = createStatement();
	      String sql = "CREATE TABLE IF NOT EXISTS " +username+
                  " (id INTEGER not NULL, " +
                  " amount VARCHAR(255), " + 
                  " mode VARCHAR(255), " + 
                  " reason VARCHAR(255), " +
                
                  " bal FLOAT, " +
                  " PRIMARY KEY ( id ))"; 

     stmt.executeUpdate(sql);
		
	}
	
	
	
	public void depositInsert(String accountname,int id,String amount,String mode,String reason,float bal) throws SQLException
	{
		stmt=createStatement();
		String sql = "INSERT INTO "+accountname+" values("+id+","+"'"+amount+"'"+","+"'"+mode+"'"+","+"'"+reason+"'"+","+bal+");";
		stmt.execute(sql);
	}
	
	
	public int readMaxOfids(String accountname) throws SQLException 
	{
		stmt=createStatement();
		int value=0;
		String sql="SELECT MAX(id) FROM "+accountname;
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
				value=rs.getInt(1);
		         rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return value;
	}
	
	public int readMaxOfid(String accountname) throws SQLException 
	{
		stmt=createStatement();
		int value=0;
		String sql="SELECT MAX(id) FROM "+accountname;
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
				value=rs.getInt(1);
		         rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return value;
	}
	
	public void withdrawInsert(String accountname,int id,String amount,String mode,String reason,float bal) throws SQLException
	
	{
		
	stmt=createStatement();
	String sql = "INSERT INTO "+accountname+" values("+id+","+"'"+amount+"'"+","+"'"+mode+"'"+","+"'"+reason+"'"+","+bal+");";
    stmt.execute(sql);
	
	}
	
	public double getBalance(String accountname,int accountid) throws SQLException
	{
		double value = 0;
		stmt=createStatement();
		String sql="SELECT bal FROM "+accountname+" where id = "+accountid;
		try 
		{
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
				 value=rs.getFloat(1);
               rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return value;
		
	}
	
	public String readNameOfAccount(String tablename,int accountid) throws SQLException
	{
	 String returnName = "falseoutput";
	 stmt=createStatement();
	 String sql = "SELECT id, name FROM "+tablename;
	 
	 
	 ResultSet rs = stmt.executeQuery(sql);
	  
	 while(rs.next())
     	{
		  int id=rs.getInt("id");
		  String e=rs.getString("name");
		  if(id==accountid)
		  
		  {
			  
			 returnName=e;
		  
		  }
	
     	}
	return returnName;
	
	}
	
	public void dropUserTable(String tablename) throws SQLException
	{   
		stmt=createStatement();
	    stmt.executeUpdate("drop table "+tablename); 
	}
	
	public void deleteFromUserTable(String accountname,int id) throws SQLException
	{   
		 stmt=createStatement();
	     String sql = "DELETE FROM "+accountname+" WHERE id = "+id;
         stmt.executeUpdate(sql);
    }
	
	public void deleteFromUserTable(String accountname) throws SQLException
	{    stmt=createStatement();
		 String sql = "DELETE FROM userdetails " +"WHERE name = "+"'"+accountname+"'";
         stmt.executeUpdate(sql);
	}
	
	public ResultSet getCompleteResultset(String currentusernumber,int id) throws SQLException
	{      
		   stmt=createStatement();
		   String sql="SELECT * FROM "+currentusernumber+" WHERE id ="+id;
		   ResultSet rs = stmt.executeQuery(sql);
		   return rs;
	}
	
	//updated and modified >>>>>>>*****<<<<<<
	public undoModels getCompleteResultsets(String currentusernumber,int id) throws SQLException
	{  String amount=null ;
       String mode=null;
	   String reason = null;
	   float bal =0;
		
	   
	   stmt=createStatement();
	   String sql="SELECT * FROM "+currentusernumber+" WHERE id ="+id;
	   ResultSet rs = stmt.executeQuery(sql);
	   
	   while(rs.next())
		
	   {
		
		id=rs.getInt("id");
		amount=rs.getString("amount");
		mode=rs.getString("mode");
		reason=rs.getString("reason");
	    bal = rs.getFloat("bal");
		
	   }
		
	       undoModels sender=new undoModels(id,amount,mode,reason,bal);
		   return sender;
	   
		
	}
	
	public ResultSet getResultset(String tablename) throws SQLException
	{      
		   stmt=createStatement();
		   String sql = "SELECT id, amount, mode, reason,bal FROM "+tablename;
		   ResultSet  rs = stmt.executeQuery(sql);
		   return rs;
	}
	
	public ResultSet getResultset(String tablename,int id) throws SQLException
	{      
		   stmt=createStatement();
		   String sql = "SELECT id, amount, mode, reason,bal FROM "+tablename;
		   ResultSet  rs = stmt.executeQuery(sql);
			while(rs.next())
			{
			id=rs.getInt("id");
			String amount = rs.getString("amount");
			String mode = rs.getString("mode");
			String reason = rs.getString("reason");
		    float bal = rs.getFloat("bal");
			 }
		   return rs;
	}
	//new result
	public undoModels getResultsets(String tablename,int id) throws SQLException
	{     
		   String amount=null ;
	       String mode=null;
	   	   String reason = null;
	   	   float bal =0;
		   stmt=createStatement();
		   String sql = "SELECT id, amount, mode, reason,bal FROM "+tablename;
		   ResultSet  rs = stmt.executeQuery(sql);
			while(rs.next())
			{
			 id=rs.getInt("id");
			 amount = rs.getString("amount");
			 mode = rs.getString("mode");
		     reason = rs.getString("reason");
		     bal = rs.getFloat("bal");
			 }
			undoModels sender=new undoModels(id,amount,mode,reason,bal);
		   return sender;
	}
	public undoModels getResultsetss(String tablename,int id) throws SQLException
	{     
		   String amount=null ;
	       String mode=null;
	   	   String reason = null;
	   	   float bal =0;
		   stmt=createStatement();
		   String sql = "SELECT id, amount, mode, reason,bal FROM "+tablename+" WHERE id="+id;
		   ResultSet  rs = stmt.executeQuery(sql);
			while(rs.next())
			{
			 id=rs.getInt("id");
			 amount = rs.getString("amount");
			 mode = rs.getString("mode");
		     reason = rs.getString("reason");
		     bal = rs.getFloat("bal");
			 }
			undoModels sender=new undoModels(id,amount,mode,reason,bal);
		   return sender;
	}
	public void delete()
	{
		
	}
	 public void updateTable(String tablename,float updbals,int id) throws SQLException 
	 {
		 stmt=createStatement();
	   	 String sql = "UPDATE "+tablename+" SET "+"bal ="+updbals+" WHERE id = "+id;
	   	 stmt.execute(sql);
	 }
	
	
	 
	 
  public void updateTable(String accountname,String reason,String amounts,int id) throws SQLException
	{
	          stmt=createStatement();
			  String sql = "UPDATE "+accountname+" SET "+"reason = '"+reason+"' ,amount='"+amounts+"' WHERE id = "+id;
			  stmt.executeUpdate(sql);
	}
	
  public void updatebal(String accountname,String amounts,int id) throws SQLException
 	{
 	          stmt=createStatement();  
 			 String sql = "UPDATE " +accountname+" SET bal = "+amounts+" WHERE id = "+id;
 			stmt.executeUpdate(sql);
 	}
  
  public  ArrayList<viewobject> selectables(String tablename) throws SQLException, ClassNotFoundException
	
  {
		viewobject object=new viewobject();
		ArrayList<viewobject> sendArray=new ArrayList<viewobject>();
	    stmt = createStatement();

	      String sql = "SELECT id, amount, mode, reason,bal FROM "+tablename;
	      ResultSet rs = stmt.executeQuery(sql);
	       
	      while(rs.next()){
	    	  int id  = rs.getInt("id");
	         float amount = rs.getFloat("amount");
	         String mode = rs.getString("mode");
	         String reason = rs.getString("reason");
	        Float bal = rs.getFloat("bal");
	        object=new viewobject(id,amount,mode,reason,bal);
	        
	         sendArray.add(object);
	      }
	      rs.close();
		return sendArray;
	}
	
	public ResultSet sortByID(String accountname) throws SQLException
	{
		stmt=createStatement();
	    String sql="SELECT * FROM "+accountname+" ORDER BY id";
        ResultSet rs = stmt.executeQuery(sql);
	    return rs;
    }
	
	
	
	public void createTableIfNotExists(String tablename) throws SQLException
	{ 
		stmt=createStatement();
		String sql="CREATE TABLE IF NOT EXISTS " +tablename+
				" (id INTEGER not NULL, " +
				" amount VARCHAR(255), " + 
				" mode VARCHAR(255), " + 
				" reason VARCHAR(255), " +
                " bal FLOAT, " +
				" PRIMARY KEY ( id ))";;
				 stmt.executeUpdate(sql);
	}
	
  
	
  public void insertIntoUsertable(String accountname,int id,String amount,String mode,String reason,float bal) throws SQLException 
  {	  
	  stmt=createStatement();
	  String sql="INSERT INTO "+accountname+" VALUES("+id+","+"'"+amount+"'"+","+"'"+mode+"'"+","+"'"+reason+"'"+","+bal+")";
      stmt.executeUpdate(sql);
  }	
	
	public void insertIntoUsertables(String accountname,int id,String amount,String mode,String reason,float bal) throws SQLException 
	  {	  
		  stmt=createStatement();
		  String sql="INSERT INTO "+accountname+" VALUES("+id+","+amount+","+mode+","+reason+","+bal+")";
	      stmt.executeUpdate(sql);
	  }	
	
		
	
	//functions from CommonServices
	public  void sort(String accountname) throws SQLException, ClassNotFoundException
	{
	
	ResultSet rs=sortByID(accountname);
	dropUserTable(accountname);
	createTableIfNotExists(accountname);
		 
    while(rs.next())
		 {
	    	 int id  = rs.getInt("id");
	         String amount = rs.getString("amount");
	         String mode = rs.getString("mode");
	         String reason = rs.getString("reason");
	         float bal = rs.getFloat("bal");
	         insertIntoUsertable(accountname,id,amount,mode,reason,bal);
	     }
    
	      rs.close();
	}

public void updatedbalances(String tablename) throws SQLException, ClassNotFoundException
	{
		
	ResultSet rs=getResultset(tablename);
	      float updbal=0;
	      
	      while(rs.next()){
	    	  int id  = rs.getInt("id");
	         String amount = rs.getString("amount");
	         String mode = rs.getString("mode");
	         String reason = rs.getString("reason");
	         int bal = rs.getInt("bal");
	        
	         if(mode.equals("deposit"))
	         {
	         float z4=Float.parseFloat(amount);
	         updbal=updbal+z4;
	         float updbals=updbal;
            updateTable(tablename,updbals,id);
	  
	         }
	         else if(mode.equals("withdraw"))
	        {
	        	 float z4=Float.parseFloat(amount);
		         updbal=updbal-z4;
		         float updbals=updbal;
		         updateTable(tablename,updbal,id);
	        }else if((mode.indexOf("transferredfrom"))==0)
	        {float z4=Float.parseFloat(amount);
	         updbal=updbal+z4;
	         
	         updateTable(tablename,updbal,id);	
	        }else if((mode.indexOf("transferredto:"))==0)
	        {float z4=Float.parseFloat(amount);
	    updbal=updbal-z4;
	         
	    updateTable(tablename,updbal,id);
	        	
	        }else
	        {
	        }
	        }
	 }

public ArrayList<viewobject> selectAccounts() throws SQLException {
	viewobject object=new viewobject();
	ArrayList<viewobject> sendArray=new ArrayList<viewobject>();
    stmt = createStatement();

      String sql = "SELECT id,name FROM "+"userdetails";
      ResultSet rs = stmt.executeQuery(sql);
       
      while(rs.next()){
    	  int id  = rs.getInt("id");
         String name = rs.getString("name");
        
        object=new viewobject(id,name);
        
         sendArray.add(object);
      }
      rs.close();
	return sendArray;
	
	
}
	
  

}
