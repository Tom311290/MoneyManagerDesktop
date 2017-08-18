package viewControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import constants.ConstantsClass;
import dom.NewEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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

		initializeCurrencies();
		initializeCategories();
		
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
		
		FXMLLoader categoriesLoader = new FXMLLoader(getClass().getResource(ConstantsClass.EDIT_CATEGORIES_LAYOUT));
		
		Parent root = categoriesLoader.load();
		Scene scene = new Scene(root, 800, 500);
		scene.getStylesheets().add(getClass().getResource(ConstantsClass.APP_CSS).toExternalForm());
		
		Stage editCategories = new Stage();
		editCategories.setTitle("Edit categories");
		editCategories.setScene(scene);
		editCategories.showAndWait();
		
		initializeCategories();
    }
	 
	 @FXML
	 private void openEditCurrenciesWindow(ActionEvent event) throws IOException{
		
		FXMLLoader categoriesLoader = new FXMLLoader(getClass().getResource(ConstantsClass.EDIT_CURRENCIES_LAYOUT));
		
		Parent root = categoriesLoader.load();
		Scene scene = new Scene(root, 800, 500);
		scene.getStylesheets().add(getClass().getResource(ConstantsClass.APP_CSS).toExternalForm());
		
		Stage editCategories = new Stage();
		editCategories.setTitle("Edit currencies");
		editCategories.setScene(scene);
		editCategories.showAndWait();
		
		initializeCurrencies();
    }
	 
	 private void initializeCurrencies() {
		ArrayList<String> currencyList = NewEntryViewInitializers.fetchDataForComboBoxes("Currencies", "Id", "null", "Currency");
		NewEntryViewInitializers.initializeComboBox(currencies, currencyList);
	}
	
	private void initializeCategories() {
	    ArrayList<String> categoryList = NewEntryViewInitializers.fetchDataForComboBoxes("Categories", "Id", "null", "Category");
	    NewEntryViewInitializers.initializeComboBox(categories, categoryList);
	}	

	private String getMessages(){
		
		ArrayList<String> messageList = new ArrayList<String>();
		String messages = "";
		
		if(dateOfExpense.getValue() == null){
			messageList.add("Please enter day of expense!");
			dateOfExpense.setStyle("-fx-background-color: red;" +
								    "-fx-background-insets: 0, 1, 2;" +
								    "-fx-background-radius: 0 6 6 6, 0 5 5 5, 0 4 4 4;");
		}else{
			dateOfExpense.setStyle("");
		}
		
		if(newEntryCost.getText().equals("")){
			messageList.add("Please enter cost of item");
			newEntryCost.setStyle("-fx-background-color: red;" +
				    "-fx-background-insets: 0, 1, 2;" +
				    "-fx-background-radius: 0 6 6 6, 0 5 5 5, 0 4 4 4;");
		}else{
			newEntryCost.setStyle("");
		}
		
		if(currencies.getValue().equalsIgnoreCase("n/a")){
			messageList.add("Please enter currency");
			currencies.setStyle("-fx-background-color: red;" +
				    "-fx-background-insets: 0, 1, 2;" +
				    "-fx-background-radius: 0 6 6 6, 0 5 5 5, 0 4 4 4;");
		}else{
			currencies.setStyle("");
		}
		
		if(categories.getValue().equalsIgnoreCase("n/a")){
			messageList.add("Please enter category of expense");
			categories.setStyle("-fx-background-color: red;" +
				    "-fx-background-insets: 0, 1, 2;" +
				    "-fx-background-radius: 0 6 6 6, 0 5 5 5, 0 4 4 4;");
		}else{
			categories.setStyle("");
		}

		for(int i=0; i<messageList.size(); i++){
			messages = messages + messageList.get(i) + "\n";
		}

		return messages;
	}
	
}

