package moneyManager.utils;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import moneyManager.constants.ConstantsClass;
import moneyManager.dom.Currency;
import moneyManager.dom.ExpensesData;

public class ButtonsUtil {

	public static void deleteSelectedData(TableView table, ExpensesData selectedData){
		
		if(table.getSelectionModel().getSelectedItem() == null){
			Alert alert = new Alert(AlertType.INFORMATION, ConstantsClass.INFO_MESSAGE_NOT_SELECTED_FOR_DELETE, ButtonType.OK);
			alert.showAndWait();
			return;
		}		
		
		Alert alert = new Alert(AlertType.WARNING, ConstantsClass.WARNING_MESSAGE_DELETE_ITEM, ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		
		if (alert.getResult() == ButtonType.YES){
			
			try {
				System.out.println("\n----------Deleting data from table \"" + selectedData.getTableName() + "\"-----------------");
				DatabaseUtil.deleteData(selectedData.getTableName(), "Id", selectedData.getId() + "");
				System.out.println("--------------------------------------------------------------------");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//fill table with fresh data
		}else{
			return;
		}		
	}
}
