package components.DAO;

import java.util.List;

import components.entities.Colomn;
import components.entities.Index;
import components.entities.Procedure;
import components.entities.Sequence;
import components.entities.Table;
import components.entities.Trigger;
import components.entities.User;
import components.entities.View;

public interface IMetier {
	/*
	 * name of current user
	 */
	public String getCurrentUserName();

	public List<User> getOtherUsers();

	/*
	 * Tables & columns & ....
	 */
	public List<Table> getAllTables();

	public List<Colomn> getAllColomnsTable(String name);

	public List<Colomn> getContraints(String tableName);

	public List<Colomn> getTablererences(String tableName);

	/*
	 * Views & columns
	 */
	public List<View> getAllViews();

	public List<Colomn> getAllColomnsView(String VIEW_NAME);

	/*
	 * indexes
	 */

	public List<Index> getAllColomnsIndex();

	/*
	 * Sequences
	 */
	public List<Sequence> getAllSequence();

	/*
	 * Procedures & columns
	 */
	public List<Procedure> getAllProcedures(String owner);

	// autre methode
	public List<Procedure> getAllProcedures();

	/**
	 * 
	 * Mazelete
	 */
	public List<Colomn> getAllColomnsProcedures(String Procedures_NAME);

	/*
	 * Triggers
	 */
	public List<Trigger> getAllTriggers();

}
