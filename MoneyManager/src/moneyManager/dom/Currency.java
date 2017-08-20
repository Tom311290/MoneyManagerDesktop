package moneyManager.dom;

import javafx.beans.property.SimpleStringProperty;
import moneyManager.utils.DatabaseUtil;

public class Currency extends DatabaseUtil{

	private int id;
	private SimpleStringProperty name;
	private SimpleStringProperty note;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name.getValue();
	}
	
	public void setName(String category) {
		this.name = new SimpleStringProperty(category);
	}

	public String getNote() {
		return note.getValue();
	}

	public void setNote(String note) {
		this.note = new SimpleStringProperty(note);
	}
}
