package org.Group5;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Random;

public class Utils {
    // Password for accessing transaction history
    private static final String PASSWORD = "1234";
    private static final Scanner scanner = new Scanner(System.in);

    // Get current date and time in a formatted string
    public static String getDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dateTime.format(date) + "," + dateTime.format(time);
    }

    // Generate a unique booking ID
    public static String generateBookingID() {
        long timestamp = System.currentTimeMillis() / 1000; // Timestamp in seconds
        Random random = new Random();
        int randomNumber = random.nextInt(1000); // Random number between 0 and 999
        return String.format("BKG-%d-%03d", timestamp, randomNumber);
    }

    // Format a string to proper case (e.g., "john doe" -> "John Doe")
    public static String formatString(String input) {
        input = input.trim();
        if (!input.contains(" ")) {
            return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        }
        return input.substring(0, 1).toUpperCase()
                + input.substring(1, input.indexOf(" ")).toLowerCase()
                + " "
                + formatString(input.substring(input.indexOf(" ") + 1));
    }

    // Clear the console screen
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Wait for user to press any key
    public static void pressAnyKeyToContinue() {
        System.out.println("\nPress any key to continue...");
        scanner.nextLine();
    }

    // Sleep for 1 second
    public static void threadSleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Sleep for specified milliseconds
    public static void threadSleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Validate password for accessing transaction history
    public static boolean validate() {
        clearConsole();
        System.out.println(Displays.titleHeader);
        System.out.print("Enter password: ");
        String input = scanner.nextLine();
        return input.equals(PASSWORD);
    }
}