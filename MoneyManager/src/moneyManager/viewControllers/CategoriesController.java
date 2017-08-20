package moneyManager.viewControllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import moneyManager.constants.ConstantsClass;
import moneyManager.dom.Category;
import moneyManager.utils.DatabaseUtil;

public class CategoriesController implements Initializable{
		
	@FXML
	public Button addCategoryButton;
	@FXML
	public Button deleteCategoryButton;
	@FXML
    public Button closeCategoryButton;
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//it will be enabled if something is written in addCategoryField
		addCategoryButton.setDisable(true);
		
		tableCategoriesColumnCategory.setCellValueFactory(new PropertyValueFactory<Category, String>("Name"));
		tableCategoriesColumnNote.setCellValueFactory(new PropertyValueFactory<Category, String>("Note"));
		
		tableCategories.getItems().setAll(initializeTableCategories());
	}
	
	@FXML
	public void addCategory(){		
		
		try {
			DatabaseUtil.insertStringData("Categories", "Category, Note", addCategoryField.getText() + "', '" + addNoteField.getText());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
		//fill table with fresh data
		tableCategories.getItems().setAll(initializeTableCategories());
	}
	
	@FXML
	public void deleteCategory(){
		Category selectedCategory = tableCategories.getSelectionModel().getSelectedItem();
		
		if(tableCategories.getSelectionModel().getSelectedItem() == null){
			Alert alert = new Alert(AlertType.INFORMATION, ConstantsClass.INFO_MESSAGE_NOT_SELECTED_FOR_DELETE, ButtonType.OK);
			alert.showAndWait();
			return;
		}

		Alert alert = new Alert(AlertType.WARNING, ConstantsClass.WARNING_MESSAGE_DELETE_ITEM + "category: " + selectedCategory.getName() + "?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		
		if (alert.getResult() == ButtonType.YES){
		
			try {
				DatabaseUtil.deleteData("Categories", "Id", selectedCategory.getId() + "");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			//fill table with fresh data
			tableCategories.getItems().setAll(initializeTableCategories());
		}else{
			return;
		}
	}
	
	@FXML
	public void closeWindow(){

		Stage stage = (Stage) closeCategoryButton.getScene().getWindow();
	    stage.close();
	}
	
	private ArrayList<Category> initializeTableCategories (){
		
		ArrayList<Category> categoryList = new ArrayList<Category>();		
		categoryList = DatabaseUtil.fetchCategories();

		return categoryList;		
	}
	
	@FXML
	public void checkInput(){
		
		if(!addCategoryField.getText().equals("")){
			addCategoryButton.setDisable(false);
		}else{
			addCategoryButton.setDisable(true);
		}
	}
}
