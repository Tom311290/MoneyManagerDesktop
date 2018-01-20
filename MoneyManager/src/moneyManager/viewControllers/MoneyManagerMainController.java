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
import java.util.logging.Logger;

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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DataFormat;
import javafx.stage.Modality;
import javafx.stage.Stage;
import moneyManager.constants.AppConstants;
import moneyManager.dom.Category;
import moneyManager.dom.Currency;
import moneyManager.dom.Expense;
import moneyManager.dom.ExpensesData;
import moneyManager.utils.*;

public class MoneyManagerMainController implements Initializable {
	
	private ExpensesTableOverviewController expensesTableCtrl= null;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			
	}
	
	@FXML
	public void openNewExpenseWindow (){
		openNewWindow("New expense", AppConstants.NEW_EXPENSE);
		expensesTableCtrl.initializeTableExpenses();
	}

	@FXML
	public void openEditCategoriesWindow (){
		openNewWindow("Edit categories", AppConstants.EDIT_CATEGORIES_LAYOUT);
	}
	
	@FXML
	public void openEditCurrenciesWindow (){
		openNewWindow("Edit currencies", AppConstants.EDIT_CURRENCIES_LAYOUT);
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
		
		Scene scene = new Scene(root);
		
		Stage stage = new Stage();
		stage.setTitle(windowName);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}
}

