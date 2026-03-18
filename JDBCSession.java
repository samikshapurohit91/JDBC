package JDBC;


import java.sql.*;
import java.util.List;
import java.util.*;

public class JDBCSession {
	
	public static void main(String [] args) {
		
		Connection connection=null;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			connection = DriverManager.getConnection(
				//1)DB //2)Username //3)password
				"jdbc:mysql://localhost:3306/DB_Name",
				"your_username",
				"your_password"
				);
				
			System.out.println("Connection created to the DB");
			
			Statement statement  = connection.createStatement();
			ResultSet resultSet= statement.executeQuery("Select * from user");
			
			while(resultSet.next()) {
				int id= resultSet.getInt(1);
				String username= resultSet.getString(2);
				
				System.out.println(" ID " + id +"  username "+username);
				
			}
			
			ResultSet orderresultSet= statement.executeQuery("Select * from orders");
			while(orderresultSet.next()) {
				int id= orderresultSet.getInt(1);
				int userId = orderresultSet.getInt(2);
				Double totalAmount= orderresultSet.getDouble(4);
				
				System.out.println(" ID " + id + " user id "+userId + " Total amount "+ totalAmount);
				
			}
			
			PreparedStatement pst = connection.prepareStatement("Update user set email=? where user_id=?");
			
			//list
			List<String> emailList = new ArrayList<>();
			emailList.add("first@gmail.com");
			emailList.add("second@gmail.com");
			emailList.add("third@gmail.com");
			emailList.add("fourth@example.com");
			
			
			List<Integer> userIds = new ArrayList<>();
			userIds.add(1);
			userIds.add(2);
			userIds.add(3);
			userIds.add(4);
			userIds.add(5);
			userIds.add(6);


			
			
			for(int i=0;i<emailList.size();i++) {
				pst.setString(1,emailList.get (i));
				pst.setInt(2,  userIds.get(i));
				
				pst.executeUpdate();
				
			}
			
			CallableStatement cst = connection.prepareCall("{CALL get_avg_orders2(?)}");	
			double avgAmount=0;
			cst.setDouble(1,  avgAmount);
			cst.registerOutParameter(1,Types.DECIMAL );
			cst.execute();
			System.out.println("Average amount before "+avgAmount);
			avgAmount = cst.getDouble(1);
			System.out.println("Avg amount after "+ avgAmount);
			
			//CRUD Operations :
			
			//CREATE - INSERT QUERY
			//Add a new entry in a user table
//			PreparedStatement pst2= connection.prepareStatement("INSERT INTO user Values(?,?,?,?,?,?,?)");
//			pst2.setInt(1, 6);
//			pst2.setString(2,"proteas");
//			pst2.setString(3,"aidenm@gmail.com");
//			pst2.setString(4,"aidenm77");
//			pst2.setString(5,"aiden");
//			pst2.setString(6,"markram");
//			pst2.setString(7,"875432");
//			
//		    pst2.executeUpdate();
		    
		    //READ Operation:
		    //Get All available users
		    Statement st2 = connection.createStatement();
		    ResultSet resultSet2 = st2.executeQuery("SELECT * from user");
		    
		    while(resultSet2.next()) {
		    	
					int id= resultSet2.getInt(1);
					String username= resultSet2.getString(2);
					
					System.out.println(" ID " + id +"  username "+username);
		    }
		    
		    //UPDATE Operation:
		    //update user password
		     
		    List<String> passwordList = new ArrayList<>();
		    passwordList.add("Sam1507");
		    passwordList.add("Chris898");
		    passwordList.add("dewaldb67");
		    passwordList.add("aliceg56");
		    passwordList.add("johnd34");
		    passwordList.add("aiden4");
		    
		    PreparedStatement pstUpdate= connection.prepareStatement("UPDATE user set password=? where user_id=?");
		    
		    for(int i=0;i<passwordList.size();i++) {
		    	
		    	pstUpdate.setString(1, passwordList.get(i));
		    	pstUpdate.setInt(2, userIds.get(i));
		    
		    	pstUpdate.executeUpdate();
		    	
		    }
		    
		    //DELETE Operation:
		    //delete user from user table
		    
		    PreparedStatement ps = connection.prepareStatement("DELETE FROM user where user_id=?");
		    ps.setInt(1,4);
		    ps.executeUpdate();
		    
		    
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("Testing java");
	}

}



//steps:
//1) create connection with db through DriverManager (DB , Username , password)
//2) create statement = connection.createStatement();
//3) ResultSet resultSet = statement.executeQuery("Select * from user);