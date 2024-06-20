package gui;
import model.Rental;
import model.Vehicle;
import util.DataLoader;

import javax.swing.*;
import java.awt.*;
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
        simulateVehicleMovement();

        setVisible(true);
    }

    private void initializeGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanels[i][j] = panel;
                add(panel);
            }
        }
    }

    private void simulateVehicleMovement() {
        for (Rental rental : Simulation.rentals) {
            rental.start();

            try {
                rental.join(); // Čekanje da se trenutna simulacija završi pre početka sledeće
                Thread.sleep(5000); // Pauza od 5 sekundi između simulacija
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public void updateGrid(int prevX,int prevY,int x, int y, Vehicle vehicle) {
        Random rand = new Random();
        if (prevX != -1 && prevY != -1) {
            JPanel prevPanel = gridPanels[prevX][prevY];
            prevPanel.setBackground(null);
            prevPanel.removeAll();
            prevPanel.revalidate();
            prevPanel.repaint();
        }
        JPanel panel = gridPanels[x][y];
        panel.setBackground(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        panel.removeAll();
        JLabel label = new JLabel("<html>ID: " + vehicle.getID() + "<br>Battery: " + vehicle.getCurrentBatteryLevel() + "</html>");
        panel.add(label);
        panel.revalidate();
        panel.repaint();

        try {
            Thread.sleep(500); // Simulate delay for movement
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
