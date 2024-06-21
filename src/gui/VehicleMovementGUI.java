package gui;
import model.Rental;
import model.Vehicle;
import util.DataLoader;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class VehicleMovementGUI extends JFrame {

    private static final int GRID_SIZE = 20;
    private JPanel[][] gridPanels;
    private Map<Point, Set<Vehicle>> vehiclePositions;

    public VehicleMovementGUI() {
        setTitle("Vehicle Movement Simulation");
        setSize(1000, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        gridPanels = new JPanel[GRID_SIZE][GRID_SIZE];
        vehiclePositions = new HashMap<>();

        initializeGrid();

        setVisible(true);
    }

    private void initializeGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                if (i >= 5 && i <= 14 && j >= 5 && j <= 14) {
                    panel.setBackground(Color.CYAN);
                }
                gridPanels[i][j] = panel;
                add(panel);
            }
        }
    }

    public synchronized void resetGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (i >= 5 && i <= 14 && j >= 5 && j <= 14) {
                    gridPanels[i][j].setBackground(Color.CYAN);
                } else {
                    gridPanels[i][j].setBackground(null);
                }
                gridPanels[i][j].removeAll();
                gridPanels[i][j].revalidate();
                gridPanels[i][j].repaint();
            }
        }
    }

    public synchronized void updateGrid(int prevX, int prevY, int x, int y, Vehicle vehicle) {
        SwingUtilities.invokeLater(() -> {
            if (prevX != -1 && prevY != -1) {
                removeVehicleFromGrid(prevX, prevY, vehicle);
            }
            addVehicleToGrid(x, y, vehicle);
        });
    }
    private synchronized void addVehicleToGrid(int x, int y, Vehicle vehicle) {
        Point point = new Point(x, y);
        vehiclePositions.computeIfAbsent(point, k -> new HashSet<>()).add(vehicle);
        JPanel panel = gridPanels[x][y];
        panel.removeAll();
        for (Vehicle v : vehiclePositions.get(point)) {
            JLabel label = new JLabel(  v.getID() + " [" + v.getCurrentBatteryLevel() + "]");
            label.setOpaque(true);
            label.setBackground(vehicle.getColor());
            panel.add(label);
        }
        panel.revalidate();
        panel.repaint();
    }
    public synchronized void removeVehicleFromGrid(int x, int y, Vehicle vehicle) {
        Point point = new Point(x, y);
        Set<Vehicle> vehiclesAtPoint = vehiclePositions.get(point);
        if (vehiclesAtPoint != null) {
            vehiclesAtPoint.remove(vehicle);
            if (vehiclesAtPoint.isEmpty()) {
                vehiclePositions.remove(point);
            }
        }
        JPanel panel = gridPanels[x][y];
        panel.removeAll();
        if (vehiclesAtPoint != null) {
            for (Vehicle v : vehiclesAtPoint) {
                JLabel label = new JLabel(v.getID() + " [" + v.getCurrentBatteryLevel() + "]");
                label.setOpaque(true);
                label.setBackground(vehicle.getColor());
                panel.add(label);
            }
        }
        panel.revalidate();
        panel.repaint();
    }
}
