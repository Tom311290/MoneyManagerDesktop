package moneyManager.viewControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import moneyManager.constants.ConstantsClass;
import moneyManager.dom.Category;
import moneyManager.dom.Expense;
import moneyManager.dom.ExpensesData;
import moneyManager.utils.*;

public class MoneyManagerMainController implements Initializable {
	
//-------New entry variables------------------------------------------------
	@FXML
	public Button newInputSaveButton;
	@FXML
	public Button newInputDeleteButton;
	@FXML
	public Button newInputCancelButton;
	@FXML
	public Button newInputEditCurrenciesButton;
	@FXML
	public Button newInputEditCategoriesButton;
	
	@FXML
	public TextArea newInputNote;
	@FXML
	public ComboBox<String> currencies = new ComboBox<String>();	
	@FXML
	public ComboBox<String> categories = new ComboBox<String>();
	@FXML
	public DatePicker expenseDate;
	@FXML
	public TextField newInputCost;
	
	@FXML
	public TableView<Expense> tableExpenses;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnCost;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnCurrency;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnCategory;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnNote;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnDate;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnExpenseDate;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnInputDate;

	private static ArrayList<Expense> listOfExpenses = new ArrayList<Expense>();
//-------------------------------------------------------------------------------
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		

		System.out.println("----------Initializing table comobo boxes-----------------");
		initializeComboBox("Currencies", currencies, "Currency");
		initializeComboBox("Categories", categories, "Category");
		System.out.println("----------------------------------------------------------");

		System.out.println("\n------------Initializing table columns--------------------");		
		HashMap<TableColumn, Double> columnsInfo = new HashMap<TableColumn, Double>();
		
		columnsInfo.put(tableExpensesColumnCost, 0.15);
		columnsInfo.put(tableExpensesColumnCurrency, 0.1);
		columnsInfo.put(tableExpensesColumnCategory, 0.1);
		columnsInfo.put(tableExpensesColumnNote, 0.4);
		columnsInfo.put(tableExpensesColumnDate, 0.25);	
		
		InitializerUtil.initializeTableColumns(columnsInfo);		
		System.out.println("----------------------------------------------------------");

		System.out.println("\n---------------Initializing table data------------------");
		listOfExpenses = DatabaseUtil.fetchExpenses();
		tableExpenses.getItems().setAll(listOfExpenses);
		System.out.println("----------------------------------------------------------");
	}

	
	@FXML
	public void saveNewInput(){		
		try {			
			
			if(getMessages().equals("")){
				 
				Expense newInput = new Expense(newInputCost.getText(), currencies.getValue(), categories.getValue(), newInputNote.getText(), expenseDate.getValue().toString());
				listOfExpenses.add(newInput);
				//filling the UI table
				tableExpenses.getItems().setAll(listOfExpenses);				
				
				String entryString = newInputCost.getText() +", '" + currencies.getValue() + "', '" + categories.getValue() + "', '" + newInputNote.getText() + "', to_date('" + expenseDate.getValue() + "', 'YYYY-MM-dd'), to_date('" + newInput.getInputDate()  + "', 'YYYY-MM-dd')";
				String columns = "MoneySpent, Currency, Category, Note, ExpenseDate, InputDate";
				String tableName = "Expenses";
				
				DatabaseUtil.insertData(tableName, columns, entryString);
				
			}else{
				Alert alert = new Alert(AlertType.INFORMATION, getMessages(), ButtonType.OK);
				alert.showAndWait();
				return;
			}				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 @FXML
	 private void openEditCategoriesWindow(ActionEvent event) throws IOException{
		
		openNewWindow("Edit categories", ConstantsClass.EDIT_CATEGORIES_LAYOUT);
		initializeComboBox("Categories", categories, "Category");
    }
	 
	 @FXML
	 private void openEditCurrenciesWindow(ActionEvent event) throws IOException{
		
		openNewWindow("Edit currencies", ConstantsClass.EDIT_CURRENCIES_LAYOUT);		
		initializeComboBox("Currencies", currencies, "Currency");
    }
	
	@FXML
	public void deleteExpense(){
		
		Expense selectedData = (Expense) tableExpenses.getSelectionModel().getSelectedItem();
		ButtonsUtil.deleteSelectedData(tableExpenses, selectedData);
		
		listOfExpenses.remove(selectedData);
		tableExpenses.getItems().setAll(listOfExpenses);

	}
	private void initializeComboBox(String tableName, ComboBox<String> comboBoxId, String... resourceColumns) {
		
		ArrayList<String> currencyList = InitializerUtil.fetchDataForComboBoxes(tableName, "Id", "null", resourceColumns);
		InitializerUtil.initializeComboBox(comboBoxId, currencyList);
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
		
		Scene scene = new Scene(root, 800, 500);
		
		Stage stage = new Stage();
		stage.setTitle(windowName);
		stage.setScene(scene);
		stage.showAndWait();
	}

	private String getMessages(){
		
		ArrayList<String> messageList = new ArrayList<String>();
		String messages = "";
		
		if(expenseDate.getValue() == null){
			messageList.add("Please enter day of expense!");
			expenseDate.setStyle(ConstantsClass.STYLE_WRONG_INPUT);
		}else{			
			expenseDate.setStyle("");
		}
		
		if(newInputCost.getText().equals("")){			
			messageList.add("Please enter cost of item!");
			newInputCost.setStyle(ConstantsClass.STYLE_WRONG_INPUT);
		}else{
			try{
				new Double(newInputCost.getText());
				newInputCost.setStyle("");
			}catch(NumberFormatException nfe){
				messageList.add("Please enter a valid number as value of item! Please use decimal point \".\"");
				newInputCost.setStyle(ConstantsClass.STYLE_WRONG_INPUT);
			}			
		}
		
		if(currencies.getValue().equalsIgnoreCase("n/a")){
			messageList.add("Please enter currency");
			currencies.setStyle(ConstantsClass.STYLE_WRONG_INPUT);
		}else{
			currencies.setStyle("");
		}
		
		if(categories.getValue().equalsIgnoreCase("n/a")){
			messageList.add("Please enter category of expense");
			categories.setStyle(ConstantsClass.STYLE_WRONG_INPUT);
		}else{
			categories.setStyle("");
		}

		for(int i=0; i<messageList.size(); i++){
			messages = messages + messageList.get(i) + "\n";
		}

		return messages;
	}
}

