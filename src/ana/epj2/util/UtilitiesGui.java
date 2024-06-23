package ana.epj2.util;

import ana.epj2.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class UtilitiesGui {

    public static Vector<Vector<Object>> convertMapToVector(Map<String, Vehicle> vehicles, Class<? extends  Vehicle> vehicleType) {
        Vector<Vector<Object>> dataVector = new Vector<>();

        for (Map.Entry<String, Vehicle> entry : vehicles.entrySet()) {
            Vehicle vehicle = entry.getValue();
            if (vehicleType.isInstance(vehicle)) {
                Vector<Object> vehicleVector = new Vector<>();
                vehicleVector.add(vehicle.getID());
                vehicleVector.add(vehicle.getManufacturer());
                vehicleVector.add(vehicle.getModel());
                vehicleVector.add(vehicle.getPurchasePrice());
                if (vehicle instanceof Car) {
                    Car car = (Car) vehicle;
                    vehicleVector.add(car.getPurchaseDate());
                    vehicleVector.add(car.getDescription());
                } else if (vehicle instanceof Scooter) {
                    Scooter scooter = (Scooter) vehicle;
                    vehicleVector.add(scooter.getMaxSpeed());
                } else if (vehicle instanceof Bicycle) {
                    Bicycle bicycle = (Bicycle) vehicle;
                    vehicleVector.add(bicycle.getAutonomy());
                }

                dataVector.add(vehicleVector);
            }
        }
        return dataVector;
    }
    public static Vector<Vector<Object>> getMalfunctions(Map<String, Vehicle> vehicles) {
        Vector<Vector<Object>> dataVector = new Vector<>();
        for (Map.Entry<String, Vehicle> entry : vehicles.entrySet()) {
            Vehicle vehicle = entry.getValue();
            if(vehicle.getMalfunction() != null){
            Vector<Object> malfunctionVector = new Vector<>();
            malfunctionVector.add(vehicle.getClass().getSimpleName());
            malfunctionVector.add(vehicle.getID());
            malfunctionVector.add(vehicle.getMalfunction().getDateAndTime());
            malfunctionVector.add(vehicle.getMalfunction().getReason());
            dataVector.add(malfunctionVector);
            }
        }
        return dataVector;
    }

    public static Vector<Vector<Object>> getBussinesResults(Map<LocalDate, List<Bill>> bills) {
        Vector<Vector<Object>> dataVector = new Vector<>();
        for (Map.Entry<LocalDate, List<Bill>> entry : bills.entrySet()) {
            LocalDate date = entry.getKey();
            List<Bill> billList = entry.getValue();
            double totalIncome = 0.0;
            double totalDiscounts = 0.0;
            double totalPromotions = 0.0;
            for (Bill bill : billList) {
                totalIncome += bill.getTotalPayment();
                totalDiscounts += bill.getDiscount();
                totalPromotions += bill.getDiscountProm();
            }
            Vector<Object> row = new Vector<>();
            row.add(date);
            row.add(totalIncome);
            row.add(totalDiscounts);
            row.add(totalPromotions);
            // Dodajte ostale potrebne kolone ovde
            dataVector.add(row);
        }
        return dataVector;
    }
}
