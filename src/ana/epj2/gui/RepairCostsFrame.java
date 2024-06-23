package ana.epj2.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.List;
import ana.epj2.model.Bicycle;
import ana.epj2.model.Car;
import ana.epj2.model.Scooter;
import ana.epj2.model.Vehicle;
import ana.epj2.util.SerializationUtil;

public class RepairCostsFrame extends JFrame {

    private Vector<Object> columns;
    private Vector<Vector<Object>> data;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton loadButton;

    /**
     * Create the frame.
     */
    public RepairCostsFrame() {
        columns = new Vector<Object>();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 400);
        setLocationRelativeTo(null);
        columns.add("ID");
        columns.add("Manufacturer");
        columns.add("Model");
        columns.add("Purchase Price");
        columns.add("Malfunction Reason");
        columns.add("Malfunction Date And Time");
        columns.add("Purchase Date");
        columns.add("Description");
        columns.add("Maximum speed");
        columns.add("Autonomy");
        columns.add("Repair Cost");
        setTitle("Repair Costs");
        initialize();
    }

    private void initialize() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        setExtendedState(Frame.MAXIMIZED_BOTH);

        loadButton = new JButton("Load Broken Vehicles");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBrokenVehicles();
            }
        });

        panel.add(loadButton, BorderLayout.NORTH);

        data = new Vector<>();
        table = new JTable(data, columns);
        scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(panel);
    }
    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane();
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setViewportView(getTable());
        }
        return scrollPane;
    }
    private void loadBrokenVehicles() {
        List<Vehicle> brokenVehicles = SerializationUtil.deserializeBrokenVehicles();
        data.clear();
        DecimalFormat formatter = new DecimalFormat("#.##");

        for (Vehicle vehicle : brokenVehicles) {
            Vector<Object> row = new Vector<>();
            row.add(vehicle.getID());
            row.add(vehicle.getManufacturer());
            row.add(vehicle.getModel());
            row.add(vehicle.getPurchasePrice());
            row.add(vehicle.getMalfunction().getReason());
            row.add(vehicle.getMalfunction().getDateAndTime());
            if (vehicle instanceof Car) {
                Car car = (Car) vehicle;
                row.add(car.getPurchaseDate());
                row.add(car.getDescription());
                row.add(""); //max speed
                row.add(""); //autonomy
                row.add(formatter.format(car.getRepairCoefficient() * car.getPurchasePrice()));
            } else if (vehicle instanceof Scooter) {
                Scooter scooter = (Scooter) vehicle;
                row.add(""); //purchase date
                row.add(""); //description
                row.add(scooter.getMaxSpeed());
                row.add(""); // autonomy
                row.add(formatter.format(scooter.getRepairCoefficient() * scooter.getPurchasePrice()));
            } else if (vehicle instanceof Bicycle) {
                Bicycle bicycle = (Bicycle) vehicle;
                row.add(""); //purchase date
                row.add(""); //description
                row.add(""); //max speed
                row.add(bicycle.getAutonomy());
                row.add(formatter.format(bicycle.getRepairCoefficient() * bicycle.getPurchasePrice()));
            }
            data.add(row);
        }
        table.revalidate();
        table.repaint();
    }
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
            table.getColumnModel().getColumn(10).setPreferredWidth(150);
        }
        return table;
    }

}