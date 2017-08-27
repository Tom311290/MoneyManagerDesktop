package moneyManager.viewControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import moneyManager.utils.*;

public class MoneyManagerMainController implements Initializable {
	
//-------New entry variables------------------------------------------------
	@FXML
	public Button newEntrySaveButton;
	@FXML
	public Button newEntryCancelButton;
	@FXML
	public Button newEntryEditCurrenciesButton;
	@FXML
	public Button newEntryEditCategoriesButton;
	
	@FXML
	public TextArea newEntryNote;
	@FXML
	public ComboBox<String> currencies = new ComboBox<String>();	
	@FXML
	public ComboBox<String> categories = new ComboBox<String>();
	@FXML
	public DatePicker dateOfExpense;
	@FXML
	public TextField newEntryCost;
	
	@FXML
	public TableView<Expense> tableNewEntry;
	@FXML
	public TableColumn<Expense, String> tableNewEntryColumnCost;
	@FXML
	public TableColumn<Expense, String> tableNewEntryColumnCategory;
	@FXML
	public TableColumn<Expense, String> tableNewEntryColumnNote;
	@FXML
	public TableColumn<Expense, String> tableNewEntryColumnDate;

	ArrayList <Expense> newEntry = new ArrayList<Expense>();
//-------------------------------------------------------------------------------
	
//-------Expenses table variables------------------------------------------------
	@FXML
	public TableView<Expense> tableExpensesOverview;
	@FXML
	public TableColumn<Expense, String> tableExpensesOverviewColumnCategory;	
	@FXML
	public TableColumn<Expense, String> tableExpensesOverviewColumnCost;
	@FXML
	public TableColumn<Expense, String> tableExpensesOverviewColumnCurrency;
	@FXML
	public TableColumn<Expense, String> tableExpensesOverviewColumnExpenseDate;
	@FXML
	public TableColumn<Expense, String> tableExpensesOverviewColumnEntryDate;
	@FXML
	public TableColumn<Expense, String> tableExpensesOverviewColumnNote;
//-------------------------------------------------------------------------------
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initializeComboBox("Currencies", currencies, "Currency");
		initializeComboBox("Categories", categories, "Category");
		initializeTableNewEntry();
		initializeTableExpensesOverview();
	}

	
	@FXML
	public void saveNewEntry(){		
		try {			
			
			if(getMessages().equals("")){
				 
				Expense entry = new Expense(newEntryCost.getText(), currencies.getValue(), categories.getValue(), newEntryNote.getText(), dateOfExpense.getValue().toString());
				newEntry.add(entry);
				//filling the UI table
				tableNewEntry.getItems().setAll(newEntry);				
				
				String entryString = newEntryCost.getText() +", '" + currencies.getValue() + "', '" + categories.getValue() + "', '" + newEntryNote.getText() + "', to_date('" + dateOfExpense.getValue() + "', 'YYYY-MM-dd'), to_date('" + entry.getEntryDate()  + "', 'YYYY-MM-dd')";
				String columns = "MoneySpent, Currency, Category, Note, ExpenseDate, EntryDate";
				DatabaseUtil.insertData("Expenses", columns, entryString);
				
				initializeTableExpensesOverview();
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

	private void initializeTableExpensesOverview(){
		
		tableExpensesOverviewColumnCategory.prefWidthProperty().bind(tableExpensesOverview.widthProperty().multiply(0.2));
		tableExpensesOverviewColumnCategory.setCellValueFactory(new PropertyValueFactory<Expense, String>("Category"));
		
		tableExpensesOverviewColumnCost.prefWidthProperty().bind(tableExpensesOverview.widthProperty().multiply(0.1));
		tableExpensesOverviewColumnCost.setCellValueFactory(new PropertyValueFactory<Expense, String>("Cost"));
		
		tableExpensesOverviewColumnCurrency.prefWidthProperty().bind(tableExpensesOverview.widthProperty().multiply(0.1));
		tableExpensesOverviewColumnCurrency.setCellValueFactory(new PropertyValueFactory<Expense, String>("Currency"));
		
		tableExpensesOverviewColumnExpenseDate.prefWidthProperty().bind(tableExpensesOverview.widthProperty().multiply(0.1));
		tableExpensesOverviewColumnExpenseDate.setCellValueFactory(new PropertyValueFactory<Expense, String>("expenseDate"));
		
		tableExpensesOverviewColumnEntryDate.prefWidthProperty().bind(tableExpensesOverview.widthProperty().multiply(0.1));
		tableExpensesOverviewColumnEntryDate.setCellValueFactory(new PropertyValueFactory<Expense, String>("entryDate"));
		
		tableExpensesOverviewColumnNote.prefWidthProperty().bind(tableExpensesOverview.widthProperty().multiply(0.4));
		tableExpensesOverviewColumnNote.setCellValueFactory(new PropertyValueFactory<Expense, String>("Note"));
		
		tableExpensesOverview.getItems().setAll(initializeTableOfExpenses());
	}
	
	private ArrayList<Expense> initializeTableOfExpenses(){
		
		ArrayList<Expense> listOfExpenses = new ArrayList<Expense>();
		
		listOfExpenses = DatabaseUtil.fetchExpenses();

		return listOfExpenses;		
	}
	
	private void initializeComboBox(String tableName, ComboBox<String> comboBoxId, String... resourceColumns) {
		
		ArrayList<String> currencyList = NewEntryViewInitializers.fetchDataForComboBoxes(tableName, "Id", "null", resourceColumns);
		NewEntryViewInitializers.initializeComboBox(comboBoxId, currencyList);
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
	
	
	private void initializeTableNewEntry(){
		
		tableNewEntryColumnCost.prefWidthProperty().bind(tableNewEntry.widthProperty().multiply(0.2));
		tableNewEntryColumnCost.setCellValueFactory(new PropertyValueFactory<Expense, String>("Cost"));
		
		tableNewEntryColumnCategory.prefWidthProperty().bind(tableNewEntry.widthProperty().multiply(0.2));
		tableNewEntryColumnCategory.setCellValueFactory(new PropertyValueFactory<Expense, String>("Category"));
		
		tableNewEntryColumnNote.prefWidthProperty().bind(tableNewEntry.widthProperty().multiply(0.4));
		tableNewEntryColumnNote.setCellValueFactory(new PropertyValueFactory<Expense, String>("Note"));
		
		tableNewEntryColumnDate.prefWidthProperty().bind(tableNewEntry.widthProperty().multiply(0.2));
		tableNewEntryColumnDate.setCellValueFactory(new PropertyValueFactory<Expense, String>("Date"));
	}
	

	
	private String getMessages(){
		
		ArrayList<String> messageList = new ArrayList<String>();
		String messages = "";
		
		if(dateOfExpense.getValue() == null){
			messageList.add("Please enter day of expense!");
			dateOfExpense.setStyle(ConstantsClass.STYLE_WRONG_INPUT);
		}else{			
			dateOfExpense.setStyle("");
		}
		
		if(newEntryCost.getText().equals("")){
			messageList.add("Please enter cost of item");
			newEntryCost.setStyle(ConstantsClass.STYLE_WRONG_INPUT);
		}else{
			newEntryCost.setStyle("");
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

