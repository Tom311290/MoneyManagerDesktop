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
	
	public static void initializeComboBox(ComboBox<String> comboBox, ArrayList<String> list){
		
		comboBox.getItems().clear();
		comboBox.getItems().addAll(list);
		
		comboBox.disableProperty().bind(Bindings.isEmpty(comboBox.getItems()));
		comboBox.getSelectionModel().select(list.size() != 0 ? list.get(0) : "N/A" );
		
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
			int indexOfWhitespace = tableColumnTitle.indexOf(" ");
			
			//deals with problem of getting column name from TableColumn node
			if(indexOfWhitespace != -1){
				tableColumnTitle = tableColumnTitle.toLowerCase()
													.replace(tableColumnTitle.charAt(indexOfWhitespace + 1), tableColumnTitle.toUpperCase().charAt(indexOfWhitespace+1))
													.replace(" ", "");
			}
			
			tableColumn.setCellValueFactory(new PropertyValueFactory (tableColumnTitle));
			
			for(TableColumn childColumn : tableColumn.getColumns()){
				childColumn.prefWidthProperty().bind(table.widthProperty().multiply(columnWidthRatio/2));
				childColumn.setCellValueFactory(new PropertyValueFactory (childColumn.getText()));
			}
		}		
	}
}
