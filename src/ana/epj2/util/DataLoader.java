package ana.epj2.util;

import ana.epj2.exceptions.IncorrectInputFormatException;
import ana.epj2.exceptions.NonExistentValueException;
import ana.epj2.exceptions.DuplicateValueException;
import ana.epj2.exceptions.IndexOutOfRangeException;
import ana.epj2.gui.*;
import ana.epj2.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

public class DataLoader {
    public static void loadVehicles()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(Simulation.APP_CONFIG_PATH));
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        Path vehiclesPath = new File(appProps.getProperty("PATH_VEHICLES")).toPath();
        Stream<String> content;
        try {
            content = Files.lines(vehiclesPath);
            content.forEach(p -> {
                try {
                    if (!p.contains("ID")) {
                        String[] line = p.split(",");
                        for (int i = 0; i < line.length; i++) {
                            line[i] = line[i].trim();
                        }
                        if(line.length != 9)
                        {
                            throw new IncorrectInputFormatException("Incorrect vehicle input line "+ p);
                        }
                        if (Simulation.vehicles.containsKey(line[0])) {
                            throw new DuplicateValueException("Duplicate vehicle ID "+line[0]);
                        }
                        switch (line[8].toLowerCase()) {//TODO da li je dobro rjesenje sa switch?
                            case "automobil":
                                Simulation.vehicles.put(line[0], new Car((line[0]), line[1], line[2], Double.parseDouble(line[4]), LocalDate.parse(line[3], formatter),
                                        (line[7])) {
                                });
                                break;
                            case "bicikl":
                                Simulation.vehicles.put(line[0], new Bicycle((line[0]), line[1], line[2], Double.parseDouble(line[4]),
                                        Double.parseDouble(line[5])) {
                                });
                                break;
                            case "trotinet":
                                Simulation.vehicles.put(line[0], new Scooter((line[0]), line[1], line[2], Double.parseDouble(line[4]), Double.parseDouble(line[6])) {
                                });
                                break;
                            default:
                                break;
                        }
                    }
                }catch(Exception ex)
                {
                    ex.printStackTrace();;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadRentals(VehicleMovementGUI vMGui) {
        Random random = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy H:mm");
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(Simulation.APP_CONFIG_PATH));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Path vehiclesPath = new File(appProps.getProperty("PATH_RENTALS")).toPath();
        Stream<String> content;
        try {
            content = Files.lines(vehiclesPath);
            content.forEach(p -> {
                try {
                    if (!p.contains("Datum")) {
                        String[] line = splitCSVLine(p);
                        for (int i = 0; i < line.length; i++) {
                            line[i] = line[i].trim();
                            if (line[i].equals("")) {
                                throw new IncorrectInputFormatException("Missing inputs in line " + p);
                            }
                        }
                        if (line.length != 8) {
                            throw new IncorrectInputFormatException("Incorrect rental input line " + p);
                        }
                        LocalDateTime datetime = LocalDateTime.parse(line[0], formatter);
                        User user = new User(line[1]);
                        String vehicleId = line[2];
                        String startLocation = line[3];
                        String endLocation = line[4];
                        int duration = Integer.parseInt(line[5]);
                        if ("da".equals(line[6])) {
                            Simulation.vehicles.get(vehicleId).setMalfunction(new Malfunction("malfunction" + random.nextInt(100), datetime));
                        }
                        boolean hasPromotion = line[7].equalsIgnoreCase("da");
                        Location start = new Location(Integer.parseInt(startLocation.split(",")[0]), Integer.parseInt(startLocation.split(",")[1]));
                        Location end = new Location(Integer.parseInt(endLocation.split(",")[0]), Integer.parseInt(endLocation.split(",")[1]));
                        if(!Simulation.vehicles.containsKey(line[2]))
                        {
                            throw new NonExistentValueException("Vehicle ID " + line[2] + " does not exist!");
                        }
                        Rental newRental = new Rental(datetime, user, Simulation.vehicles.get(line[2]), start, end, duration, hasPromotion, vMGui);
                        if (Simulation.rentals.contains(newRental)) {
                            throw new DuplicateValueException("Duplicate rental date " + line[0] + " vehicle ID " + line[2]);
                        }
                        if (!checkLocations(start, end)) {
                            throw new IndexOutOfRangeException("Location/s " + start + " " + end + " out of range!");
                        }
                        Simulation.rentals.add(newRental);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(Simulation.rentals, Comparator.comparing(Rental::getDateAndTime));
        for (Rental rental : Simulation.rentals) {
            LocalDateTime dateTime = rental.getDateAndTime();
            try {
                Simulation.blockOfRentals.computeIfAbsent(dateTime, k -> new ArrayList<>()).add(rental);
            } catch (ConcurrentModificationException ex) {
                ex.printStackTrace();
            }
        }
        for (Map.Entry<LocalDateTime, List<Rental>> entry : Simulation.blockOfRentals.entrySet()) {
            List<Rental> rentalList = entry.getValue();
            for (Rental rental : rentalList) {
                System.out.println(rental);
            }
        }
    }

    private static String[] splitCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            switch (c) {
                case '"':
                    inQuotes = !inQuotes;
                    break;
                case ',':
                    if (inQuotes) {
                        current.append(c);
                    } else {
                        result.add(current.toString());
                        current.setLength(0);
                    }
                    break;
                default:
                    current.append(c);
                    break;
            }
        }
        result.add(current.toString());

        return result.toArray(new String[0]);
    }
    public static boolean checkLocations(Location start, Location end)
    {
        return isWithinRange(start.getX(),0,19) && isWithinRange(start.getY(),0,19)
                && isWithinRange(end.getX(),0,19) && isWithinRange(end.getY(),0,19);
    }
    public static boolean isWithinRange(int value, int min, int max)
    {
        return value <= max && value >= min;
    }


}
