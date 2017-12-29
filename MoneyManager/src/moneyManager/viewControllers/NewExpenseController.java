package moneyManager.viewControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import moneyManager.constants.AppConstants;
import moneyManager.dom.Category;
import moneyManager.dom.Currency;
import moneyManager.dom.Expense;
import moneyManager.dom.ExpensesData;
import moneyManager.utils.ButtonsUtil;
import moneyManager.utils.DatabaseUtil;
import moneyManager.utils.InitializerUtil;

public class NewExpenseController implements Initializable{

	@FXML
	public Button newExpenseUpdateButton;
	@FXML
	public Button newExpenseSaveButton;
	@FXML
	public Button newExpenseDeleteButton;
	@FXML
	public Button newExpenseCancelButton;
	
	@FXML
	public TextField newExpenseCost;
	@FXML
	public TextArea newExpenseNote;	
	@FXML
	public ComboBox<Currency> newExpenseCurrencies = new ComboBox<Currency>();
	@FXML
	public ComboBox<Category> newExpenseCategories = new ComboBox<Category>();
	@FXML
	public DatePicker expenseDate;	
	
	public String expenseId;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("-------------Initializing newExpense window---------------");
		initializeCurrenciesComboBoxs(newExpenseCurrencies);
		initializeCategoriesComboBox(newExpenseCategories);
		System.out.println("----------------------------------------------------------");
		
	}
	
	private void initializeCurrenciesComboBoxs(ComboBox<Currency> comboBoxId) {
		
		ArrayList<Currency> currencyList = DatabaseUtil.fetchCurrencies();
		if(currencyList.size() == 0){
			Alert alert = new Alert(AlertType.WARNING, "Please specify at least one currency before adding new expense", ButtonType.OK);
			alert.showAndWait();
			return;
		}else{
			InitializerUtil.initializeCurrencyComboBox(comboBoxId, currencyList);
		}		
	}
	
	private void initializeCategoriesComboBox(ComboBox<Category> comboBoxId) {
		
		ArrayList<Category> currencyList = DatabaseUtil.fetchCategories();
		
		if(currencyList.size() == 0){
			Alert alert = new Alert(AlertType.WARNING, "Please specify at least one category before adding new expense", ButtonType.OK);
			alert.showAndWait();
			return;
		}else{
			InitializerUtil.initializeCategoryComboBox(comboBoxId, currencyList);
		}
	}
	
	@FXML
	public void saveNewExpense(){		
		try {			
			
			String messages = getMessages();
			if(messages.equals("")){							
				
				String entryString = newExpenseCost.getText() +", " 
									+ newExpenseCurrencies.getValue().getId() + ", " 
									+ newExpenseCategories.getValue().getId() + ", '"
									+ newExpenseNote.getText() + "', " 
									+ "to_date('" + expenseDate.getValue().toString() + "', 'YYYY-MM-dd'), "
									+ "to_date('" + LocalDate.now()  + "', 'YYYY-MM-dd'), "
									+ "false";
				
				String columns = "MoneySpent, CurrencyId, CategoryId, Note, ExpenseDate, InputDate, Hidden";
				String tableName = "Expenses";
				
				DatabaseUtil.insertData(tableName, columns, entryString);
				
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
	public void clear(){
		
		newExpenseSaveButton.setDisable(false);
		expenseId = "";		        	
		newExpenseCurrencies.getSelectionModel().select(newExpenseCurrencies.getItems().get(0));	
    	newExpenseCategories.getSelectionModel().select(newExpenseCategories.getItems().get(0));    	
    	expenseDate.setValue(null);
    	newExpenseCost.setText("");
    	newExpenseNote.setText("");
    	
    	Stage stage = (Stage) newExpenseCancelButton.getScene().getWindow();
    	stage.close();
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
		String itemCost = newExpenseCost.getText();
		if(itemCost.equals("")){
			
			messageList.add("Please enter cost of item!");
			newExpenseCost.setStyle(AppConstants.STYLE_WRONG_INPUT);
		}else{
			try{
				new Double(itemCost);
				newExpenseCost.setStyle("");
				
			}catch(NumberFormatException nfe){
				
				if(itemCost.contains(",")){
					itemCost = itemCost.replaceAll(",", ".");
					newExpenseCost.setText(itemCost);
					newExpenseCost.setStyle("");
				}
				
				try{
					new Double(itemCost);
				}catch(NumberFormatException e){
					messageList.add("Please enter a valid value number!");
					newExpenseCost.setStyle(AppConstants.STYLE_WRONG_INPUT);
				}
			}			
		}
		
		if(newExpenseCurrencies.getValue() == null){
			messageList.add("Please enter currency");
			newExpenseCurrencies.setStyle(AppConstants.STYLE_WRONG_INPUT);
		}else{
			newExpenseCurrencies.setStyle("");
		}
		
		if(newExpenseCategories.getValue() == null){
			messageList.add("Please enter category of expense");
			newExpenseCategories.setStyle(AppConstants.STYLE_WRONG_INPUT);
		}else{
			newExpenseCategories.setStyle("");
		}

		for(int i=0; i<messageList.size(); i++){
			messages = messages + messageList.get(i) + "\n";
		}

		return messages;
	}
	
	public void setnewExpenseCost(String costs){
		this.newExpenseCost.setText(costs);
	}

	public void setnewExpenseNote(String note){
		this.newExpenseNote.setText(note);
	}

	public void setnewExpenseCurrencies(Currency currency){
		this.newExpenseCurrencies.setValue(currency);
	}

	public void setnewExpenseCategories (Category category){
		this.newExpenseCategories.setValue(category);
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
