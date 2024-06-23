package ana.epj2.util;

import ana.epj2.gui.Simulation;
import ana.epj2.model.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Map;

public class SerializationUtil {

    public static void serializeBrokenVehicles() {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(Simulation.APP_CONFIG_PATH));
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        File destinationFolder = new File(appProps.getProperty("BROKEN_VEHICLES_PATH"));
        if (!destinationFolder.exists()) {
            destinationFolder.mkdir();
        }
        for (Map.Entry<String, Vehicle> entry : Simulation.vehicles.entrySet()) {
            Vehicle vehicle = entry.getValue();
            if (vehicle.getMalfunction() != null) {
                try (ObjectOutputStream oos = new ObjectOutputStream(
                        new FileOutputStream(new File(destinationFolder, vehicle.getID() + ".ser")))) {
                    oos.writeObject(vehicle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static List<Vehicle> deserializeBrokenVehicles() {
        List<Vehicle> brokenVehicles = new ArrayList<>();
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(Simulation.APP_CONFIG_PATH));
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        File folder = new File(appProps.getProperty("BROKEN_VEHICLES_PATH"));
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".ser"));

        if (files != null) {
            for (File file : files) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    Vehicle vehicle = (Vehicle) ois.readObject();
                    brokenVehicles.add(vehicle);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return brokenVehicles;
    }
}