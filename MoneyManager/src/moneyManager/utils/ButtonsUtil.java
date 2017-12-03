package moneyManager.utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import moneyManager.constants.AppConstants;
import moneyManager.dom.Currency;
import moneyManager.dom.Expense;
import moneyManager.dom.ExpensesData;

public class ButtonsUtil {

	/**
	 * Deletes data logically by turning on flag "hidden"
	 * @param table
	 * @param selectedData
	 */
	public static void deleteSelectedData(TableView table, ExpensesData selectedData){
		
		if(table.getSelectionModel().getSelectedItem() == null){
			Alert alert = new Alert(AlertType.INFORMATION, AppConstants.INFO_MESSAGE_NOT_SELECTED_FOR_DELETE, ButtonType.OK);
			alert.showAndWait();
			return;
		}		
		
		Alert alert = new Alert(AlertType.WARNING, AppConstants.WARNING_MESSAGE_DELETE_ITEM, ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		
		if (alert.getResult() == ButtonType.YES){
			
			try {
				System.out.println("\n--Deleting data from table \"" + selectedData.getTableName() + "\"");
				DatabaseUtil.updateData(selectedData.getTableName(), "Id", selectedData.getId() + "", "Hidden = true");
				System.out.println("--------------------------------------------------------------------");

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//fill table with fresh data
		}else{
			return;
		}		
	}	
}
