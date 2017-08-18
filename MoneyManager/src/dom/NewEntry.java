package dom;

import javafx.beans.property.SimpleStringProperty;

public class NewEntry {
	
	private int id;
	private SimpleStringProperty cost;
	private SimpleStringProperty currency;
	private SimpleStringProperty category;
	private SimpleStringProperty date;
	private SimpleStringProperty note;
	
	public NewEntry(String cost, String currency, String category, String date, String note){
		this.cost = new SimpleStringProperty(cost);
		this.currency = new SimpleStringProperty(currency);
		this.category = new SimpleStringProperty(category);
		this.date = new SimpleStringProperty(date);
		this.note = new SimpleStringProperty(note);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCost() {
		return cost.getValue();
	}
	public void setCost(SimpleStringProperty cost) {
		this.cost = cost;
	}
	public String getCurrency() {
		return currency.getValue();
	}
	public void setCurrency(SimpleStringProperty currency) {
		this.currency = currency;
	}
	public String getCategory() {
		return category.getValue();
	}
	public void setCategory(SimpleStringProperty category) {
		this.category = category;
	}
	public String getDate() {
		return date.getValue();
	}
	public void setDate(SimpleStringProperty date) {
		this.date = date;
	}
	public String getNote() {
		return note.getValue();
	}
	public void setNote(SimpleStringProperty note) {
		this.note = note;
	}


}
