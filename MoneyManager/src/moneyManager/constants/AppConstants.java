package moneyManager.constants;

public class AppConstants {

	//database
	static public final String H2_DRIVER = "org.h2.Driver";
	static public final String JDBC_URL = "jdbc:h2:~/";
	static public final String DATABASE_NAME = "HouseMoneyManager;DATABASE_TO_UPPER=false";
	//static public final String DATABASE_NAME = "HouseMoneyManager-prod;DATABASE_TO_UPPER=false";
	static public final String DATABASE_USER_NAME = "admin";
	static public final String DATABASE_USER_PASSWORD = "admin";
	
	static public final boolean LOG_SQL = false;
	//fxml files	
	static public final String MONEY_MANAGER_LAYOUT = "MoneyManagerLayout.fxml";
	static public final String EDIT_CATEGORIES_LAYOUT = "CategoriesLayout.fxml";
	static public final String EDIT_CURRENCIES_LAYOUT = "CurrenciesLayout.fxml";
	static public final String EDIT_EXPENSE = "EditExpense.fxml";
	static public final String NEW_EXPENSE = "NewExpense.fxml";
	static public final String EXPENSES_TABLE_OVERVIEW = "ExpensesTableOverview.fxml";

	//css files
	static public final String APP_CSS = "application.css";
	
	//messages
	static public final String WARNING_MESSAGE_DELETE_ITEM = "Are You sure you want to delete selected item?";
	static public final String INFO_MESSAGE_NOT_SELECTED_FOR_DELETE = "Please select item you want to delete!";
	
	//runtime styles
	static public final String STYLE_WRONG_INPUT = "-fx-effect: innershadow( three-pass-box, rgba( 255, 0, 0, 0.5 ), 10, 0, 0, 0 );";
}
