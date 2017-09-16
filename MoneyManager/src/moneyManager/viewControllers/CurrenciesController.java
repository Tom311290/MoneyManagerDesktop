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
	public Button deleteCurrencyButton;
	@FXML
	public Button closeCurrencyButton;
	
	@FXML
	public TextField addCurrencyField;
	@FXML
	public TextArea addNoteField;
	
	@FXML
	public TableView<ExpensesData> tableCurrencies;
	@FXML
	public TableColumn<Currency, String> tableCurrenciesColumnCurrency;
	@FXML
	public TableColumn<Currency, String> tableCurrenciesColumnNote;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		addCurrencyButton.setDisable(true);
		
		System.out.println("\n------------Initializing table columns--------------------");
		HashMap<TableColumn, Double> columnsInfo = new HashMap<TableColumn, Double>();
		
		columnsInfo.put(tableCurrenciesColumnCurrency, 0.2);
		columnsInfo.put(tableCurrenciesColumnNote, 0.8);
		
		InitializerUtil.initializeTableColumns(columnsInfo);
		System.out.println("----------------------------------------------------------");
		
		System.out.println("\n---------------Initializing table data------------------------");

		//InitializerUtil.initializeTableData();
		
		System.out.println("----------------------------------------------------------");
		
		//tableCurrencies.getItems().setAll(InitializerUtil.fetchTableData(new Currency()));
	}
	
	@FXML
	public void addCurrency(){		
		
		try {
			if(addCurrencyField.getText().equals("")){
				Alert alert = new Alert(AlertType.INFORMATION, "Please enter currency", ButtonType.OK);
				addCurrencyField.setStyle(ConstantsClass.STYLE_WRONG_INPUT);
				alert.showAndWait();
				return;
			}else{
				DatabaseUtil.insertData("Currencies", "Currency, Note", "'" + addCurrencyField.getText() + "', '" + addNoteField.getText() + "'");
				addCurrencyField.setStyle("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
		//fill table with fresh data
		//tableCurrencies.getItems().setAll(initializeTableCurrencies());
	}
	
	@FXML
	public void deleteCurrency(){
		
		Currency selectedData = (Currency) tableCurrencies.getSelectionModel().getSelectedItem();
		ButtonsUtil.deleteSelectedData(tableCurrencies, selectedData);	
	}

	@FXML
	public void checkInput(){
		
		if(!addCurrencyField.getText().equals("")){
			addCurrencyButton.setDisable(false);
		}else{
			addCurrencyButton.setDisable(true);
		}
	}
	
	public void closeWindow(){
	    Stage stage = (Stage) closeCurrencyButton.getScene().getWindow();
	    stage.close();
	}
}
