package moneyManager.dom;

import java.sql.Date;

import javafx.beans.property.SimpleStringProperty;
import moneyManager.utils.DatabaseUtil;

public class NewEntry extends DatabaseUtil{
	
	private int id;
	private SimpleStringProperty cost;
	private SimpleStringProperty currency;
	private SimpleStringProperty category;
	private Date date;
	private SimpleStringProperty note;
	
	public NewEntry(String cost, String currency, String category, String note, Date date){
		this.cost = new SimpleStringProperty(cost);
		this.currency = new SimpleStringProperty(currency);
		this.category = new SimpleStringProperty(category);
		this.note = new SimpleStringProperty(note);
		this.date = date;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getNote() {
		return note.getValue();
	}
	public void setNote(SimpleStringProperty note) {
		this.note = note;
	}


}
