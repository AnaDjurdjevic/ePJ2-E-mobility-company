package ana.epj2.util;

import ana.epj2.model.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
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
    public static Vector<Vector<Object>> getBusinessResults(Map<LocalDate, List<Bill>> bills) {
        Vector<Vector<Object>> dataVector = new Vector<>();
        DecimalFormat formatter = new DecimalFormat("#.##");
        double grandTotalIncome = 0.0;
        double grandTotalDiscounts = 0.0;
        double grandTotalPromotions = 0.0;
        double grandTotalRidesWide = 0.0;
        double grandTotalRidesNarrow = 0.0;
        double grandTotalMaintenance = 0.0;
        double grandTotalRepairs = 0.0;

        for (Map.Entry<LocalDate, List<Bill>> entry : bills.entrySet()) {
            LocalDate date = entry.getKey();
            List<Bill> billList = entry.getValue();
            double totalIncome = 0.0;
            double totalDiscounts = 0.0;
            double totalPromotions = 0.0;
            double totalRidesWide = 0.0;
            double totalRidesNarrow = 0.0;
            double totalMaintenance = 0.0;
            double totalRepairs = 0.0;
            for (Bill bill : billList) {
                totalIncome += bill.getTotalPayment();
                totalDiscounts += bill.getDiscount();
                totalPromotions += bill.getDiscountProm();
                if (bill.getRental().isWasInTheWiderPart()) {
                    totalRidesWide += bill.getTotalPayment();
                } else {
                    totalRidesNarrow += bill.getTotalPayment();
                }
                totalMaintenance += bill.getTotalPayment() * 0.2;
                totalRepairs += bill.getRepairCost();
            }
            grandTotalIncome += totalIncome;
            grandTotalDiscounts += totalDiscounts;
            grandTotalPromotions += totalPromotions;
            grandTotalRidesWide += totalRidesWide;
            grandTotalRidesNarrow += totalRidesNarrow;
            grandTotalMaintenance += totalMaintenance;
            grandTotalRepairs += totalRepairs;
            Vector<Object> row = new Vector<>();
            row.add(date);
            row.add(formatter.format(totalIncome));
            row.add(formatter.format(totalDiscounts));
            row.add(formatter.format(totalPromotions));
            row.add(formatter.format(totalMaintenance));
            row.add(formatter.format(totalRepairs));
            row.add(formatter.format(totalRidesWide));
            row.add(formatter.format(totalRidesNarrow));
            dataVector.add(row);
        }
        double totalCompanyCosts = grandTotalIncome * 0.2;
        double totalTax = (grandTotalIncome - grandTotalMaintenance - grandTotalRepairs - totalCompanyCosts) * 0.1;
        Vector<Object> totalRow = new Vector<>();
        totalRow.add("Total");
        totalRow.add(formatter.format(grandTotalIncome));
        totalRow.add(formatter.format(grandTotalDiscounts));
        totalRow.add(formatter.format(grandTotalPromotions));
        totalRow.add(formatter.format(grandTotalMaintenance));
        totalRow.add(formatter.format(grandTotalRepairs));
        totalRow.add(formatter.format(grandTotalRidesWide));
        totalRow.add(formatter.format(grandTotalRidesNarrow));
        totalRow.add(formatter.format(totalCompanyCosts));
        totalRow.add(formatter.format(totalTax));
        dataVector.add(totalRow);
        return dataVector;
    }
}
