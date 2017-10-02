package moneyManager.dom;

import javafx.beans.property.SimpleStringProperty;

public class Category extends ExpensesData{

	private final String tableName = "Categories";
	private SimpleStringProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty note;
	
	public String getId() {
		return id.getValue();
	}
	
	public void setId(String id) {
		this.id = new SimpleStringProperty(id);
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

	public String getTableName() {
		return tableName;
	}

	@Override
	public String getCost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCost(String cost) {
		// TODO Auto-generated method stub
		
	}
}
