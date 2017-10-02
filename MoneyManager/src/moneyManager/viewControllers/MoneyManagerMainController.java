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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DataFormat;
import javafx.stage.Stage;
import moneyManager.constants.ConstantsClass;
import moneyManager.dom.Category;
import moneyManager.dom.Expense;
import moneyManager.dom.ExpensesData;
import moneyManager.utils.*;

public class MoneyManagerMainController implements Initializable {
	
//-------New entry variables------------------------------------------------
	@FXML
	public Button newInputUpdateButton;
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
	public TextField newInputCost;
	@FXML
	public TextArea newInputNote;
	@FXML
	public ComboBox<String> newInputCurrencies = new ComboBox<String>();	
	@FXML
	public ComboBox<String> newInputCategories = new ComboBox<String>();
	@FXML
	public DatePicker expenseDate;	
	
	@FXML
	public TableView<Expense> tableExpenses;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnID;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnCost;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnCurrency;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnCategory;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnNote;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnExpenseDate;
	@FXML
	public TableColumn<Expense, String> tableExpensesColumnInputDate;
	
	public String expenseId;

//-------------------------------------------------------------------------------
	
	@SuppressWarnings("rawtypes")
	@Override
	public void initialize(URL location, ResourceBundle resources) {		

		System.out.println("----------Initializing table comobo boxes-----------------");
		initializeComboBox("Currencies", newInputCurrencies, "Currency");
		initializeComboBox("Categories", newInputCategories, "Category");
		System.out.println("----------------------------------------------------------");

		System.out.println("\n------------Initializing table columns--------------------");		
		HashMap<TableColumn, Double> columnsInfo = new HashMap<TableColumn, Double>();
		
		columnsInfo.put(tableExpensesColumnCost, 0.1);
		columnsInfo.put(tableExpensesColumnCurrency, 0.1);
		columnsInfo.put(tableExpensesColumnCategory, 0.1);
		columnsInfo.put(tableExpensesColumnNote, 0.45);
		columnsInfo.put(tableExpensesColumnExpenseDate, 0.125);	
		columnsInfo.put(tableExpensesColumnInputDate, 0.125);	

		InitializerUtil.initializeTableColumns(columnsInfo);		
		System.out.println("----------------------------------------------------------");
		
		System.out.println("\n--------------Initializing table data-----------------");
		initializeTableExpenses();
		System.out.println("----------------------------------------------------------");
		System.out.println("\n--------------Initializing input fields-----------------");
		newInputUpdateButton.setDisable(true);
	    System.out.println("Enable double click action: ON");
		enableDoubleClickAction();
		System.out.println("----------------------------------------------------------");
				
	}

	
	@FXML
	public void saveNewInput(){		
		try {			
			
			if(getMessages().equals("")){							
				
				String entryString = newInputCost.getText() +", '" 
									+ newInputCurrencies.getValue() + "', '" 
									+ newInputCategories.getValue() + "', '" 
									+ newInputNote.getText() + "', " 
									+ "to_date('" + expenseDate.getValue().toString() + "', 'YYYY-MM-dd'), "
									+ "to_date('" + LocalDate.now()  + "', 'YYYY-MM-dd')";
				
				String columns = "MoneySpent, Currency, Category, Note, ExpenseDate, InputDate";
				String tableName = "Expenses";
				
				DatabaseUtil.insertData(tableName, columns, entryString);				
				initializeTableExpenses();
				
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
		initializeComboBox("Categories", newInputCategories, "Category");
    }
	 
	 @FXML
	 private void openEditCurrenciesWindow(ActionEvent event) throws IOException{
		
		openNewWindow("Edit currencies", ConstantsClass.EDIT_CURRENCIES_LAYOUT);		
		initializeComboBox("Currencies", newInputCurrencies, "Currency");
    }
	
	@FXML
	public void deleteExpense(){
		
		Expense selectedData = (Expense) tableExpenses.getSelectionModel().getSelectedItem();
		ButtonsUtil.deleteSelectedData(tableExpenses, selectedData);
		
		clear();
		initializeTableExpenses();

	}
	
	@FXML
	public void updateExpense(){
		
		//update data in table
		String columnsAndValues = "MoneySpent = " + newInputCost.getText() + ", "
								+ "Currency = '" + newInputCurrencies.getSelectionModel().getSelectedItem() + "', "
								+ "Category = '" + newInputCategories.getSelectionModel().getSelectedItem() + "', "
								+ "Note = '" + newInputNote.getText() + "', "
								+ "ExpenseDate = to_date('" + expenseDate.getValue() + "', 'YYYY-MM-dd')" + ", "
								+ "InputDate = to_date('" + LocalDate.now()  + "', 'YYYY-MM-dd')";
		
		try {
			DatabaseUtil.updateData((new Expense().getTableName()), "Id", expenseId, columnsAndValues);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		initializeTableExpenses();
	}
	
	@FXML
	public void clear(){
		
		newInputSaveButton.setDisable(false);
		expenseId = "";		        	
		newInputCurrencies.getSelectionModel().select(newInputCurrencies.getItems().get(0));	
    	newInputCategories.getSelectionModel().select(newInputCategories.getItems().get(0));    	
    	expenseDate.setValue(null);
    	newInputCost.setText("");
    	newInputNote.setText("");
    	
    	newInputUpdateButton.setDisable(true);
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
		
		Scene scene = new Scene(root, 800, 600);
		
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
				messageList.add("Please enter a valid value number! HINT: use decimal point \".\"");
				newInputCost.setStyle(ConstantsClass.STYLE_WRONG_INPUT);
			}			
		}
		
		if(newInputCurrencies.getValue().equalsIgnoreCase("n/a")){
			messageList.add("Please enter currency");
			newInputCurrencies.setStyle(ConstantsClass.STYLE_WRONG_INPUT);
		}else{
			newInputCurrencies.setStyle("");
		}
		
		if(newInputCategories.getValue().equalsIgnoreCase("n/a")){
			messageList.add("Please enter category of expense");
			newInputCategories.setStyle(ConstantsClass.STYLE_WRONG_INPUT);
		}else{
			newInputCategories.setStyle("");
		}

		for(int i=0; i<messageList.size(); i++){
			messages = messages + messageList.get(i) + "\n";
		}

		return messages;
	}
	
	private void enableDoubleClickAction(){		
		
		tableExpenses.setRowFactory( tableView -> {
			
		    TableRow<Expense> row = new TableRow<>();		    
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	
		        	newInputSaveButton.setDisable(true);
		        	newInputUpdateButton.setDisable(false);
		        	Expense rowData = row.getItem();
		        	
		        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		            LocalDate date = LocalDate.parse(rowData.getExpenseDate(), formatter);
		        	
		        	expenseId = rowData.getId();		        	
		        	newInputCurrencies.getSelectionModel().select(rowData.getCurrency());	
		        	newInputCategories.getSelectionModel().select(rowData.getCategory());		        	
		        	expenseDate.setValue(date);
		        	newInputCost.setText(rowData.getCost());
		        	newInputNote.setText(rowData.getNote());
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

