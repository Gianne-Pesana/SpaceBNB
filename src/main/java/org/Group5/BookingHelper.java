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
            System.out.print("Enter number of nights you wish to stay: ");
            duration = scanner.nextInt();
            if (duration > 0) {
                break;
            } else {
                System.out.println("Duration must be greater than 0.");
            }
        }
        return duration;
    }

    // Get number of people from user
    public static int getNumberOfPeople() {
        int people;
        while (true) {
            System.out.print("Enter number of people: ");
            people = scanner.nextInt();
            if (people > 0) {
                break;
            } else {
                System.out.println("Number of people must be greater than 0.");
            }
        }
        return people;
    }

    // Confirm booking with user
    public static boolean confirmBooking(Planets planet, Hotels hotel, Rooms room, int duration, int people, String bookingName) {
        Scanner scanner = new Scanner(System.in);
        Utils.clearConsole();

        int headRowLen = ((room.getRoomDetails().length() - 22) / 2) + 10;
        int separatorLen = room.getRoomDetails().length() + 20;

        // Placeholder for ASCII art header
        System.out.println(Displays.titleHeader);// Replace with your ASCII art header


        System.out.println("\n" + "=".repeat(headRowLen) + " Booking Confirmation " + "=".repeat(headRowLen));
        System.out.println();

        double subtotal = room.getPricePerNight() * duration;
        double vatRate = 0.12;
        double vatAmount = subtotal * vatRate;
        double total = subtotal + vatAmount;

        System.out.printf("Booking Name:      %s\n", bookingName);
        System.out.printf("Destination:       %s\n", planet.getPlanetName());
        System.out.printf("Hotel:             %s\n", hotel.getHotelName());
        System.out.printf("Room:              %s\n", room.getRoomDetails());
        System.out.printf("Stay Duration:     %d nights\n", duration);
        System.out.printf("Number of People:  %d\n", people);
        System.out.println("-".repeat(separatorLen));
        System.out.printf("Subtotal:          ₱%,.2f\n", subtotal);
        System.out.printf("VAT (12%%):        ₱%,.2f\n", vatAmount);
        System.out.println("-".repeat(separatorLen));
        System.out.printf("Total:             ₱%,.2f\n", total);
        System.out.println("-".repeat(separatorLen));

        System.out.print("\nWould you like to confirm your booking? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        return response.equals("yes");
    }

    // Show booking receipt
    public static void showReceipt(Booking booking) {
        Utils.clearConsole();
        String receiptTitle =
                            " ____                       ____  _   _ ____  \n" +
                            "/ ___| _ __   __ _  ___ ___| __ )| \\ | | __ ) \n" +
                            "\\___ \\| '_ \\ / _` |/ __/ _ \\  _ \\|  \\| |  _ \\ \n" +
                            " ___) | |_) | (_| | (_|  __/ |_) | |\\  | |_) |\n" +
                            "|____/| .__/ \\__,_|\\___\\___|____/|_| \\_|____/ \n" +
                            "      |_|                                     ";
        System.out.println(receiptTitle);
        System.out.println("  🚀 Intergalactic Space Booking System 🌌");
        System.out.println("==============================================");
        System.out.println();

        double subtotal = booking.getRoom().getPricePerNight() * booking.getStayDuration();
        double vatAmount = subtotal * VAT_RATE;
        double total = subtotal + vatAmount;

        System.out.println("    🧾 ===== Booking Receipt ===== 🧾");
        System.out.println("🔗 Booking ID:        " + booking.getBookingID());
        System.out.println("📅 Booking Date:      " + booking.getDateTime());
        System.out.println("👤 Booking Name:      " + booking.getBookingName());
        System.out.println("🪐 Planet:            " + booking.getPlanet().getPlanetName());
        System.out.println("🏨 Hotel:             " + booking.getHotel().getHotelName());
        System.out.println("🛏️ Room:              " + booking.getRoom().getRoomNumber() + " (" + booking.getRoom().getRoomType() + ")");
        System.out.println("⏳ Duration:          " + booking.getStayDuration() + " nights");
        System.out.println("👥 Number of People:  " + booking.getNumberOfPeople());
        System.out.println("----------------------------------------------");
        System.out.printf("💰 Subtotal:         ₱%,.2f\n", subtotal);
        System.out.printf("🧾 VAT (12%%):       ₱%,.2f\n", vatAmount);
        System.out.println("----------------------------------------------");
        System.out.printf("💳 Total:            ₱%,.2f\n", total);
        System.out.println("==============================================");
        System.out.println();
        System.out.println("🙏 Thank you for booking with us! 🙏");
        System.out.println("✨ We wish you a pleasant journey through the stars! ✨");
    }


    // Display transaction history
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