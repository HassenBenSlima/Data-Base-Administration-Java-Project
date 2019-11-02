package components.entities;

public class Table {

	private String tableName;

	public Table(String table) {
		this.tableName = table;

	}

	public Table() {
	}

	public String toString() {
		return tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
