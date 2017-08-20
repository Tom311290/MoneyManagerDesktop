package viewControllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import constants.ConstantsClass;
import database.DatabaseUtil;
import dom.Category;
import dom.Currency;
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
	public TableView<Currency> tableCurrencies;
	@FXML
	public TableColumn<Currency, String> tableCurrenciesColumnCurrency;
	@FXML
	public TableColumn<Currency, String> tableCurrenciesColumnNote;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tableCurrenciesColumnCurrency.setCellValueFactory(new PropertyValueFactory<Currency, String>("Name"));
		tableCurrenciesColumnNote.setCellValueFactory(new PropertyValueFactory<Currency, String>("Note"));
		
		tableCurrencies.getItems().setAll(initializeTableCurrencies());
	}
	
	@FXML
	public void addCurrency(){		
		
		try {
			DatabaseUtil.insertStringData("Currencies", "Currency, Note", addCurrencyField.getText() + "', '" + addNoteField.getText());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
		//fill table with fresh data
		tableCurrencies.getItems().setAll(initializeTableCurrencies());
	}
	
	@FXML
	public void deleteCurrency(){
		
		if(tableCurrencies.getSelectionModel().getSelectedItem() == null){
			Alert alert = new Alert(AlertType.INFORMATION, ConstantsClass.INFO_MESSAGE_NOT_SELECTED_FOR_DELETE, ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		Currency selectedCurrency = tableCurrencies.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.WARNING, ConstantsClass.WARNING_MESSAGE_DELETE_ITEM + "currency: " + selectedCurrency.getName() + "?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		
		if (alert.getResult() == ButtonType.YES){
			
			try {
				DatabaseUtil.deleteData("Currencies", "Id", selectedCurrency.getId() + "");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//fill table with fresh data
			tableCurrencies.getItems().setAll(initializeTableCurrencies());
		}else{
			return;
		}		
	}

	public void closeWindow(){
	    Stage stage = (Stage) closeCurrencyButton.getScene().getWindow();
	    stage.close();
	}
	
	private ArrayList<Currency> initializeTableCurrencies(){
		
		ArrayList<Currency> currencyList = new ArrayList<Currency>();		
		currencyList = DatabaseUtil.fetchCurrencies();

		return currencyList;		
	}
}
