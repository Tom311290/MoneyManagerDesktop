package moneyManager.dom;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;


public class Expense extends ExpensesData{
	
	private int id;
	private final String tableName = "Expenses";
	private SimpleStringProperty note;
	private SimpleStringProperty cost;
	private SimpleStringProperty currency;
	private SimpleStringProperty category;
	private SimpleStringProperty inputDate;
	private SimpleStringProperty expenseDate;
	
	public Expense(String cost, String currency, String category, String note, String expenseDate){
		this.cost = new SimpleStringProperty(cost);
		this.currency = new SimpleStringProperty(currency);
		this.category = new SimpleStringProperty(category);
		this.note = new SimpleStringProperty(note);
		this.expenseDate = new SimpleStringProperty(expenseDate);
		this.inputDate = new SimpleStringProperty(LocalDate.now().toString());
	}

	public Expense(){
		
	}
	
	public String getCost() {
		return cost.getValue();
	}
	
	public void setCost(String cost) {
		this.cost = new SimpleStringProperty(cost);
	}
	
	public String getCurrency() {
		return currency.getValue();
	}
	
	public void setCurrency(String currency) {
		this.currency =  new SimpleStringProperty(currency);
	}
	
	public String getCategory() {
		return category.getValue();
	}
	
	public void setCategory(String category) {
		this.category = new SimpleStringProperty(category);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getNote() {
		return note.getValue();
	}

	public void setNote(String note) {
		this.note = new SimpleStringProperty(note);
	}

	public String getInputDate() {
		return inputDate.getValue().toString();
	}

	public void setInputDate(String date) {
		this.inputDate = new SimpleStringProperty(date);
	}
	
	public String getExpenseDate() {
		return expenseDate.getValue();
	}

	public void setExpenseDate(String date) {
		this.expenseDate = new SimpleStringProperty(date);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(String category) {
		// TODO Auto-generated method stub		
	}

	public String getTableName() {
		return tableName;
	}

}
