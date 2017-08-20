package viewControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import constants.ConstantsClass;
import dom.NewEntry;
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
import viewControllers.viewInitializers.NewEntryViewInitializers;

public class MoneyManagerMainController implements Initializable {
	
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
	public TableView<NewEntry> tableNewEntry;
	@FXML
	public TableColumn<NewEntry, String> tableNewEntryColumnCost;
	@FXML
	public TableColumn<NewEntry, String> tableNewEntryColumnCategory;
	@FXML
	public TableColumn<NewEntry, String> tableNewEntryColumnNote;

	ArrayList <NewEntry> newEntry = new ArrayList<NewEntry>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initializeComboBox("Currencies", currencies, "Currency");
		initializeComboBox("Categories", categories, "Category");
		
		tableNewEntryColumnCost.setCellValueFactory(new PropertyValueFactory<NewEntry, String>("Cost"));
		tableNewEntryColumnCategory.setCellValueFactory(new PropertyValueFactory<NewEntry, String>("Category"));
		tableNewEntryColumnNote.setCellValueFactory(new PropertyValueFactory<NewEntry, String>("Note"));
	}

	@FXML
	public void saveNewEntry(){		
		try {			
			
			if(getMessages().equals("")){				
				NewEntry entry = new NewEntry(newEntryCost.getText(), currencies.getValue(), categories.getValue(), dateOfExpense.getValue().toString(), newEntryNote.getText());
				newEntry.add(entry);
				tableNewEntry.getItems().setAll(newEntry);
				
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

