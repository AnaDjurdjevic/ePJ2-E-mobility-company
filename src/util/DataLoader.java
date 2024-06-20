package util;

import exceptions.DuplicateValueException;
import exceptions.IncorrectInputFormatException;
import exceptions.IndexOutOfRangeException;
import gui.*;
import model.*;

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
    //TODO mozda vidjeti bolji nacin sa manje dupliranja koda
    public static void loadVehicles()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(Simulation.appConfigPath));
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
                        switch (line[8]) {//TODO da li je dobro rjesenje sa switch?
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
    public static void loadRentals(GridPanel gridPanel) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy H:mm");
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(Simulation.appConfigPath));
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        Path vehiclesPath = new File(appProps.getProperty("PATH_RENTALS")).toPath();
        Stream<String> content;
        try {
            content = Files.lines(vehiclesPath);
            content.forEach(p -> {
                try {
                    if (!p.contains("Datum")) {
                        String[] line = p.split(",");
                        for (int i = 0; i < line.length; i++) {
                            line[i] = line[i].trim();
                            if(line[i].equals(""))
                            {
                                throw new IncorrectInputFormatException("Missing inputs!");
                            }
                        }
                        if(line.length != 10)
                        {
                            throw new IncorrectInputFormatException("Incorrect rental input line "+ p);
                        }
                        LocalDateTime datetime = LocalDateTime.parse(line[0], formatter);
                        User user = new User(line[1]);
                        String vehicleId = line[2];
                        String startLocationX = line[3].replace("\"","");
                        String startLocationY = line[4].replace("\"","");;
                        String endLocationX = line[5].replace("\"","");;
                        String endLocationY = line[6].replace("\"","");;
                        int duration = Integer.parseInt(line[7]);
                        if ("da".equals(line[8])) {
                            Simulation.vehicles.get(vehicleId).setMalfunction(new Malfunction("", LocalDateTime.now()));
                        }
                        boolean hasPromotion = line[9].equalsIgnoreCase("da");
                        Location start = new Location(Integer.parseInt(startLocationX), Integer.parseInt(startLocationY));
                        Location end = new Location(Integer.parseInt(endLocationX), Integer.parseInt(endLocationY));
                        Rental newRental = new Rental(datetime, user, Simulation.vehicles.get(line[2]), start, end, duration, hasPromotion, gridPanel);
                        if (Simulation.rentals.contains(newRental)) {
                            throw new DuplicateValueException("Duplicate rental date "+line[0]+" vehicle ID "+line[2]);
                        }
                        if(!checkLocations(start,end))
                        {
                            throw new IndexOutOfRangeException("Location/s " + start + " " + end + " out of range!");
                        }
                        Simulation.rentals.add(newRental);

                    }
                }catch(Exception ex)
                //TODO kako rijesiti hvatanje svih ostalih izuzetaka npr pogresan datum
                {
                    ex.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        // prebaciti u posebnu metodu
        //TODO Sortiranje iznajmljivanja po datumu i vremenu
        //treba napraviti blokove iznajmljivanja
        Collections.sort(Simulation.rentals, Comparator.comparing(Rental::getDateAndTime));

        // Pokretanje svake simulacije u zasebnoj niti
        /*for (Rental rental : Simulation.rentals) {
            rental.start();

            try {
                rental.join(); // Čekanje da se trenutna simulacija završi pre početka sledeće
                Thread.sleep(5000); // Pauza od 5 sekundi između simulacija
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
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
