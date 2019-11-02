
package components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import components.DAO.IQuerySQLImpl;
import components.DAO.MetierDataBaseImpl;
import components.entities.Colomn;
import components.entities.Index;
import components.entities.Procedure;
import components.entities.Sequence;
import components.entities.Table;
import components.entities.Trigger;
import components.entities.User;
import components.entities.View;
import components.frame.Authentification;
import components.frame.SchemaWindow;

public class TreeData extends JPanel implements TreeSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private JEditorPane htmlPane;
	private JTree tree;
	public static JTable jtable;
	private static boolean DEBUG = false;
	private static boolean visible = true;
	// Optionally play with line styles. Possible values are
	// "Angled" (the default), "Horizontal", and "None".
	private static boolean playWithLineStyle = false;
	private static String lineStyle = "Horizontal";
	private static JFrame frame;

	// Optionally set the look and feel.
	private static boolean useSystemLookAndFeel = true;

	public TreeData() {
		super(new GridLayout(1, 0));

		// Create the nodes.
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Connexions");
		createNodes(top);

		// Create a tree that allows one selection at a time.
		tree = new JTree(top);
		/******** Listener ********/
		// Where the tree is initialized: you can select one node
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		tree.addTreeSelectionListener(this);
		/****************/
		File fileIcon = new File("images/Database-Table-icon.png");
		File fileOpenIcon = new File("images/Folder-Generic-Blue-icon.png");
		File fileClosedIcon = new File("images/Folder-Generic-Green-icon.png");
		// Set the icon for leaf nodes.

		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		Icon closedIcon = new ImageIcon(fileOpenIcon.toString());
		Icon openIcon = new ImageIcon(fileClosedIcon.toString());
		Icon leafIcon = new ImageIcon(fileIcon.toString());
		renderer.setClosedIcon(closedIcon);
		renderer.setOpenIcon(openIcon);
		renderer.setLeafIcon(leafIcon);

		// to request that the Java look and feel use only horizontal lines to group
		// nodes, use the following code:"Angled" (default)
		if (playWithLineStyle) {
			System.out.println("line style = " + lineStyle);
			tree.putClientProperty("JTree.lineStyle", lineStyle);
		}
		/** component's panel **/
		// Create the scroll pane and add the tree to it.
		JScrollPane treeView = new JScrollPane(tree);
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		// Create the HTML viewing pane.
		Label lab = new Label("Query:");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		panel.add(lab, c);
		c.weightx = 1;
		panel.setLayout(new GridBagLayout());

		JButton buttonSchema = new JButton("Schema");

		JButton buttonSubmit = new JButton("Submit");
		JButton buttonClear = new JButton("Clear");
		JButton buttonDeconnect = new JButton("deconnect");

		TextArea sql = new TextArea();

		jtable = new JTable();
		JScrollPane jtableView = new JScrollPane(jtable);

		panel.add(sql, c);
		panel.add(buttonSubmit, c);

		panel.add(buttonClear);
		panel.add(buttonSchema);
		panel2.setLayout(new GridBagLayout());

		GridBagConstraints c2 = new GridBagConstraints();

		c2.fill = GridBagConstraints.HORIZONTAL;
		Label resultat = new Label("Resultat:");
		c2.weightx = 0.5;
		panel2.add(resultat, c2);
		c2.weightx = 0.5;
		c2.insets = new Insets(20, 0, 0, 0); // Insets(int top, int left, int bottom, int right)
		c2.anchor = GridBagConstraints.LINE_START; // bottom of space
		panel2.add(jtableView, c2);
		c2.weightx = 0.5;
		c2.fill = GridBagConstraints.FIRST_LINE_START;
		panel2.add(buttonDeconnect, c2);

		JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane2.setTopComponent(panel);
		splitPane2.setBottomComponent(panel2);

		buttonSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String requet = sql.getText().replace(";", "");
				new IQuerySQLImpl().getrequets(requet);
			}
		});

		buttonDeconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new Authentification().UserLogin();

			}
		});

		buttonSchema.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				SchemaWindow window = new SchemaWindow();
				window.setVisible(true);

			}
		});

		buttonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sql.setText("");
			}
		});

		// Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(splitPane2);

		Dimension minimumSize = new Dimension(500, 100);

		// htmlView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(280);
		splitPane.setPreferredSize(new Dimension(1100, 600));

		// Add the split pane to this panel.
		add(splitPane);
	}

	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		// Returns the last path element of the selection.
		// This method is useful only when the selection model allows a single
		// selection.

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		if (node == null)
			return;

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			// current node selected
			System.out.println(node.toString());
		}

		if (DEBUG) {
			System.out.println(nodeInfo.toString());
		}
	}

	private void createNodes(DefaultMutableTreeNode top) {

		MetierDataBaseImpl property = new MetierDataBaseImpl();

		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode component = null;
		DefaultMutableTreeNode table = null;
		DefaultMutableTreeNode attribut = null;
		DefaultMutableTreeNode constraint = null;
		DefaultMutableTreeNode columns = null;
		DefaultMutableTreeNode tablesReference = null;
		DefaultMutableTreeNode constraintName = null;
		DefaultMutableTreeNode r_constraintName = null;
		// name of current user
		category = new DefaultMutableTreeNode(property.getCurrentUserName());
		/*
		 * tables
		 */
		component = new DefaultMutableTreeNode("Tables");
		/*
		 * name of tables
		 */

		for (Table tab : property.getAllTables()) {

			table = new DefaultMutableTreeNode(tab.toString());
			columns = new DefaultMutableTreeNode("Columns");
			table.add(columns);
			constraint = new DefaultMutableTreeNode("Constraint");
			table.add(constraint);
			tablesReference = new DefaultMutableTreeNode("TablesRerenceés");
			table.add(tablesReference);
			component.add(table);

			for (Colomn col : property.getAllColomnsTable(tab.toString())) {

				attribut = new DefaultMutableTreeNode(col.toString());
				columns.add(attribut);
			}

			for (Colomn col : property.getContraints(tab.toString())) {

				constraintName = new DefaultMutableTreeNode(col.toString());

				constraint.add(constraintName);

			}

			for (Colomn col : property.getTablererences(tab.toString())) {

				r_constraintName = new DefaultMutableTreeNode(col.toString());

				tablesReference.add(r_constraintName);

			}

		}

		category.add(component);
		top.add(category);

		/*
		 * Indexes
		 */
		component = new DefaultMutableTreeNode("Indexes");
		// name of tables
		for (Index ind : property.getAllColomnsIndex()) {
			attribut = new DefaultMutableTreeNode(ind.toString());
			component.add(attribut);
		}

		category.add(component);
		top.add(category);

		/*
		 * View
		 */
		component = new DefaultMutableTreeNode("Views");
		for (View view : property.getAllViews()) {

			table = new DefaultMutableTreeNode(view.toString());
			component.add(table);
			for (Colomn col : property.getAllColomnsView(view.toString())) {

				attribut = new DefaultMutableTreeNode(col.toString());
				table.add(attribut);
			}
		}

		category.add(component);
		top.add(category);

		component = new DefaultMutableTreeNode("Sequences");
		for (Sequence seq : property.getAllSequence()) {

			table = new DefaultMutableTreeNode(seq.toString());
			component.add(table);

		}

		category.add(component);
		top.add(category);

		component = new DefaultMutableTreeNode("Procedures");
		for (Procedure procedure : property.getAllProcedures(property.getCurrentUserName())) {

			table = new DefaultMutableTreeNode(procedure.toString());
			component.add(table);

		}

		category.add(component);
		top.add(category);

		component = new DefaultMutableTreeNode("Triggers");
		for (Trigger trigger : property.getAllTriggers()) {

			table = new DefaultMutableTreeNode(trigger.toString());
			component.add(table);

		}

		category.add(component);
		top.add(category);

		component = new DefaultMutableTreeNode("Other users");
		for (User user : property.getOtherUsers()) {

			attribut = new DefaultMutableTreeNode(user.toString());
			component.add(attribut);

		}

		category.add(component);
		top.add(category);

	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = TreeData.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event dispatch thread.
	 */
	public static void createAndShowGUI() {

		if (useSystemLookAndFeel) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				System.err.println("Couldn't use system look and feel.");
			}
		}

		// Create and set up the window.
		frame = new JFrame("DataBase");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new TreeData());

		// Display the window.
		frame.pack();
		frame.setVisible(visible);
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();

			}
		});
	}

}