package components.schema;

/**
 * @description Represents a relationship in the DB.
 *
 */
public class DBRelation {

	public String pk_key;
	public String pk_table;

	public String fk_key;
	public String fk_table;

	/**
	 * Initializes a new DBRelation object.
	 * 
	 * @param pk_key
	 *            The primaty key.
	 * @param _pk_table
	 *            The table that holds the primary key.
	 * @param _fk_key
	 *            The foreign key.
	 * @param _fk_table
	 *            The table that holds the foreign key.
	 */
	public DBRelation(String pk_key, String pk_table, String fk_key, String fk_table) {
		this.pk_key = pk_key;
		this.pk_table = pk_table;
		this.fk_key = fk_key;
		this.fk_table = fk_table;

	}
}
