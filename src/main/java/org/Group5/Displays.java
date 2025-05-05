package org.Group5;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;


public class Displays {
    private static final String TRANSACTIONS_PATH = "transactions\\transactions.txt";
    public static String titleHeader =
            "███████╗██████╗  █████╗  ██████╗███████╗██████╗ ███╗   ██╗██████╗ \n" +
            "██╔════╝██╔══██╗██╔══██╗██╔════╝██╔════╝██╔══██╗████╗  ██║██╔══██╗\n" +
            "███████╗██████╔╝███████║██║     █████╗  ██████╔╝██╔██╗ ██║██████╔╝\n" +
            "╚════██║██╔═══╝ ██╔══██║██║     ██╔══╝  ██╔══██╗██║╚██╗██║██╔══██╗\n" +
            "███████║██║     ██║  ██║╚██████╗███████╗██████╔╝██║ ╚████║██████╔╝\n" +
            "╚══════╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚══════╝╚═════╝ ╚═╝  ╚═══╝╚═════╝ \n" +
            "==================================================================\n";

        public static String getOperation() {
            String choice;
            Scanner scanner = new Scanner(System.in);
            while (true) {
                Utils.clearConsole();
                System.out.println(titleHeader);
                System.out.println("Select operation:");
                System.out.println("[1] Book hotel room");
                System.out.println("[2] View receipts");
                System.out.println();
                System.out.print("Enter operation choice: ");
                choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("quit")) return "quit";

                if (choice.equals("1") || choice.equals("2")) break;
                else System.out.println("Invalid choice!");
            }

