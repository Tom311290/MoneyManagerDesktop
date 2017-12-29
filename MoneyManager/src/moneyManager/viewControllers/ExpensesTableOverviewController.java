package moneyManager.viewControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import moneyManager.constants.AppConstants;
import moneyManager.dom.Category;
import moneyManager.dom.Currency;
import moneyManager.dom.Expense;
import moneyManager.utils.DatabaseUtil;
import moneyManager.utils.InitializerUtil;

public class ExpensesTableOverviewController implements Initializable{

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
	
	public ExpensesTableOverviewController getController(){
		return this;
	}
	
	/**
	 * Opens new window and sets initial data (ex.: double click on row opens a new window with data
	 * from that row)
	 * @param windowName
	 * @param resource
	 * @param expense
	 */
	private void openNewWindow(String windowName, String resource, Expense expense){
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
		
		Parent root = null;
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		EditExpenseController controller = setChildData(fxmlLoader, expense);		

		Scene scene = new Scene(root);		
		Stage stage = new Stage();
		stage.setTitle(windowName);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
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
		        	openNewWindow("Edit expense", AppConstants.EDIT_EXPENSE, rowData);
		        }
		    });
		    return row ;
		 });
	}	
	
	public void initializeTableExpenses(){
		
		ArrayList<Expense> listOfExpenses = new ArrayList<Expense>();		
		listOfExpenses = DatabaseUtil.fetchExpenses();

		tableExpenses.getItems().setAll(listOfExpenses);
	}
}
