package gui;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GridPanel extends JPanel {
    private static final int GRID_SIZE = 20;
    private int rows;
    private int cols;
    private List<List<List<Vehicle>>> grid;

    public GridPanel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<List<Vehicle>> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(new ArrayList<>());
            }
            grid.add(row);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        // Draw grid and color specific region
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * cellWidth;
                int y = row * cellHeight;
                if (row >= 5 && row <= 14 && col >= 5 && col <= 14) {
                    g.setColor(Color.CYAN);
                    g.fillRect(x, y, cellWidth, cellHeight);
                }
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellWidth, cellHeight);

                // Draw vehicles if any
                List<Vehicle> vehicles = grid.get(row).get(col);
                if (!vehicles.isEmpty()) {
                    for (Vehicle vehicle : vehicles) {
                        g.setColor(Color.RED);
                        g.fillRect(x + 5, y + 5, cellWidth - 10, cellHeight - 10);
                        g.setColor(Color.BLACK);
                        g.drawString(vehicle.getID(), x + 10, y + 20);
                    }
                }
            }
        }
    }

    public synchronized void addVehicle(int x, int y, Vehicle vehicle) {
        grid.get(y).get(x).add(vehicle);
        repaint();
    }

    public synchronized void removeVehicle(int x, int y, Vehicle vehicle) {
        grid.get(y).get(x).remove(vehicle);
        repaint();
    }
}