            return choice;
        }

    public static void welcomeMessage() {
        String message = "Welcome to SpaceBNB! 🌌\n" +
                "Ready to boldly go where many have only dreamed of sleeping? ✨\n" +
                "Your cosmic concierge awaits to guide you through stellar stays in galaxies both near and... slightly further. 🚀\n\n" +
                "So, where's your next orbit taking you?";
        System.out.println(titleHeader + message);
    }

    public static void showPlanetMenu(ArrayList<Planets> planetsList) {
        if (planetsList.isEmpty()) {
            System.out.println("Error: Planet List is Empty!");
            return;
        }

        System.out.println("--- Select Your Interstellar Destination ---");
        System.out.println("|-----|---------------|------------------------------------------|------------------------------------------|");
        System.out.println("| No. | Planet        | Climate                                  | Distance from Earth                      |");
        System.out.println("|-----|---------------|------------------------------------------|------------------------------------------|");

        for (int i = 0; i < planetsList.size(); i++) {
            Planets planet = planetsList.get(i);
            System.out.printf("| %-3d | %-13s | %-40s | %-40s |\n",
                    i + 1,
                    planet.getPlanetName(),
                    planet.getClimate(),
                    planet.getDistance());
        }
        System.out.println("|-----|---------------|------------------------------------------|------------------------------------------|");
        System.out.println();
    }

    public static Planets getPlanetChoice(ArrayList<Planets> planetsList) {
        int choice;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showPlanetMenu(planetsList);

            System.out.print("Enter the number of your desired planet: ");
            choice = scanner.nextInt();

            if (choice == -1) return null; // sentinel value

            if (choice >= 1 && choice <= planetsList.size()) {
                break; // valid input, exit loop
            } else {
                System.out.println("Error: Invalid planet number!");
            }

            Utils.threadSleep();
            Utils.clearConsole();
        }
        return planetsList.get(choice - 1); // Return the selected Planet object
    }

    // Modified return type to Hotels object, removed try/catch around nextInt
    public static Hotels getHotelChoice(ArrayList<Hotels> hotelList, String planetName) {
        int choice;
        Scanner scanner = new Scanner(System.in);

        if (hotelList.isEmpty()) {
            System.out.println("\nNo hotels found on " + planetName + ".");
            Utils.pressAnyKeyToContinue();
            return null; // Indicate no hotels available
        }

        while (true) {
            Utils.clearConsole(); // Clear before showing menu
            System.out.println(titleHeader);
            System.out.println("\nYou've chosen to explore the wonders of " + planetName + "!");
            System.out.println("\nNow, select your home away from home:");
            showHotelMenu(hotelList);

            System.out.print("Enter the number of the hotel where you wish to reside: ");
            choice = scanner.nextInt();

            if (choice >= 1 && choice <= hotelList.size()) {
                break; // valid input, exit loop
            } else {
                System.out.println("Error: Invalid hotel number!");
            }

            Utils.threadSleep();
        }
        return hotelList.get(choice - 1); // Return the selected Hotels object
    }

    public static void showHotelMenu(ArrayList<Hotels> hotelList) {
        System.out.println("--------------------------------------------");
        for (int i = 0; i < hotelList.size(); i++) {
            Hotels hotel = hotelList.get(i);
            System.out.println("#" + (i + 1));
            System.out.println(hotel.getHotelName());
            System.out.println("Rating: " + hotel.renderRating());
            System.out.println("--------------------------------------------");
        }
    }


    public static void showRoomMenu(ArrayList<Rooms> roomsList) {
        // Manually filter for only available rooms
        ArrayList<Rooms> availableRooms = new ArrayList<>();
        for (Rooms room : roomsList) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }

        if (availableRooms.isEmpty()) {
            System.out.println("\nNo available rooms at this time.");
            return; // No rooms to show
        }

        System.out.println("\n--- Available Rooms ---");
        System.out.println("|---------|----------|--------------|---------------------------------------------------|");
        System.out.println("| Room ID | Type     | Price/Night  | Details                                           |");
        System.out.println("|---------|----------|--------------|---------------------------------------------------|");

        for (Rooms room : availableRooms) {
            String specificDetails = "N/A"; // Default placeholder

            // Use instanceof and casting to access specific getters for details column
            if (room instanceof LuxuryRoom) {
                LuxuryRoom lr = (LuxuryRoom) room;
                specificDetails = String.format("Amenities: %s, Butler: %s", lr.getSpecialAmenities(), lr.isHasButlerService() ? "Yes" : "No");
            } else if (room instanceof StandardRoom) {
                StandardRoom sr = (StandardRoom) room;
                specificDetails = String.format("Bed Size: %s, Balcony: %s", sr.getBedSize(), sr.isHasBalcony() ? "Yes" : "No");
            } else if (room instanceof EconomyRoom) {
                EconomyRoom er = (EconomyRoom) room;
                specificDetails = String.format("Bed Type: %s, Size: %s", er.getBedType(), er.getRoomSize());
            }


            System.out.printf("| %-7s | %-8s | ₱%-11.2f | %-50s |\n",
                    room.getRoomNumber(),
                    room.getRoomType(),
                    room.getPricePerNight(),
                    specificDetails);
        }
        System.out.println("|---------|----------|--------------|---------------------------------------------------|");
        System.out.println();
    }

    public static Rooms getRoomChoice(ArrayList<Rooms> roomsList) {
        Scanner scanner = new Scanner(System.in);
        // Manually filter for only available rooms to validate against
        ArrayList<Rooms> availableRooms = new ArrayList<>();
        for (Rooms room : roomsList) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms were available for selection.");
            Utils.pressAnyKeyToContinue();
            return null; // Indicate no rooms are available
        }

        String chosenRoomNumber;
        Rooms selectedRoom = null; // Will hold the selected room object

        while (selectedRoom == null) { // Loop until a valid, available room number is entered
            showRoomMenu(roomsList); // Show the menu (it filters internally)

            System.out.print("Enter the Room Number of your desired room: ");
            chosenRoomNumber = scanner.nextLine().trim(); // Read the room number as String

            // Check if the entered room number exists in the available rooms
            for (Rooms room : availableRooms) {
                // Use case-insensitive comparison for flexibility
                if (room.getRoomNumber().equalsIgnoreCase(chosenRoomNumber)) {
                    selectedRoom = room; // Found the matching room
                    break; // Exit the inner loop
                }
            }

            if (selectedRoom == null) {
                System.out.println("Error: Invalid Room Number or room is not available!");
                Utils.threadSleep();
                Utils.clearConsole();
                System.out.println(titleHeader); // Redisplay header
                // The showRoomMenu call at the start of the loop will redisplay the rooms
            }
        }

        return selectedRoom; // Return the selected room object
    }

    public static String getBookingName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your booking name: ");
        String name = scanner.nextLine();
        // Basic validation: ensure name is not empty
        while (name.trim().isEmpty()) {
            System.out.println("Booking name cannot be empty.");
            System.out.print("Enter your booking name: ");
            name = scanner.nextLine();
        }
        return Utils.formatString(name);
    }


    public static int getStayDuration() {
        Scanner scanner = new Scanner(System.in);
        int duration;
        while (true) {
            System.out.print("Enter number of days you wish to stay: ");
            duration = scanner.nextInt();
            if (duration > 0) {
                break; // Valid input
            } else {
                System.out.println("Error: Stay duration must be at least one day.");
            }
        }
        scanner.nextLine(); // Consume newline
        return duration;
    }

    // Updated method to get number of people with validation (max 7)
    public static int getNumberOfPeople() { // Return int
        Scanner scanner = new Scanner(System.in);
        int people;
        final int MAX_PEOPLE = 7; // Define max people

        while (true) {
            System.out.print("Enter number of people staying (Max " + MAX_PEOPLE + "): ");
            people = scanner.nextInt();

            if (people > 0 && people <= MAX_PEOPLE) {
                break; // Valid input
            } else if (people <= 0) {
                System.out.println("Error: Number of people must be at least one.");
            } else {
                System.out.println("Error: Maximum number of people allowed is " + MAX_PEOPLE + ".");
            }
        }
        scanner.nextLine(); // Consume newline
        return people; // Return as int
    }

    // Updated confirmBooking to show detailed summary with CORRECTED VAT calculation
    public static boolean confirmBooking(Planets planet, Hotels hotel, Rooms room, int duration, int people, String bookingName) {
        Scanner scanner = new Scanner(System.in);
        Utils.clearConsole();
        System.out.println(titleHeader);
        System.out.println("\n--- Booking Summary ---");

        double subtotal = room.getPricePerNight() * duration;
        double vatRate = 0.12;
        // VAT calculation: 12% of the subtotal
        double vatAmount = subtotal * vatRate;
        double total = subtotal + vatAmount;

        System.out.println("Booking Name: " + bookingName);
        System.out.println("Destination: " + planet.getPlanetName());
        System.out.println("Hotel: " + hotel.getHotelName());
        System.out.println("Room: " + room.getRoomDetails()); // Use the full room details
        System.out.println("Stay Duration: " + duration + " nights");
        System.out.println("Number of People: " + people);
        System.out.println("------------------------------------");
        System.out.printf("Subtotal:         ₱%.2f\n", subtotal);
        System.out.printf("VAT (12%%):        ₱%.2f\n", vatAmount);
        System.out.println("------------------------------------");
        System.out.printf("Total:            ₱%.2f\n", total);
        System.out.println("------------------------------------");


        System.out.print("\nConfirm booking? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes");
    }

    public static void showReceipt(Booking booking) {
        Utils.clearConsole();
        System.out.println(titleHeader);
        System.out.println("\n--- Booking Confirmation Receipt ---");
        System.out.println("===================================="); // Header separator

        // Access details from the Booking object
        System.out.println("Booking ID:   " + booking.getBookingID());
        System.out.println("Booking Name: " + booking.getBookingName());

        // Format date and time nicely
        String[] dateTimeParts = booking.getDateTime().split(",");
        String date = dateTimeParts[0];
        String time = dateTimeParts[1];
        System.out.println("Date:         " + date);
        System.out.println("Time:         " + time);

        System.out.println("------------------------------------");
        System.out.println("Destination:  " + booking.getPlanet().getPlanetName());
        System.out.println("Hotel:        " + booking.getHotel().getHotelName());
        System.out.println("Room:         " + booking.getRoom().getRoomDetails()); // Use full room details
        System.out.println("Stay Duration:" + booking.getStayDuration() + " nights");
        System.out.println("Number of People: " + booking.getNumberOfPeople());

        // Calculate totals again for the receipt using VAT
        double subtotal = booking.getRoom().getPricePerNight() * booking.getStayDuration();
        double vatRate = 0.12;
        double vatAmount = subtotal * vatRate; // Corrected VAT calculation
        double total = subtotal + vatAmount;

        System.out.println("------------------------------------");
        System.out.printf("Subtotal:         ₱%.2f\n", subtotal);
        System.out.printf("VAT (12%%):        ₱%.2f\n", vatAmount);
        System.out.println("------------------------------------");
        System.out.printf("Total Paid:       ₱%.2f\n", total);
        System.out.println("====================================");

        System.out.println("\nThank you for choosing SpaceBNB for your cosmic travels!");
        System.out.println("We hope you have a stellar stay!");
        System.out.println("Safe travels across the galaxy! ✨🚀");

        Utils.pressAnyKeyToContinue();
        Utils.clearConsole();
    }

    // Method to save booking details to file
    public static void saveBookingToFile(Booking booking) {
        String filePath = TRANSACTIONS_PATH;

        // Calculate totals for saving
        double subtotal = booking.getRoom().getPricePerNight() * booking.getStayDuration();
        double vatRate = 0.12;
        double vatAmount = subtotal * vatRate; // Corrected VAT calculation
        double total = subtotal + vatAmount;

        // true for append mode
        try (FileWriter writer = new FileWriter(filePath, true)) {

            String bookingRecord = String.format(
                    "%s|%s|%s|%s|%s|%s|%d|%d|%.2f",
                    booking.getBookingID(),       // 1
                    booking.getBookingName(),     // 2
                    booking.getDateTime(),        // 3
                    booking.getPlanet().getPlanetName(), // 4
                    booking.getHotel().getHotelName(),   // 5
                    booking.getRoom().getRoomNumber(), // Added room number (6)
                    booking.getStayDuration(),    // 7
                    booking.getNumberOfPeople(),  // 8
                    total                         // 9
            );

            // Write the formatted string to the file
            writer.append(bookingRecord + "\n");

            System.out.println("\nBooking successfully saved to " + filePath);

        } catch (IOException e) {
            System.err.println("Error saving booking to file: " + e.getMessage());
        }
    }

    public static void viewTransactions() {
        Utils.clearConsole();
        System.out.println(titleHeader);
        System.out.println();
        System.out.println("Transactions");

        ArrayList<String[]> rows = new ArrayList<>();
        int nameIndex = 1; // Name is at index 1
        int nameColWidth = "Name".length(); // Initial width from header

        try (Scanner inFile = new Scanner(new FileReader(TRANSACTIONS_PATH))) {
            while (inFile.hasNextLine()) {
                String line = inFile.nextLine().trim();
                String[] parts = line.split("\\|");

                if (parts.length != 9) {
                    System.out.println("Transaction file invalid, skipping...");
                    continue;
                }

                rows.add(parts);

                // Update name column width if needed
                if (parts[nameIndex].length() > nameColWidth) {
                    nameColWidth = parts[nameIndex].length();
                }
            }
        } catch (IOException e) {
            System.out.println("Error in opening transactions file.");
            return;
        }

        nameColWidth += 2; // buffer spacing for name column

        // Print header
        System.out.printf(
                "%-20s %-"+nameColWidth+"s %-20s %-15s %-30s %-10s %-6s %-5s %-10s%n",
                "Booking ID", "Name", "Date", "Planet", "Hotel", "Room No.", "Days", "Pax", "Total"
        );

        // Print separator line
        int totalWidth = 20 + nameColWidth + 20 + 15 + 30 + 10 + 6 + 5 + 10 + (7);
        for (int i = 0; i < totalWidth; i++) System.out.print("-");
        System.out.println();

        // Print rows
        for (String[] row : rows) {
            System.out.printf(
                    "%-20s %-"+nameColWidth+"s %-20s %-15s %-30s %-10s %-6s %-5s %-10s%n",
                    row[0], // Booking ID
                    row[1], // Name (dynamic)
                    row[2], // Date
                    row[3], // Planet
                    row[4], // Hotel
                    row[5], // Room No.
                    row[6], // Days
                    row[7], // Pax
                    row[8]  // Total
            );
        }
    }
}