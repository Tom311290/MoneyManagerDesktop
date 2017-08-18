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
			
/*			String note = newEntryNote.getText() != null ? newEntryNote.getText() : "";
			NewEntry entry = new NewEntry(newEntryCost.getText(), currencies.getValue(), categories.getValue(), dateOfExpense.getValue().toString(), newEntryNote.getText());
			newEntry.add(entry);*/

			tableNewEntry.getItems().setAll(newEntry);
			
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
	 
	public void initializeCurrencies() {
		ArrayList<String> currencyList = NewEntryViewInitializers.fetchDataForComboBoxes("Currencies", "Id", "null", "Currency");
		NewEntryViewInitializers.initializeComboBox(currencies, currencyList);
	}
	
	public void initializeCategories() {
	    ArrayList<String> categoryList = NewEntryViewInitializers.fetchDataForComboBoxes("Categories", "Id", "null", "Category");
	    NewEntryViewInitializers.initializeComboBox(categories, categoryList);
	}	

}

