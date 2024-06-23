package ana.epj2.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;
import ana.epj2.util.BillsCreator;
import ana.epj2.util.UtilitiesGui;
/**
 * BusinessResultsFrame is a frame that displays business results in a table.
 * The table includes columns for Date, Total income, Total discount, Total promotions,
 * Total maintenance, Total repairs, Total income - wide area, Total income - narrow area,
 * Total company costs, and Total tax.
 */
public class BusinessResultsFrame extends JFrame {

    private Vector<Object> columns;
    private Vector<Vector<Object>> data;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable table;

    /**
     * Creates a new BusinessResultsFrame.
     * Initializes the table with columns and data for business results.
     */
    public BusinessResultsFrame() {
        columns = new Vector<Object>();
        columns.add("Date");
        columns.add("Total income");
        columns.add("Total discount");
        columns.add("Total promotions");
        columns.add("Total maintenance");
        columns.add("Total repairs");
        columns.add("Total income - wide");
        columns.add("Total income - narrow");
        columns.add("Total company costs");
        columns.add("Total tax");
        setTitle("Business Results");
        data = UtilitiesGui.getBusinessResults(BillsCreator.bills);
        initialize();
    }
    /**
     * Initializes the frame and its components.
     */
    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 815, 420);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(this.contentPane);
        this.contentPane.add(getScrollPane(), BorderLayout.CENTER);
    }
    /**
     * Returns the scroll pane containing the table.
     *
     * @return the scroll pane
     */
    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane();
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setViewportView(getTable());
        }
        return scrollPane;
    }
    /**
     * Returns the table displaying business results data.
     *
     * @return the table
     */
    private JTable getTable() {
        if (table == null) {
            table = new JTable(new DefaultTableModel(data, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setFillsViewportHeight(true);
            table.getColumnModel().getColumn(0).setPreferredWidth(150);
            table.getColumnModel().getColumn(1).setPreferredWidth(200);
            table.getColumnModel().getColumn(2).setPreferredWidth(350);
            table.getColumnModel().getColumn(3).setPreferredWidth(150);
            table.getColumnModel().getColumn(4).setPreferredWidth(150);
            table.getColumnModel().getColumn(5).setPreferredWidth(200);
            table.getColumnModel().getColumn(6).setPreferredWidth(350);
            table.getColumnModel().getColumn(7).setPreferredWidth(150);
            table.getColumnModel().getColumn(8).setPreferredWidth(350);
            table.getColumnModel().getColumn(9).setPreferredWidth(150);
        }
        return table;
    }

}