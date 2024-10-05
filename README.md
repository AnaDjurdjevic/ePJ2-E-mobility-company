Short description
ePJ2 is an e-mobility company that rents electric cars, bicycles, and scooters in the city of Java. 
The goal of this application is to simulate the usage of these vehicles based on predefined data, 
generate detailed financial reports, track vehicle status, and provide business statistics. 
The system manages vehicle rentals, calculates costs based on time, distance, and other factors (like breakdowns or promotions),
and displays real-time simulations of vehicle movements on a map.

Project assignment text
ePJ2 is an e-mobility company that rents electric cars, bicycles, and scooters in the narrow and wider areas of the city of Java. The goal of this project is to develop a program that simulates the usage of these vehicles based on predefined data, generates detailed financial reports, provides statistics, and tracks the status of all vehicles in use.

The company manages a number of cars, with the following data stored: unique identifier (ID), purchase date, purchase price, manufacturer, model, description, and current battery level. For electric bicycles, the following data is stored: unique identifier (ID), manufacturer, model, purchase price, current battery level, and range per charge (autonomy). For electric scooters, the stored data includes: unique identifier (ID), manufacturer, model, current battery level, purchase price, and maximum speed. All vehicles can experience breakdowns, for which the reason (description), date, and time are recorded. All vehicles have battery charging capabilities (a method that increases the current level). Battery discharge occurs during movement, and cars have the capacity to transport multiple passengers.

The core business of the company is vehicle rental. During the rental process, the date and time of the rental, the user's name, the current location where the vehicle was picked up, the location where the vehicle is returned after use, and the duration of use in seconds are recorded. For car rentals, an identification document (passport for foreign citizens and ID card for locals) and a driver's license (number) must be provided. Based on this information, a payment invoice is generated and delivered to the users as a txt file.

The company defines rental prices for each type of vehicle based on the usage time in seconds. The total payment amount is calculated based on the duration of the ride and additional factors. These factors include the use of the vehicle in the wider city area, breakdowns, discounts, and promotions. The calculation method is given in a table, and all parameters are stored externally in properties files (https://www.baeldung.com/java-properties).

![table](https://github.com/user-attachments/assets/d06ae13f-73b3-4e21-ad6b-9f23e0b91ece)
Example calculation:
Amount (base price * distance) = ((SCOOTER_UNIT_PRICE * Duration) * DISTANCE_WIDE)
Total payment (Amount - discount - promotion) = Amount - (DISCOUNT * Amount) - (DISCOUNT_PROM * Amount)

All items must be listed individually on the invoices. If a breakdown occurs, the total payment is 0.
All data on vehicles and rentals will be available on the course's Moodle page. For each project defense, the test data may be changed, so nothing should be hardcoded.

The rental data is used to simulate the company's operations. The data is read from a file one row at a time, sorted by the rental time, and this data is used to display the movement on the map in separate threads for each row. It is mandatory that the simulation follows the rental order (date and time). One thread is associated with only one rental, while the map is shared among all threads. The simulation runs for the number of seconds corresponding to the rental duration. Since the starting and ending points of the rental are known, students must create a direct path between them and move the display on the map accordingly. The display can be implemented by marking map fields with a different color and displaying the vehicle identifier and battery level. After the simulation of all rentals for a given date and time is completed, there should be a 5-second pause, after which the next set of rentals is simulated. Once the simulation of a rental is completed, an invoice is generated containing all the data related to that rental and the total payment amount. Additionally, items related to discounts or promotions, if applicable, should be listed. The files are saved in a folder at a specific path (defined in the properties file).
The program should have several graphical interfaces for data display, which can be implemented using JavaFX or Swing GUI:

Main screen to display the map,
Screen for displaying all vehicles (3 tables, showing all information),
Screen for displaying breakdowns (1 table, columns: vehicle type, ID, time, breakdown description), and
Screen for displaying business results.
The map consists of a 20x20 grid. The fields marked in white (outer) represent the wider part of the city, while the fields marked in blue represent the narrow part of the city. The field 0,0 is the top left, and the field 19,19 is the bottom right. The test data is defined in this way. The allowed paths are strictly straight lines, and the simulation duration is divided by the number of fields the vehicle will cross on the map, which defines the time spent on each field. Vehicle positions on the map are displayed in real time.

The business results should display all revenues (amounts from generated invoices) and all additional parameters relevant to the business. There are two types of reports: summary and daily. For the summary report, the following data should be displayed:

Total revenue (sum of all payment amounts on all invoices),
Total discount (sum of all discount amounts from all invoices),
Total promotions (sum of all promotion values from all invoices),
Total revenue from rides in the narrow and wide parts of the city,
Total maintenance cost (total revenue * 0.2),
Total repair costs (coefficient (cars 0.07, bicycles 0.04, scooters 0.02) * purchase price of the broken vehicle). This is calculated as the sum of all individual breakdowns.
Total company costs (20% of total revenue), and
Total tax (total revenue - total maintenance costs - total repair costs - company costs) * 10%.
Daily reports display items 1-6 grouped by date. All available dates should be shown. This report is presented in a table format.
Depending on the index number, one additional functionality from the following three must be implemented (e.g., 1234/24 => sum of digits is 1+2+3+4+2+4 = 16, (16%3 + 1)=2):

Finding the vehicles that generated the most revenue for each type,
Finding the vehicles that incurred the most losses for each type,
Finding the vehicles that broke down and calculating the repair costs.
These data are individually serialized into binary files, where the vehicle object with all fields and amounts is stored in a specific folder. It is also necessary to implement a screen where these data can be deserialized and displayed.
![Screenshot (324)](https://github.com/user-attachments/assets/a51f5f8f-ee16-46a2-9bf9-14a03f1c3a52)
![Screenshot (325)](https://github.com/user-attachments/assets/71d6abbc-a3f4-44bf-bd0a-5ee3bf144586)
![Screenshot (326)](https://github.com/user-attachments/assets/3aa20283-e908-4804-b363-8036f69a8d0f)
![Screenshot (327)](https://github.com/user-attachments/assets/3c069177-1961-479c-a659-b74588b84192)
![Screenshot (328)](https://github.com/user-attachments/assets/471fbcaa-af60-46cf-b749-21448a3ad908)
![Screenshot (329)](https://github.com/user-attachments/assets/340b918e-7d68-40a3-8fe7-6590415a0735)
![Screenshot (330)](https://github.com/user-attachments/assets/7d2c05fb-b0b0-4669-ba31-d8c72e6aaae4)
![Screenshot (331)](https://github.com/user-attachments/assets/ac6884bb-fa83-4129-8271-bf9f7bee0bb4)
