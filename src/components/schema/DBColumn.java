package components.schema;

/**
 * @description Represents a column in the DB.
 *
 */
public class DBColumn {

	public String name;
	public String type;
	public String size;
	public boolean primaryKey;

	/**
	 * Checks if this column is the primary key for the table.
	 * 
	 * @return true if this is the primary key; otherwise false.
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	/**
	 * Initializes a new instance of the DBColumn class
	 * 
	 * @param _name
	 *            The name of the DB column.
	 * @param _type
	 *            The type of data stored in this column.
	 * @param _size
	 *            The max size of data that could be stored in this column.
	 */
	public DBColumn(String name, String type, String size) {
		this.name = name;
		this.type = type;
		this.size = size;

		primaryKey = false;

	}
}
