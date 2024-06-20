package gui;
import model.Rental;
import model.Vehicle;
import util.DataLoader;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VehicleMovementGUI extends JFrame {

    private static final int GRID_SIZE = 20;
    private JPanel[][] gridPanels;

    public VehicleMovementGUI() {
        setTitle("Vehicle Movement Simulation");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        gridPanels = new JPanel[GRID_SIZE][GRID_SIZE];

        initializeGrid();

        setVisible(true);
    }

    private void initializeGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
        Random rand = new Random();
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
        System.out.println("Vehicle ID: " + vehicle.getID() + " updated on grid at (" + x + ", " + y + ")");

    }
}
