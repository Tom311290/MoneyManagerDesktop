package constants;

public class ConstantsClass {

	//database
	static public final String H2_DRIVER = "org.h2.Driver";
	static public final String JDBC_URL = "jdbc:h2:~/";
	static public final String DATABASE_NAME = "HouseMoneyManager;DATABASE_TO_UPPER=false";
	static public final String DATABASE_USER_NAME = "admin";
	static public final String DATABASE_USER_PASSWORD = "admin";
	
	//fxml files	
	static public final String MONEY_MANAGER_LAYOUT = "MoneyManagerLayout.fxml";
	static public final String EDIT_CATEGORIES_LAYOUT = "CategoriesLayout.fxml";
	static public final String EDIT_CURRENCIES_LAYOUT = "CurrenciesLayout.fxml";
	
	//css files
	static public final String APP_CSS = "application.css";
	
	//messages
	static public final String WARNING_MESSAGE_DELETE_ITEM = "Are You sure you want to delete ";
	static public final String INFO_MESSAGE_NOT_SELECTED_FOR_DELETE = "Please select item you want to delete!";
	
	//runtime styles
/*	static public final String STYLE_INCORRECT_DATA = "-fx-background-color: red;" +
													  "-fx-background-insets: 0, 1, 2;" +
													  "-fx-background-radius: 0 6 6 6, 0 5 5 5, 0 4 4 4;";*/
	
	static public final String STYLE_WRONG_INPUT = "	-fx-effect: innershadow( three-pass-box, rgba( 255, 0, 0, 0.5 ), 10, 0, 0, 0 );";
}
