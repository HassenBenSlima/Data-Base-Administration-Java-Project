package components.frame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import components.TreeData;
import components.DB.SingletonConnection;

public class Authentification {

	private JButton login;
	private JButton cancel;
	private JTextField username;
	private JPasswordField password;
	private JLabel user;
	private JLabel pass;

	public void UserLogin() {
		JFrame frame = new JFrame();

		frame.setTitle("choose database :) By Hassen Ben Slima");
		frame.setLayout(new GridLayout(3, 2));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		user = new JLabel("Username");
		pass = new JLabel("Password");

		login = new JButton("Login");
		cancel = new JButton("Cancel");

		username = new JTextField();
		password = new JPasswordField();

		frame.add(user);
		frame.add(username);

		frame.add(pass);
		frame.add(password);

		frame.add(login);
		frame.add(cancel);

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});

		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String log = username.getText();
				String pass = new String(password.getPassword());

				try {
					SingletonConnection.setConnection(
							DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", log, pass));
					frame.setVisible(false);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				TreeData.createAndShowGUI();

			}
		});

		Dimension minimumSize = new Dimension(500, 100);

		frame.setSize(minimumSize);

	}

	public static void main(String[] args) {
		new Authentification().UserLogin();
	}
}
