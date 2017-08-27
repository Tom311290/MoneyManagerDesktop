package moneyManager.dom;

import java.sql.Date;
import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;


public class Expense{
	
	private int id;
	private SimpleStringProperty note;
	private SimpleStringProperty cost;
	private SimpleStringProperty currency;
	private SimpleStringProperty category;
	private SimpleStringProperty entryDate;
	private SimpleStringProperty expenseDate;
	
	public Expense(String cost, String currency, String category, String note, String expenseDate){
		this.cost = new SimpleStringProperty(cost);
		this.currency = new SimpleStringProperty(currency);
		this.category = new SimpleStringProperty(category);
		this.note = new SimpleStringProperty(note);
		this.expenseDate = new SimpleStringProperty(expenseDate);
		this.entryDate = new SimpleStringProperty(LocalDate.now().toString());
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

	public String getEntryDate() {
		return entryDate.getValue().toString();
	}

	public void setEntryDate(String date) {
		this.entryDate = new SimpleStringProperty(date);
	}

	public SimpleStringProperty getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(String expenseDate) {
		this.expenseDate = new SimpleStringProperty(expenseDate);
	}

}
