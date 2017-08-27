package moneyManager.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import moneyManager.constants.ConstantsClass;
import moneyManager.dom.*;

public class DatabaseUtil {

	private static String jdbc = ConstantsClass.JDBC_URL + ConstantsClass.DATABASE_NAME;
	private static String databaseUserName = ConstantsClass.DATABASE_USER_NAME;
	private static String databaseUserPass = ConstantsClass.DATABASE_USER_PASSWORD;
	
	public static Connection getDBConnection(){
     
		Connection conn = null;
		
		try {
			
			Class.forName(ConstantsClass.H2_DRIVER);
			conn = DriverManager.getConnection(jdbc, databaseUserName, databaseUserPass);
			
		} catch (ClassNotFoundException e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
			
		} catch(SQLException e){
			System.out.println( e.getMessage() );
			e.printStackTrace();
		}
               
        return conn;
	}

	public static void insertData(String tableName, String columns, String values) throws SQLException{
		
		try{
			//--------------------------------------------------------------------------
			
			//QUERY STRING INIT------------------------------------------------------------------------
			String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
			//-----------------------------------------------------------------------------------------
			
			Connection conn = null;
			PreparedStatement ps = null;
			
			try{
				
				conn = getDBConnection();
				ps = conn.prepareStatement(query);
				System.out.println(query);
				ps.executeUpdate();
				
			}catch(SQLException e){
				e.printStackTrace();
				
			}catch(Exception e){
				e.printStackTrace();
				
			}finally{
				ps.close();
				conn.close();
			}
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	
	/**
	 * 
	 * @param tableName
	 * @param searchInColumnName 
	 * 				column that is used as search criteria
	 * @param searchValue
	 * 				value that we search for
	 * @param fetchColumnsList
	 * 				values that we want to fetch from result set
	 * @return ArrayLIst object that contains string data from selected columns
	 * @throws SQLException
	 */
	public static ArrayList<String> fetchData(String tableName, String searchInColumnName, String searchValue, String... fetchColumnsList) throws SQLException{
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> fetchedData = new ArrayList<String>();
		
		try{
			//QUERY STRING INIT------------------------------------------------------------------------
			String query = "SELECT * FROM " + tableName + " WHERE "+ searchInColumnName +" = ? OR ? IS NULL";
			//-----------------------------------------------------------------------------------------

			conn = getDBConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			/*
			 * instead of using if -> if it's not a number it is a string
			 * and it will throw NumberFormatException
			 */
			try{				
				long value = Integer.parseInt(searchValue);
				ps.setLong(1, value);
				
			}catch(NumberFormatException e){
				if(searchValue.equalsIgnoreCase("null")){
					ps.setNull(1, Types.INTEGER);
					ps.setNull(2, Types.INTEGER);
				}
				else{
					searchValue = "'" + searchValue + "'";
					ps.setString(1, searchValue);
					ps.setString(2, searchValue);
				}
			}
			
			System.out.print(query.replace("?", searchValue) + "\n");

			ps.executeQuery();
			rs = ps.getResultSet();
			
			while(rs.next()){ 
				for(String columnName : fetchColumnsList){
					fetchedData.add(rs.getString(columnName));
				}
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			rs.close();
			ps.close();
			conn.close();
		}
		
		return fetchedData;
	}
	
	public static ArrayList<Category> fetchCategories(){
		
		ArrayList<Category> fetchedCategories = new ArrayList<Category>();
		
		try{		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;			
			
			
			//QUERY STRING INIT------------------------------------------------------------------------
			String query = "SELECT * FROM Categories WHERE Id IS NOT NULL";
			//-----------------------------------------------------------------------------------------

			conn = getDBConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			/*
			 * instead of using if -> if it's not a number it is a string
			 * and it will throw NumberFormatException
			 */
			
			System.out.print(query + "\n");
			ps.executeQuery();
			rs = ps.getResultSet();
			
			while(rs.next()){				
				Category category = new Category();				
				category.setId(rs.getInt("Id"));
				category.setName(rs.getString("Category"));
				category.setNote(rs.getString("Note"));
				
				fetchedCategories.add(category);				
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();				
		}	
		
		return fetchedCategories;
	}
	
	public static ArrayList<Currency> fetchCurrencies(){
		
		ArrayList<Currency> fetchedCurrencies = new ArrayList<Currency>();
		
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		
		try{
			//QUERY STRING INIT------------------------------------------------------------------------
			String query = "SELECT * FROM Currencies WHERE Id IS NOT NULL";
			//-----------------------------------------------------------------------------------------

			conn = getDBConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			/*
			 * instead of using if -> if it's not a number it is a string
			 * and it will throw NumberFormatException
			 */
			
			System.out.print(query + "\n");
			ps.executeQuery();
			rs = ps.getResultSet();
			
			while(rs.next()){				
				Currency currency = new Currency();				
				currency.setId(rs.getInt("Id"));
				currency.setName(rs.getString("Currency"));
				currency.setNote(rs.getString("Note"));
				
				fetchedCurrencies.add(currency);				
			}
		
			rs.close();
			ps.close();
			conn.close();
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}	
		
		return fetchedCurrencies;
	}
	
	public static ArrayList<Expense> fetchExpenses(){
		
		ArrayList<Expense> fetchedCurrencies = new ArrayList<Expense>();
		
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;			
		
		try{
			//QUERY STRING INIT------------------------------------------------------------------------
			String query = "SELECT * FROM Expenses WHERE Id IS NOT NULL";
			//-----------------------------------------------------------------------------------------

			conn = getDBConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			/*
			 * instead of using if -> if it's not a number it is a string
			 * and it will throw NumberFormatException
			 */
			
			System.out.print(query + "\n");
			ps.executeQuery();
			rs = ps.getResultSet();
			
			while(rs.next()){				
				Expense expense = new Expense();				
				expense.setId(rs.getInt("Id"));
				expense.setCost((rs.getString("MoneySpent")));
				expense.setCurrency(rs.getString("Currency"));
				expense.setCategory(rs.getString("Category"));
				expense.setNote(rs.getString("Note"));
				expense.setExpenseDate(rs.getString("ExpenseDate").toString());
				expense.setEntryDate(rs.getString("EntryDate").toString());
				
				fetchedCurrencies.add(expense);				
			}
		
			rs.close();
			ps.close();
			conn.close();
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}	
		
		return fetchedCurrencies;
	}
	
	public static void deleteData(String tableName, String searchInColumnName, String searchValue) throws SQLException{
				
		//QUERY STRING INIT------------------------------------------------------------------------
		String query = "DELETE FROM " + tableName + " WHERE "+ searchInColumnName + " = ?";
		//-----------------------------------------------------------------------------------------
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try{
			conn = getDBConnection();
			ps = conn.prepareStatement(query);
			
			/*
			 * instead of using if -> if it's not a number it is a string
			 * and it will throw NumberFormatException
			 */
			try{				
				int value = Integer.parseInt(searchValue);
				ps.setInt(1, value);
				
			}catch(NumberFormatException e){
				searchValue = "'" + searchValue + "'";
				ps.setString(1, searchValue);				
			}
			
			System.out.println(query.replace("?", searchValue));
			ps.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			ps.close();
			conn.close();
		}
	}
	
	public static void updateData(String tableName, String searchInColumnName, String searchValue) throws SQLException{
		
		//QUERY STRING INIT------------------------------------------------------------------------
		String query = "UPDATE \"" + tableName + "\" SET  WHERE \""+ searchInColumnName +"\" = ?";
		//-----------------------------------------------------------------------------------------
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try{
			conn = getDBConnection();
			ps = conn.prepareStatement(query);
			
			/*
			 * instead of using if -> if it's not a number it is a string
			 * and it will throw NumberFormatException
			 */
			try{				
				int value = Integer.parseInt(searchValue);
				ps.setInt(1, value);
				
			}catch(NumberFormatException e){
				searchValue = "'" + searchValue + "'";
				ps.setString(1, searchValue);
				
			}
			
			System.out.println(query);
			ps.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			ps.close();
			conn.close();
		}
	}
	

}
