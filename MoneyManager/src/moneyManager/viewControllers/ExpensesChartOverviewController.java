package moneyManager.viewControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import moneyManager.dom.Category;
import moneyManager.dom.Expense;
import moneyManager.utils.DatabaseUtil;

public class ExpensesChartOverviewController implements Initializable{

	@FXML
	public PieChart pieChart;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ArrayList<Expense> expensesList = DatabaseUtil.fetchExpenses();
		ArrayList<Category> categoryList = DatabaseUtil.fetchCategories();
		HashMap<Long, Double> total = new HashMap<>();
		
		for(Category category : categoryList){
			total.put(new Long(category.getId()), 0.0);
		}
		
		for(Expense expense : expensesList){
			double sum = 0.0;
			long key = expense.getCategoryID();
			sum = total.get(key) + Double.parseDouble(expense.getCost());
			total.put(key, sum);
		}
		
		ArrayList<Data> pieChartDataList = new ArrayList<Data>();

		for(Category category : categoryList){
							
			pieChartDataList.add(new PieChart.Data(category.getName(), total.get(Long.parseLong(category.getId()))));

		}
			
		
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(pieChartDataList); 
		pieChart.setData(pieChartData);
		
	}
	
	
}
