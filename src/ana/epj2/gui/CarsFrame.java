package ana.epj2.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;
import ana.epj2.model.Car;
import ana.epj2.util.UtilitiesGui;
/**
 * CarsFrame is a frame that displays information about cars in a table.
 * The table includes columns for ID, Manufacturer, Model, Purchase price, Purchase date, and Description.
 */
public class CarsFrame extends JFrame {

    private Vector<Object> columns;
    private Vector<Vector<Object>> data;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable table;

    /**
     * Creates a new CarsFrame.
     * Initializes the table with columns and data for cars.
     */
    public CarsFrame() {
        columns = new Vector<Object>();
        columns.add("ID");
        columns.add("Manufacturer");
        columns.add("Model");
        columns.add("Purchase price");
        columns.add("Purchase date");
        columns.add("Description");
        setTitle("Cars");
        data = UtilitiesGui.convertMapToVector(Simulation.vehicles, Car.class);
        initialize();
    }
    /**
     * Initializes the frame and its components.
     */
    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 815, 420);
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
     * Returns the table displaying car data.
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
            table.getColumnModel().getColumn(4).setPreferredWidth(350);
            table.getColumnModel().getColumn(5).setPreferredWidth(150);
        }
        return table;
    }

}