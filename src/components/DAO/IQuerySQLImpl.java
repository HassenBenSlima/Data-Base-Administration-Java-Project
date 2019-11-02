package components.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import components.TreeData;
import components.DB.SingletonConnection;
import net.proteanit.sql.DbUtils;

public class IQuerySQLImpl implements IQuerySQL {

	@Override
	public void getrequets(String req) {
		Connection conn = SingletonConnection.getConnection();
		try {

			PreparedStatement ps = conn.prepareStatement(req);

			ResultSet rs = ps.executeQuery();

			TreeData.jtable.setModel(DbUtils.resultSetToTableModel(rs));

			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Verifiez votre requete");

			// e.printStackTrace();
		}
	}

}
