package viewControllers.viewInitializers;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseUtil;
import javafx.scene.control.ComboBox;

/**
 * 
 * @author Tomislav
 * Used for initialization of data in combo boxes, tables etc. in New Entry tab
 */
public class NewEntryViewInitializers {
	
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
		comboBox.getSelectionModel().select(list.size() != 0 ? list.get(0) : "N/A");
	}
}
