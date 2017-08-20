package moneyManager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 

public class Test {

	public static void main(String[] args) {

		try {
			System.out.println(DatabaseUtil.fetchData("CostEntries", "CE_Id", "1", "CE_MoneySpent"));
			//System.out.println(DatabaseUtil.fetchData("Users", "UserName", "mama", "UserId", "UserFirstName", "UserSecondName", "UserPassword"));
			
			//MyDatabase.insertData("Users", "UserName, UserFirstName, UserPassword", "tata', 'Željko', 'daodaonje");
			//DatabaseUtil.deleteData("Users", "UserId", "3");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
