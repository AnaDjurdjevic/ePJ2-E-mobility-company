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
        setSize(800, 800);
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

    public void resetGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (i >= 5 && i <= 14 && j >= 5 && j <= 14) {
                    gridPanels[i][j].setBackground(Color.CYAN); // Restore the original color for the specified region
                } else {
                    gridPanels[i][j].setBackground(null); // Restore the default color
                }
                gridPanels[i][j].removeAll();
                gridPanels[i][j].revalidate();
                gridPanels[i][j].repaint();
            }
        }
    }

    public void updateGrid(int prevX,int prevY,int x, int y, Vehicle vehicle) {
        if (prevX != -1 && prevY != -1) {
            JPanel prevPanel = gridPanels[prevX][prevY];
            if (prevX >= 5 && prevX <= 14 && prevY >= 5 && prevY <= 14) {
                prevPanel.setBackground(Color.CYAN); // Restore the original color for the specified region
            } else {
                prevPanel.setBackground(null); // Restore the default color
            }
            prevPanel.removeAll();
            prevPanel.revalidate();
            prevPanel.repaint();
        }
        JPanel panel = gridPanels[x][y];
        panel.setBackground(vehicle.getColor());
        panel.removeAll();
        JLabel label = new JLabel(vehicle.getID() + " [" + vehicle.getCurrentBatteryLevel() + "]");
        panel.add(label);
        panel.revalidate();
        panel.repaint();
        //System.out.println("Vehicle ID: " + vehicle.getID() + " updated on grid at (" + x + ", " + y + ")");

    }
    public void removeVehicleFromGrid(int x, int y, Vehicle vehicle) {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = gridPanels[x][y];
            if (x >= 5 && x <= 14 && y >= 5 && y <= 14) {
                panel.setBackground(Color.CYAN); // Restore the original color for the specified region
            } else {
                panel.setBackground(null); // Restore the default color
            }
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
        });
    }
}
