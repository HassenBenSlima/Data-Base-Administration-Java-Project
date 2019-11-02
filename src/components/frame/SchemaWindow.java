package components.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.mindfusion.diagramming.Align;
import com.mindfusion.diagramming.ArrowHeads;
import com.mindfusion.diagramming.AutoResize;
import com.mindfusion.diagramming.Cell;
import com.mindfusion.diagramming.Diagram;
import com.mindfusion.diagramming.DiagramLink;
import com.mindfusion.diagramming.DiagramView;
import com.mindfusion.diagramming.GridRouter;
import com.mindfusion.diagramming.LayeredLayout;
import com.mindfusion.diagramming.LinkCrossings;
import com.mindfusion.diagramming.LinkShape;
import com.mindfusion.diagramming.ShadowsStyle;
import com.mindfusion.diagramming.SimpleShape;
import com.mindfusion.diagramming.SolidBrush;
import com.mindfusion.diagramming.TableNode;
import com.mindfusion.diagramming.TextFormat;
import com.mindfusion.diagramming.ZoomControl;
import com.mindfusion.diagramming.jlayout.Orientation;

import components.schema.DBColumn;
import components.schema.DBMetaData;
import components.schema.DBRelation;

/**
 * @description Main class for the sample application.
 *
 */

public class SchemaWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Diagram diagram;
	private DiagramView diagramView;
	private JScrollPane jscrollPane;
	private ZoomControl zoomer;
	private ArrayList<String> tables;

	public SchemaWindow() {
		super("Class Diagram Sample");
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// initialize controls and table data
		initialize();

		// create DB tables
		createTables();

		createRelationships();

		arrangeDiagram();

		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

	}

	private void initialize() {
		// diagram initialization
		diagram = new Diagram();
		diagram.setAutoResize(AutoResize.RightAndDown);

		// initialize a diagramView that will render the diagram.
		diagramView = new DiagramView(diagram);
		diagramView.setVisible(true);

		// provide a zoomer for the diagram
		zoomer = new ZoomControl();
		zoomer.setView(diagramView);
		zoomer.setPreferredSize(new Dimension(70, 50));
		zoomer.setVisible(true);

		// use a scroll pane to host large diagrams
		jscrollPane = new JScrollPane(diagramView);
		jscrollPane.setVisible(true);
		jscrollPane.setAutoscrolls(true);

		getContentPane().setLayout(new BorderLayout());
		this.add(zoomer, BorderLayout.EAST);
		this.add(jscrollPane, BorderLayout.CENTER);

		// diagram settings
		diagram.setTableColumnCount(4);
		diagram.setTableRowHeight(10f);
		diagram.setShadowsStyle(ShadowsStyle.None);
		diagram.setEnableStyledText(true);

		// read data for the tables
		tables = new ArrayList<String>();

		try {
			tables = DBMetaData.getTablesMetadata();
		} catch (SQLException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	private void createTables() {
		for (String tableName : tables) {
			try {
				ArrayList<DBColumn> tableData = DBMetaData.getColumnsMetadata(tableName);

				TableNode table = diagram.getFactory().createTableNode(10, 10, 50, tableData.size() * 8, 4,
						tableData.size());
				table.setCaption("<b>" + tableName + "</b>");
				table.setId(tableName);

				// customize the tables
				table.setCaptionFormat(new TextFormat(Align.Center, Align.Center));
				table.setCaptionHeight(7f);
				table.setRowHeight(10f);
				table.setAllowResizeColumns(true);
				table.getColumns().get(0).setWidth(22f);
				table.setShape(SimpleShape.RoundedRectangle);
				table.setBrush(new SolidBrush(new Color((int) 153, (int) 179, (int) 255)));

				int rowIndex = 0;

				for (DBColumn column : tableData) {
					table.getCell(1, rowIndex).setText("<b>" + column.name + "</b>");
					table.getCell(2, rowIndex).setText(column.type);
					table.getCell(3, rowIndex).setText(column.size);

					// if the column is a primary key - set an image. If not, leave it empty.
					if (column.isPrimaryKey()) {
						try {

							File pathToFile = new File("res/key.png");
							Image image = ImageIO.read(pathToFile);
							table.getCell(0, rowIndex).setImage(image);

						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}

					rowIndex++;

				}

				// adjust the size of the tables and columns.
				table.resizeToFitText(true);
				Rectangle2D.Float t_size = table.getBounds();
				table.getColumns().get(0).setWidth(7);
				table.resize(t_size.width + 7, t_size.height);
				table.resizeToFitImage();

			} catch (SQLException s) {
				System.err.println(s.getMessage());
			}
		}
	}

	private void createRelationships() {
		try {
			ArrayList<DBRelation> relations = DBMetaData.getRelationsMetadata(tables);

			for (DBRelation relation : relations) {
				TableNode source = (TableNode) diagram.findNodeById(relation.pk_table);
				TableNode destination = (TableNode) diagram.findNodeById(relation.fk_table);

				if (source != null && destination != null) {
					int pk_index = -1;
					int fk_index = -1;

					int rowCount = source.getRowCount();

					for (int i = 0; i < rowCount; i++) {
						Cell cell = source.getCell(1, i);

						if (cell.getText().equals("<b>" + relation.pk_key + "</b>")) {
							pk_index = i;
							break;
						}
					}

					rowCount = destination.getRows().size();

					for (int i = 0; i < rowCount; i++) {
						Cell cell = destination.getCell(1, i);
						if (cell.getText().equals("<b>" + relation.fk_key + "</b>")) {
							fk_index = i;
							break;
						}
					}

					DiagramLink link = diagram.getFactory().createDiagramLink(source, pk_index, destination, fk_index);
					link.setBaseShape(ArrowHeads.RevWithLine);
					link.setBaseShapeSize(3f);
					link.setHeadShapeSize(3f);
					link.setShape(LinkShape.Cascading);

					// if the relation is not on a primary key column, assign a fk image to it.
					try {

						File pathToFile = new File("res/fkey.png");
						Image image = ImageIO.read(pathToFile);
						if (fk_index > -1 && destination.getCell(0, fk_index).getImage() == null)
							destination.getCell(0, fk_index).setImage(image);

					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	private void arrangeDiagram() {
		// use LayeredLayout with some initial customization
		LayeredLayout layout = new LayeredLayout(Orientation.Horizontal, 30f, 25f, 5f, 5f);
		layout.arrange(diagram);

		// adjust link position
		for (DiagramLink link : diagram.getLinks()) {

			if (link.getOrigin().getBounds().getX() < link.getDestination().getBounds().getX())
				link.setEndPoint(new Point2D.Float(link.getDestination().getBounds().x, link.getEndPoint().y));
		}
		// re-route all links
		diagram.setLinkRouter(new GridRouter());
		diagram.routeAllLinks();

		// resize the diagram after the layout to fit all items
		diagram.resizeToFitItems(20);
		diagramView.scrollTo(diagram.getBounds().width / 2, 0);

		// customize the links
		diagram.setLinkCrossings(LinkCrossings.Arcs);
		diagram.setRoundedLinks(true);
		diagram.setRoundedLinksRadius(3);
		// redraw the control
		diagram.repaint();
	}


}
