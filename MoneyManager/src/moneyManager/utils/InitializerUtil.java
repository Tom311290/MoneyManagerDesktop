package moneyManager.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import moneyManager.dom.Category;
import moneyManager.dom.Currency;
import moneyManager.dom.Expense;
import moneyManager.dom.ExpensesData;
import moneyManager.utils.DatabaseUtil;

/**
 * 
 * @author Tomislav
 * Used for initialization of data in combo boxes, tables etc. in New Entry tab
 */
public class InitializerUtil {
	
	/**
	 * 
	 * @param tableName
	 * @param searchInColumnName
	 * @param searchValue
	 * @param fetchColumnsList
	 * @return list of Strings
	 */
	public static ArrayList<String> fetchDataForComboBoxes(String tableName, String searchInColumnName, String searchValue, String... fetchColumnsList){
	    
		ArrayList<String> list = null;
		try {
			list = DatabaseUtil.fetchData(tableName, searchInColumnName, searchValue, fetchColumnsList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static void initializeCurrencyComboBox(ComboBox<Currency> comboBox, ArrayList<Currency> list){
		
		comboBox.getItems().clear();
		comboBox.getItems().addAll(list);
		comboBox.setConverter(new StringConverter<Currency>(){

			@Override
			public String toString(Currency object) {				
				return object.getName();
			}

			@Override
			public Currency fromString(String string) {
				return comboBox.getItems().stream().filter(ap -> ap.getName().equals(string)).findFirst().orElse(null);
			}
			
		});
		
		comboBox.disableProperty().bind(Bindings.isEmpty(comboBox.getItems()));

		comboBox.getSelectionModel().select(list.get(0));
		comboBox.getSelectionModel().getSelectedItem();

	}
	
	public static void initializeCategoryComboBox(ComboBox<Category> comboBox, ArrayList<Category> list){
		
		comboBox.getItems().clear();
		comboBox.getItems().addAll(list);
		comboBox.setConverter(new StringConverter<Category>(){

			@Override
			public String toString(Category object) {				
				return object.getName();
			}

			@Override
			public Category fromString(String string) {
				return comboBox.getItems().stream().filter(ap -> ap.getName().equals(string)).findFirst().orElse(null);
			}
			
		});
		
		comboBox.disableProperty().bind(Bindings.isEmpty(comboBox.getItems()));
		comboBox.getSelectionModel().select(list.get(0));
		comboBox.getSelectionModel().getSelectedItem();

	}

	
	public static ArrayList<ExpensesData> initializeTableData(ExpensesData expenseObject){
		ArrayList<ExpensesData> currencyList = new ArrayList<ExpensesData>();		
		currencyList = DatabaseUtil.fetchData(expenseObject);
	
		return currencyList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void initializeTableColumns(HashMap<TableColumn, Double> columnsInfo){
		
		for (Map.Entry<TableColumn, Double> columnInfo : columnsInfo.entrySet())
		{
			TableColumn<ExpensesData, String> tableColumn = columnInfo.getKey();
			TableView<ExpensesData> table = tableColumn.getTableView();
			
			double columnWidthRatio = columnInfo.getValue();
			
			tableColumn.prefWidthProperty().bind(table.widthProperty().multiply(columnWidthRatio));
			
			String tableColumnTitle = tableColumn.getText();
			int whitespaceIndex = tableColumnTitle.indexOf(" ");
			
			//deals with problem of getting column name from TableColumn node
			if(whitespaceIndex != -1){
				tableColumnTitle = tableColumnTitle.toLowerCase()
													.replace(tableColumnTitle.charAt(whitespaceIndex + 1), tableColumnTitle.toUpperCase().charAt(whitespaceIndex+1))
													.replace(" ", "");
			}
			
			System.out.println("Initalizing column: " + tableColumnTitle + "; Column width: " + columnWidthRatio + "%");
			tableColumn.setCellValueFactory(new PropertyValueFactory (tableColumnTitle));
			
			for(TableColumn childColumn : tableColumn.getColumns()){
				childColumn.prefWidthProperty().bind(table.widthProperty().multiply(columnWidthRatio/2));
				childColumn.setCellValueFactory(new PropertyValueFactory (childColumn.getText()));
			}
		}		
	}
}
