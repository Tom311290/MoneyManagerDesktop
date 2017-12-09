package moneyManager.viewControllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import moneyManager.constants.AppConstants;
import moneyManager.dom.Category;
import moneyManager.dom.Currency;
import moneyManager.dom.Expense;
import moneyManager.utils.ButtonsUtil;
import moneyManager.utils.DatabaseUtil;
import moneyManager.utils.InitializerUtil;

public class EditExpenseController implements Initializable{
	
	@FXML
	public Button editExpenseUpdateButton;
	@FXML
	public Button editExpenseDeleteButton;
	@FXML
	public Button editExpenseCancelButton;
	@FXML
	public Button editExpenseEditCurrenciesButton;
	@FXML
	public Button editExpenseEditCategoriesButton;
	
	@FXML
	public TextField editExpenseCost;
	@FXML
	public TextArea editExpenseNote;
	@FXML
	public ComboBox<Currency> editExpenseCurrencies = new ComboBox<Currency>();
	@FXML
	public ComboBox<Category> editExpenseCategories = new ComboBox<Category>();
	@FXML
	public DatePicker expenseDate;	
	
	public String expenseId;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("Initializing newExpense window---------------");
		initializeCurrenciesComboBoxs(editExpenseCurrencies);
		initializeCategoriesComboBox(editExpenseCategories);
		System.out.println("---------------------------------------------");
		
	}
	

	public EditExpenseController initializeController(Expense expense) {
		this.setExpenseDate(expense.getExpenseDate());
		this.setExpenseId(expense.getId());
		
		Category category = new Category();
		category.setName(expense.getCategory());
		category.setId(expense.getCategoryID() +"");
		this.setEditExpenseCategories(category);
		
		Currency currency = new Currency();
		currency.setName(expense.getCurrency());
		currency.setId(expense.getCurrencyID() +"");
		this.setEditExpenseCurrencies(currency);
		//-----------------------------------------------------------------------------------
		
		this.setEditExpenseCost(expense.getCost());
		this.setEditExpenseNote(expense.getNote());
		
		return this;
	}
	
	@FXML
	public void updateExpense(){		
		try {			
			
			String messages = getMessages();
			if(messages.equals("")){							
				
				String updateValues = "MoneySpent = " + editExpenseCost.getText() + ", " +
										"CurrencyId = " + editExpenseCurrencies.getValue().getId() + ", " +
										"CategoryId = " + editExpenseCategories.getValue().getId() + ", " +
										"Note = '" + editExpenseNote.getText() + "', " +
										"ExpenseDate = to_date('" + expenseDate.getValue().toString() + "', 'YYYY-MM-dd'), " +
										"InputDate = to_date('" + LocalDate.now()  + "', 'YYYY-MM-dd'), " +
										"Hidden = false";
				
				String whereColumn = "Id";
				String whereValue = expenseId;
				String tableName = "Expenses";				
				
				DatabaseUtil.updateData(tableName, whereColumn, whereValue, updateValues);
				
				Stage stage = (Stage) editExpenseUpdateButton.getScene().getWindow();
		    	stage.close();
				
			}else{
				Alert alert = new Alert(AlertType.WARNING, messages, ButtonType.OK);
				alert.showAndWait();
				return;
			}				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void deleteExpense(){

		//update data in table
		String columnsAndValues = "Hidden = true";
		String tableName = "Expenses";
		
		try {
			DatabaseUtil.updateData(tableName, "Id", expenseId, columnsAndValues);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		Stage stage = (Stage) editExpenseCancelButton.getScene().getWindow();
    	stage.close();

	}
	
	@FXML
	public void cancel(){
		
		editExpenseUpdateButton.setDisable(false);
		expenseId = "";		        	
		editExpenseCurrencies.getSelectionModel().select(editExpenseCurrencies.getItems().get(0));	
    	editExpenseCategories.getSelectionModel().select(editExpenseCategories.getItems().get(0));    	
    	expenseDate.setValue(null);
    	editExpenseCost.setText("");
    	editExpenseNote.setText("");
    	
    	Stage stage = (Stage) editExpenseCancelButton.getScene().getWindow();
    	stage.close();
	}
	
	private void initializeCurrenciesComboBoxs(ComboBox<Currency> comboBoxId) {
		
		ArrayList<Currency> currencyList = DatabaseUtil.fetchCurrencies();
		if(currencyList.size() == 0){
			
		}
		InitializerUtil.initializeCurrencyComboBox(comboBoxId, currencyList);
	}
	
	private void initializeCategoriesComboBox(ComboBox<Category> comboBoxId) {
		
		ArrayList<Category> currencyList = DatabaseUtil.fetchCategories();
		InitializerUtil.initializeCategoryComboBox(comboBoxId, currencyList);
	}
	
	private String getMessages(){
		
		ArrayList<String> messageList = new ArrayList<String>();
		String messages = "";
		
		if(expenseDate.getValue() == null){
			messageList.add("Please enter day of expense!");
			expenseDate.setStyle(AppConstants.STYLE_WRONG_INPUT);
		}else{			
			expenseDate.setStyle("");
		}
		
		/*
		 * - if entered value contains "," instead of ".", it will be replaced with "."
		 * - if entered value contains letters, warning message will be shown
		 */
		String itemCost = editExpenseCost.getText();
		if(itemCost.equals("")){
			
			messageList.add("Please enter cost of item!");
			editExpenseCost.setStyle(AppConstants.STYLE_WRONG_INPUT);
		}else{
			try{
				new Double(itemCost);
				editExpenseCost.setStyle("");
				
			}catch(NumberFormatException nfe){
				
				if(itemCost.contains(",")){
					itemCost = itemCost.replaceAll(",", ".");
					editExpenseCost.setText(itemCost);
					editExpenseCost.setStyle("");
				}
				
				try{
					new Double(itemCost);
				}catch(NumberFormatException e){
					messageList.add("Please enter a valid value number!");
					editExpenseCost.setStyle(AppConstants.STYLE_WRONG_INPUT);
				}
			}			
		}
		
		if(editExpenseCurrencies.getValue() == null){
			messageList.add("Please enter currency");
			editExpenseCurrencies.setStyle(AppConstants.STYLE_WRONG_INPUT);
		}else{
			editExpenseCurrencies.setStyle("");
		}
		
		if(editExpenseCategories.getValue() == null){
			messageList.add("Please enter category of expense");
			editExpenseCategories.setStyle(AppConstants.STYLE_WRONG_INPUT);
		}else{
			editExpenseCategories.setStyle("");
		}

		for(int i=0; i<messageList.size(); i++){
			messages = messages + messageList.get(i) + "\n";
		}

		return messages;
	}
	
	public void setEditExpenseCost(String costs){
		this.editExpenseCost.setText(costs);
	}

	public void setEditExpenseNote(String note){
		this.editExpenseNote.setText(note);
	}

	public void setEditExpenseCurrencies(Currency currency){
		this.editExpenseCurrencies.getSelectionModel().select(currency);
		this.editExpenseCurrencies.getSelectionModel().getSelectedItem();
	}

	public void setEditExpenseCategories (Category category){
		this.editExpenseCategories.getSelectionModel().select(category);
		this.editExpenseCategories.getSelectionModel().getSelectedItem();
	}

	public void setExpenseDate(String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        
        this.expenseDate.setValue(localDate);
        
	}
	
	public void setExpenseId(String id){
		this.expenseId = id;
	}

}
