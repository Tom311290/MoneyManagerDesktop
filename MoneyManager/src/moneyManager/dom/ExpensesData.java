package moneyManager.dom;


public abstract class ExpensesData {
	
	public abstract int getId();
	
	public abstract void setId(int id);
	
	public abstract String getCost();
	
	public abstract void setCost(String cost);
	
	public abstract String getName();
	
	public abstract void setName(String category);

	public abstract String getNote();
	
	public abstract void setNote(String note);
	
	public abstract String getTableName();	

}
