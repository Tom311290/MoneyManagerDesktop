package moneyManager.viewControllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import moneyManager.constants.AppConstants;
import moneyManager.dom.Category;
import moneyManager.dom.Currency;
import moneyManager.dom.Expense;
import moneyManager.dom.ExpensesData;
import moneyManager.utils.ButtonsUtil;
import moneyManager.utils.DatabaseUtil;
import moneyManager.utils.InitializerUtil;

public class CategoriesController implements Initializable{
	
	@FXML
	public Button updateCategoryButton;
	@FXML
	public Button addCategoryButton;
	@FXML
	public Button deleteCategoryButton;
	@FXML
    public Button cancelCategoryButton;
	
	@FXML
	public TextField addCategoryField;
	@FXML
	public TextArea addNoteField;
	
	@FXML
	public TableView<Category> tableCategories;
	@FXML
	public TableColumn<Category, String> tableCategoriesColumnCategory;
	@FXML
	public TableColumn<Category, String> tableCategoriesColumnNote;
	
	private String categoryId;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		addCategoryButton.setDisable(true);
		updateCategoryButton.setDisable(true);
		
		System.out.println("\n---------Initializing table of categories--------------------");
		System.out.println("\n------------Initializing table columns--------------------");
		HashMap<TableColumn, Double> columnsInfo = new HashMap<TableColumn, Double>();
		
		columnsInfo.put(tableCategoriesColumnCategory, 0.2);
		columnsInfo.put(tableCategoriesColumnNote, 0.8);
		
		InitializerUtil.initializeTableColumns(columnsInfo);
		System.out.println("----------------------------------------------------------");
		
		System.out.println("\n---------------Initializing table data----------------------");
		initializeTableCategories();
		System.out.println("Enable double click action: ON");
		enableDoubleClickAction();
		System.out.println("----------------------------------------------------------");
	}
	
	@FXML
	public void addCategory(){		
		
		try {
			DatabaseUtil.insertData("Categories", "Category, Note, Hidden", "'" + addCategoryField.getText() + "', '" + addNoteField.getText() + "', false");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
		//fill table with fresh data
		initializeTableCategories();
	}
	
	@FXML
	public void deleteCategory(){
		
		Category selectedData = (Category) tableCategories.getSelectionModel().getSelectedItem();
		ButtonsUtil.deleteSelectedData(tableCategories, selectedData);
		
		clear();
		initializeTableCategories();
	}
	
	@FXML
	public void updateCategory(){
		
		//update data in table
				String columnsAndValues = "Category = '" + addCategoryField.getText() + "', "
										+ "Note = '" + addNoteField.getText() + "'";										
				
				try {
					DatabaseUtil.updateData((new Category().getTableName()), "Id", categoryId, columnsAndValues);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				initializeTableCategories();
	}
	@FXML
	public void clear(){

		categoryId = "";
        addCategoryField.setText("");		            
    	addNoteField.setText("");
    	
    	addCategoryButton.setDisable(true);
    	updateCategoryButton.setDisable(true);
    	
    	Stage stage = (Stage) cancelCategoryButton.getScene().getWindow();
    	stage.close();
	}
	
	@FXML
	public void checkInput(){
		
		if(!addCategoryField.getText().equals("")){
			addCategoryButton.setDisable(false);
		}else{
			addCategoryButton.setDisable(true);
		}
	}
	
	private void initializeTableCategories(){
		
		ArrayList<Category> categoryList = new ArrayList<Category>();		
		categoryList = DatabaseUtil.fetchCategories();

		tableCategories.getItems().setAll(categoryList);
	}
	
	private void enableDoubleClickAction(){		
		
		tableCategories.setRowFactory( tableView -> {
			
		    TableRow<Category> row = new TableRow<>();		    
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	
		        	addCategoryButton.setDisable(true);
		        	updateCategoryButton.setDisable(false);
		        	
		        	Category rowData = row.getItem();
		        	categoryId = rowData.getId();
		            addCategoryField.setText(rowData.getName());		            
		        	addNoteField.setText(rowData.getNote());
		        }
		    });		    
		    return row ;
		 });
	}
}
