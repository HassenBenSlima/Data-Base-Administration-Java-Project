package components.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import components.DB.SingletonConnection;
import components.entities.Colomn;
import components.entities.Index;
import components.entities.Procedure;
import components.entities.Sequence;
import components.entities.Table;
import components.entities.Trigger;
import components.entities.User;
import components.entities.View;

public class MetierDataBaseImpl implements IMetier {

	public static void main(String[] args) {
		System.out.println(new MetierDataBaseImpl().getCurrentUserName());

		System.out.println("------------Tables-----------------");

		List<Table> tab = new MetierDataBaseImpl().getAllTables();
		System.out.println("-------------Contraintes----------------");

		for (Table table : tab) {
			System.out.println(table.toString());

			List<Colomn> coll = new MetierDataBaseImpl().getContraints(table.toString());

			for (Colomn cols : coll) {
				System.out.println(cols.toString());
			}
		}

		System.out.println("-------------Views----------------");
		List<View> vw = new MetierDataBaseImpl().getAllViews();

		for (View view : vw) {
			System.out.println(view.toString());
		}

		System.out.println("-----------------------------");
		List<Colomn> col = new MetierDataBaseImpl().getAllColomnsTable("DEPARTMENTS");

		for (Colomn colomn : col) {
			System.out.println(colomn.toString());
		}

		System.out.println("-------------Index----------------");
		List<Index> ind = new MetierDataBaseImpl().getAllColomnsIndex();

		for (Index index : ind) {
			System.out.println(index.toString());
		}

		System.out.println("-------------Procedures----------------");
		List<Procedure> procedures = new MetierDataBaseImpl().getAllProcedures();

		for (Procedure procedure : procedures) {
			System.out.println(procedure.toString());
		}

	}

	@Override
	public String getCurrentUserName() {
		String name = null;
		Connection conn = SingletonConnection.getConnection();

		try {

			PreparedStatement ps = conn.prepareStatement("SELECT username FROM user_users");

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				name = rs.getString("username");
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return name;
	}

	@Override
	public List<Table> getAllTables() {
		List<Table> tables = new ArrayList<Table>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT table_name FROM user_tables");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Table table = new Table();
				table.setTableName(rs.getString("table_name"));

				tables.add(table);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;
	}

	@Override
	public List<Colomn> getAllColomnsTable(String name) {

		List<Colomn> colomns = new ArrayList<Colomn>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT column_name FROM user_tab_cols WHERE table_name =?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Colomn colomn = new Colomn();
				colomn.setColomnName(rs.getString("column_name"));
				colomns.add(colomn);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colomns;
	}

	@Override
	public List<Colomn> getContraints(String tableName) {
		List<Colomn> colomns = new ArrayList<Colomn>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn
					.prepareStatement("select constraint_name  from user_constraints where table_name=?");
			ps.setString(1, tableName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Colomn colomn = new Colomn();
				colomn.setColomnName(rs.getString("constraint_name"));
				colomns.add(colomn);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colomns;
	}

	@Override
	public List<Colomn> getTablererences(String tableName) {
		List<Colomn> colomns = new ArrayList<Colomn>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn
					.prepareStatement("select R_CONSTRAINT_NAME  from user_constraints where table_name=?");
			ps.setString(1, tableName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Colomn colomn = new Colomn();
				colomn.setColomnName(rs.getString("R_CONSTRAINT_NAME"));
				colomns.add(colomn);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colomns;
	}

	/*
	 * Views
	 */
	@Override
	public List<View> getAllViews() {
		List<View> views = new ArrayList<View>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("select view_name from user_views");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				View view = new View();
				view.setViewName(rs.getString("view_name"));

				views.add(view);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return views;
	}

	@Override
	public List<Colomn> getAllColomnsView(String VIEW_NAME) {

		List<Colomn> colomns = new ArrayList<Colomn>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("select column_name from ALL_TAB_COLUMNS where table_name=?");
			ps.setString(1, VIEW_NAME);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Colomn colomn = new Colomn();
				colomn.setColomnName(rs.getString("column_name"));
				colomns.add(colomn);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colomns;
	}

	/*
	 * Indexes
	 */
	@Override
	public List<Index> getAllColomnsIndex() {
		List<Index> colomns = new ArrayList<Index>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT INDEX_NAME FROM user_indexes");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Index colomn = new Index();
				colomn.setIndexName(rs.getString("INDEX_NAME"));
				colomns.add(colomn);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colomns;
	}

	@Override
	public List<Sequence> getAllSequence() {
		List<Sequence> sequences = new ArrayList<Sequence>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT sequence_name FROM user_sequences");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Sequence sequence = new Sequence();
				sequence.setSequenceName(rs.getString("sequence_name"));
				sequences.add(sequence);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sequences;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see components.DAO.IMetier#getAllProcedures(owner)
	 */
	@Override
	public List<Procedure> getAllProcedures(String owner) {
		List<Procedure> procedures = new ArrayList<Procedure>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("select OBJECT_NAME from ALL_OBJECTS where OWNER='" + owner
					+ "' AND upper(OBJECT_TYPE) = upper('PROCEDURE')  ");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Procedure procedure = new Procedure();
				procedure.setProcedureName(rs.getString("OBJECT_NAME"));
				procedures.add(procedure);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return procedures;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see components.DAO.IMetier#getOtherUsers()
	 */
	@Override
	public List<User> getOtherUsers() {
		List<User> users = new ArrayList<User>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn
					.prepareStatement("((select username from all_users) minus (select username from user_users))");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserName(rs.getString("username"));
				users.add(user);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see components.DAO.IMetier#getAllProcedures()
	 */
	@Override
	public List<Procedure> getAllProcedures() {
		List<Procedure> procedures = new ArrayList<Procedure>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT object_name FROM user_procedures");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Procedure procedure = new Procedure();
				procedure.setProcedureName(rs.getString("object_name"));
				procedures.add(procedure);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return procedures;
	}

	@Override
	public List<Colomn> getAllColomnsProcedures(String Procedures_NAME) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see components.DAO.IMetier#getAllTriggers()
	 */
	@Override
	public List<Trigger> getAllTriggers() {
		List<Trigger> colomns = new ArrayList<Trigger>();
		Connection conn = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT TRIGGER_NAME FROM user_triggers");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Trigger colomn = new Trigger();
				colomn.setTriggerName(rs.getString("TRIGGER_NAME"));
				colomns.add(colomn);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colomns;
	}

}
