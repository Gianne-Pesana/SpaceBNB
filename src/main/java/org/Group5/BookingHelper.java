package org.Group5;

import java.util.ArrayList;
import java.util.Scanner;

public class BookingHelper {
    private static final Scanner scanner = new Scanner(System.in);
    private static final double VAT_RATE = 0.12; // 12% VAT rate

    // Get booking name from user
    public static String getBookingName() {
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

    // Get stay duration from user
    public static int getStayDuration() {
        int duration;
        while (true) {
            System.out.print("Enter number of days you wish to stay: ");
            try {
                duration = Integer.parseInt(scanner.nextLine());
                if (duration > 0) {
                    break;
                } else {
                    System.out.println("Duration must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return duration;
    }

    // Get number of people from user
    public static int getNumberOfPeople() {
        int people;
        while (true) {
            System.out.print("Enter number of people: ");
            try {
                people = Integer.parseInt(scanner.nextLine());
                if (people > 0) {
                    break;
                } else {
                    System.out.println("Number of people must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return people;
    }

    // Confirm booking with user
    public static boolean confirmBooking(Planets planet, Hotels hotel, Rooms room, int duration, int people, String bookingName) {
        System.out.println(Displays.titleHeader);
        Utils.clearConsole();
        System.out.println();
        double subtotal = room.getPricePerNight() * duration;
        double vatAmount = subtotal * VAT_RATE;
        double total = subtotal + vatAmount;
        
        System.out.println("\n=== Booking Summary ===");
        System.out.println("Planet: " + planet.getPlanetName());
        System.out.println("Hotel: " + hotel.getHotelName());
        System.out.println("Room: " + room.getRoomNumber() + " (" + room.getRoomType() + ")");
        System.out.println("Duration: " + duration + " days");
        System.out.println("Number of People: " + people);
        System.out.println("Booking Name: " + bookingName);
        System.out.println("------------------------------------");
        System.out.printf("Subtotal:         ₱%.2f\n", subtotal);
        System.out.printf("VAT (12%%):        ₱%.2f\n", vatAmount);
        System.out.println("------------------------------------");
        System.out.printf("Total:            ₱%.2f\n", total);
        
        while (true) {
            System.out.print("\nConfirm booking? (yes/no): ");
            String choice = scanner.nextLine().toLowerCase();
            if (choice.equals("yes")) {
                return true;
            } else if (choice.equals("no")) {
                return false;
            } else {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        }
    }

    // Show booking receipt
    public static void showReceipt(Booking booking) {
        System.out.println(Displays.titleHeader);
        Utils.clearConsole();
        System.out.println();
        double subtotal = booking.getRoom().getPricePerNight() * booking.getStayDuration();
        double vatAmount = subtotal * VAT_RATE;
        double total = subtotal + vatAmount;
        
        System.out.println("\n=== Booking Receipt ===");
        System.out.println("Booking ID: " + booking.getBookingID());
        System.out.println("Date: " + booking.getDateTime());
        System.out.println("Planet: " + booking.getPlanet().getPlanetName());
        System.out.println("Hotel: " + booking.getHotel().getHotelName());
        System.out.println("Room: " + booking.getRoom().getRoomNumber() + " (" + booking.getRoom().getRoomType() + ")");
        System.out.println("Duration: " + booking.getStayDuration() + " days");
        System.out.println("Number of People: " + booking.getNumberOfPeople());
        System.out.println("Booking Name: " + booking.getBookingName());
        System.out.println("------------------------------------");
        System.out.printf("Subtotal:         ₱%.2f\n", subtotal);
        System.out.printf("VAT (12%%):        ₱%.2f\n", vatAmount);
        System.out.println("------------------------------------");
        System.out.printf("Total:            ₱%.2f\n", total);
        System.out.println("=====================");
    }

    // Display transaction history
    //TODO: FIX UI
    public static void displayTransactionHistory(ArrayList<String> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\n=== Transaction History ===");
        
        // Define column widths
        int idWidth = 20;      // Booking ID
        int nameWidth = 20;    // Booking Name
        int dateWidth = 20;    // Date
        int planetWidth = 15;  // Planet
        int hotelWidth = 30;   // Hotel
        int roomWidth = 10;    // Room
        int daysWidth = 6;     // Days
        int paxWidth = 5;      // Pax
        int totalWidth = 12;   // Total

        // Calculate total width for separator line
        int totalTableWidth = idWidth + nameWidth + dateWidth + planetWidth + 
                            hotelWidth + roomWidth + daysWidth + paxWidth + totalWidth + 8; // 8 for spaces between columns

        // Print header
        System.out.printf("%-" + idWidth + "s %-" + nameWidth + "s %-" + dateWidth + "s %-" + 
                         planetWidth + "s %-" + hotelWidth + "s %-" + roomWidth + "s %-" + 
                         daysWidth + "s %-" + paxWidth + "s %-" + totalWidth + "s%n",
                "Booking ID", "Name", "Date", "Planet", "Hotel", "Room", "Days", "Pax", "Total");

        // Print separator line
        for (int i = 0; i < totalTableWidth; i++) {
            System.out.print("-");
        }
        System.out.println();

        // Print transaction rows
        for (String transaction : transactions) {
            String[] parts = transaction.split("\\|");
            if (parts.length >= 10) {
                System.out.printf("%-" + idWidth + "s %-" + nameWidth + "s %-" + dateWidth + "s %-" + 
                                planetWidth + "s %-" + hotelWidth + "s %-" + roomWidth + "s %-" + 
                                daysWidth + "s %-" + paxWidth + "s ₱%-" + (totalWidth-1) + ".2f%n",
                        parts[0],  // Booking ID
                        parts[8],  // Booking Name
                        parts[1],  // Date
                        parts[2],  // Planet
                        parts[3],  // Hotel
                        parts[4],  // Room
                        parts[6],  // Days
                        parts[7],  // Pax
                        Double.parseDouble(parts[9])); // Total Price (including VAT)
            }
        }

        // Print bottom separator
        for (int i = 0; i < totalTableWidth; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
} 