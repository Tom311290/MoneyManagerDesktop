package moneyManager.viewControllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import moneyManager.constants.ConstantsClass;
import moneyManager.dom.Category;
import moneyManager.dom.Currency;
import moneyManager.dom.Expense;
import moneyManager.dom.ExpensesData;
import moneyManager.utils.ButtonsUtil;
import moneyManager.utils.DatabaseUtil;
import moneyManager.utils.InitializerUtil;

public class CurrenciesController implements Initializable{
	
	@FXML
	public Button addCurrencyButton;
	@FXML
	public Button updateCurrencyButton;
	@FXML
	public Button deleteCurrencyButton;
	@FXML
	public Button cancelCurrencyButton;
	
	@FXML
	public TextField addCurrencyField;
	@FXML
	public TextArea addNoteField;
	
	@FXML
	public TableView<Currency> tableCurrencies;
	@FXML
	public TableColumn<Currency, String> tableCurrenciesColumnCurrency;
	@FXML
	public TableColumn<Currency, String> tableCurrenciesColumnNote;
		
	private String currencyId;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		addCurrencyButton.setDisable(true);
		updateCurrencyButton.setDisable(true);
		
		System.out.println("\n----------Initializing table Currencies-------------------");
		System.out.println("\n------------Initializing table columns--------------------");
		HashMap<TableColumn, Double> columnsInfo = new HashMap<TableColumn, Double>();
		
		columnsInfo.put(tableCurrenciesColumnCurrency, 0.2);
		columnsInfo.put(tableCurrenciesColumnNote, 0.8);
		
		InitializerUtil.initializeTableColumns(columnsInfo);
		System.out.println("----------------------------------------------------------");
		
		System.out.println("\n---------------Initializing table data----------------------");
		initializeTableCurrencies();
		enableDoubleClickAction();
		System.out.println("----------------------------------------------------------");

	}
	
	@FXML
	public void addCurrency(){		
		
		try {			
			DatabaseUtil.insertData("Currencies", "Currency, Note", "'" + addCurrencyField.getText() + "', '" + addNoteField.getText() + "'");			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
		//fill table with fresh data
		initializeTableCurrencies();
	}
	
	@FXML
	public void deleteCurrency(){
		
		Currency selectedData = (Currency) tableCurrencies.getSelectionModel().getSelectedItem();
		ButtonsUtil.deleteSelectedData(tableCurrencies, selectedData);

		initializeTableCurrencies();
	}

	@FXML
	public void updateCurrency(){
		
		//update data in table
		String columnsAndValues = "Currency = '" + addCurrencyField.getText() + "', "
								+ "Note = '" + addNoteField.getText() + "'";										
		
		try {
			DatabaseUtil.updateData((new Currency().getTableName()), "Id", currencyId, columnsAndValues);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		initializeTableCurrencies();
	}
	@FXML
	public void clear(){

		currencyId = "";
		addCurrencyField.setText("");		            
    	addNoteField.setText("");
    	
    	addCurrencyButton.setDisable(true);
    	updateCurrencyButton.setDisable(true);
	}
	@FXML
	public void checkInput(){
		
		if(!addCurrencyField.getText().equals("")){
			addCurrencyButton.setDisable(false);
		}else{
			addCurrencyButton.setDisable(true);
		}
	}
	
	private ArrayList<Currency> initializeTableCurrencies(){
		
		ArrayList<Currency> currencyList= new ArrayList<Currency>();		
		currencyList = DatabaseUtil.fetchCurrencies();
		
		tableCurrencies.getItems().setAll(currencyList);

		return currencyList;		
	}
	
	private void enableDoubleClickAction(){		
		
		tableCurrencies.setRowFactory( tableView -> {
			
		    TableRow<Currency> row = new TableRow<>();		    
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	
		        	addCurrencyButton.setDisable(true);
		        	updateCurrencyButton.setDisable(false);
		        	
		        	Currency rowData = row.getItem();
		        	currencyId = rowData.getId();
		            addCurrencyField.setText(rowData.getName());		            
		        	addNoteField.setText(rowData.getNote());
		        }
		    });
		    return row ;
		 });
	}
}
