package moneyManager.dom;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;


public class Expense extends ExpensesData{	
	
	private final String tableName = "Expenses";
	private SimpleStringProperty id;
	private SimpleStringProperty note;
	private SimpleStringProperty cost;
	private SimpleStringProperty currency;
	private int currencyID;
	private SimpleStringProperty category;
	private int categoryID;
	private SimpleStringProperty expenseDate;
	private SimpleStringProperty inputDate;
	
	public Expense(String cost, String currency, String category, String note, String expenseDate, int currencyID, int categoryID){
		this.cost = new SimpleStringProperty(cost);
		this.currency = new SimpleStringProperty(currency);
		this.currencyID = currencyID;
		this.category = new SimpleStringProperty(category);
		this.categoryID = categoryID;
		this.note = new SimpleStringProperty(note);
		this.expenseDate = new SimpleStringProperty(expenseDate);
		this.inputDate = new SimpleStringProperty(LocalDate.now().toString());
	}

	public Expense(){
		
	}
	
	public String toString(){
		
		String data = "--------------------------------\n";
		data += "ID: " + getId() + "\n";
		data += "Note: " + getNote() + "\n";		
		data += "Cost: " + getCost() + "\n";
		data += "Currency: " + getCurrency() + "\n";
		data += "Category: " + getCategory() + "\n";
		data += "Expense date: " + getExpenseDate() + "\n";
		data += "Input date: " + getInputDate() + "\n";
		data += "--------------------------------\n";
		
		return data;
	}
	
	public String getId() {
		return id.getValue();
	}
	
	public void setId(String id) {
		this.id = new SimpleStringProperty(id);
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

	public String getNote() {
		return note.getValue();
	}

	public void setNote(String note) {
		this.note = new SimpleStringProperty(note);
	}

	public String getInputDate() {
		return inputDate.getValue();
	}

	public void setInputDate(String inputDate) {
		this.inputDate = new SimpleStringProperty(inputDate);
	}
	
	public String getExpenseDate() {
		return expenseDate.getValue();
	}

	public void setExpenseDate(String expenseDate) {
		this.expenseDate = new SimpleStringProperty(expenseDate);
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

	public int getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(int currencyID) {
		this.currencyID = currencyID;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}



}
