package moneyManager.viewControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DataFormat;
import javafx.stage.Stage;
import moneyManager.constants.AppConstants;
import moneyManager.dom.Category;
import moneyManager.dom.Currency;
import moneyManager.dom.Expense;
import moneyManager.dom.ExpensesData;
import moneyManager.utils.*;

public class MoneyManagerMainController implements Initializable {
		
//-------New entry variables------------------------------------------------	
	
	@FXML
	public TableView<Expense> tableExpenses;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnID;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnCost;
	@FXML
	public TableColumn<Expense, Currency> tableExpensesColumnCurrency;
	@FXML
	public TableColumn<Expense, Category> tableExpensesColumnCategory;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnNote;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnExpenseDate;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnInputDate;
	
	public long currencyId;
	public long categoryId;

//-------------------------------------------------------------------------------
	
	@SuppressWarnings("rawtypes")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		System.out.println("Initializing table Expenses-------------------");

		System.out.println("--Initializing table columns----------------\n");		
		HashMap<TableColumn, Double> columnsInfo = new HashMap<TableColumn, Double>();
		
		columnsInfo.put(tableExpensesColumnCost, 0.1);
		columnsInfo.put(tableExpensesColumnCurrency, 0.1);
		columnsInfo.put(tableExpensesColumnCategory, 0.1);
		columnsInfo.put(tableExpensesColumnNote, 0.45);
		columnsInfo.put(tableExpensesColumnExpenseDate, 0.125);	
		columnsInfo.put(tableExpensesColumnInputDate, 0.125);	

		InitializerUtil.initializeTableColumns(columnsInfo);
		
		System.out.println("--Initializing table data--------------------\n");		
		initializeTableExpenses();
		enableDoubleClickAction();
		System.out.println("-----------------------------------------------");
				
	}
	
	@FXML
	public void openNewExpenseWindow (){
		openNewWindow("New expense", AppConstants.NEW_EXPENSE);
		initializeTableExpenses();
	}

	@FXML
	public void openEditCategoriesWindow (){
		openNewWindow("Edit categories", AppConstants.EDIT_CATEGORIES_LAYOUT);
	}
	
	@FXML
	public void openEditCurrenciesWindow (){
		openNewWindow("Edit currencies", AppConstants.EDIT_CURRENCIES_LAYOUT);
	}
	
	private void openNewWindow(String windowName, String resource){
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
		
		Parent root = null;
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scene scene = new Scene(root);
		
		Stage stage = new Stage();
		stage.setTitle(windowName);
		stage.setScene(scene);
		stage.showAndWait();
	}

	/**
	 * Opens new window and sets initial data (ex.: double click on row opens a new window with data
	 * from that row)
	 * @param windowName
	 * @param resource
	 * @param expense
	 */
	private void openNewWindow2(String windowName, String resource, Expense expense){
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
		
		Parent root = null;
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EditExpenseController controller = setChildData(fxmlLoader, expense);		

		Scene scene = new Scene(root);		
		Stage stage = new Stage();
		stage.setTitle(windowName);
		stage.setScene(scene);
		
		stage.showAndWait();
		initializeTableExpenses();
	}
	
	private EditExpenseController setChildData(FXMLLoader loader, Expense expense){
		EditExpenseController controller = loader.getController();

		return controller.initializeController(expense);		
	}
	
	private void enableDoubleClickAction(){		
		
		tableExpenses.setRowFactory( tableView -> {
			
		    TableRow<Expense> row = new TableRow<>();		    
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	
		        	Expense rowData = row.getItem();		        	
		        	openNewWindow2("Edit expense", AppConstants.EDIT_EXPENSE, rowData);
		        }
		    });
		    return row ;
		 });
	}	
	
	private void initializeTableExpenses(){
		
		ArrayList<Expense> listOfExpenses = new ArrayList<Expense>();		
		listOfExpenses = DatabaseUtil.fetchExpenses();

		tableExpenses.getItems().setAll(listOfExpenses);
	}
}

