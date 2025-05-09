package org.Group5;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Main program loop
        while (true) {
            // Show main menu and get user choice
            String choice = Displays.showMainMenu();
            // Handle user choice
            switch (choice) {
                case "1":
                    // Start booking process
                    startBooking();
                    break;
                case "2":
                    // View transaction history
                    viewTransactionHistory();
                    break;
                case "3":
                    System.out.println("Thank you for using SpaceBNB. Goodbye! Ingat.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to start the booking process
    private static void startBooking() {
        // Clear screen and show welcome message
        Utils.clearConsole();
        Displays.welcomeMessage();
        
        // Step 1: Select planet
        Planets selectedPlanet = selectPlanet();
        if (selectedPlanet == null) {
            System.out.println("No planet selected. Returning to main menu.");
            return;
        }
        
        // Step 2: Select hotel
        Hotels selectedHotel = selectHotel(selectedPlanet);
        if (selectedHotel == null) {
            System.out.println("No hotel selected. Returning to main menu.");
            return;
        }
        
        // Step 3: Select room
        Rooms selectedRoom = selectRoom(selectedHotel, selectedPlanet);
        if (selectedRoom == null) {
            System.out.println("No room selected. Returning to main menu.");
            return;
        }
        
        // Step 4: Process booking
        processBooking(selectedPlanet, selectedHotel, selectedRoom);
    }
    
    // Method to select a planet
    private static Planets selectPlanet() {
        // Get list of available planets
        ArrayList<Planets> planets = FileHelper.readPlanetData();
        
        // Show planet selection menu and get user choice
        return Displays.getPlanetChoice(planets);
    }
    
    // Method to select a hotel
    private static Hotels selectHotel(Planets planet) {
        // Get list of hotels for selected planet
        ArrayList<Hotels> hotels = FileHelper.readHotelData(planet.getPlanetName());
        
        // Show hotel selection menu and get user choice
        return Displays.getHotelChoice(hotels, planet.getPlanetName());
    }
    
    // Method to select a room
    private static Rooms selectRoom(Hotels hotel, Planets planet) {
        // Clear screen and show room selection header
        Utils.clearConsole();
        System.out.println(Displays.titleHeader);
        System.out.println("\nYou've selected " + hotel.getHotelName() + " on " + planet.getPlanetName() + "!");
        System.out.println("\nLoading available rooms...");
        Utils.threadSleep(500);
        
        // Show room selection menu and get user choice
        return Displays.getRoomChoice(hotel.getRooms());
    }
    
    // Method to process the booking
    private static void processBooking(Planets planet, Hotels hotel, Rooms room) {
        // Clear screen and show booking form
        Utils.clearConsole();
        System.out.println(Displays.titleHeader);
        System.out.println("\n------- Enter Booking Details ------");
        
        // Get booking information
        String bookingName = BookingHelper.getBookingName();
        int stayDuration = BookingHelper.getStayDuration();
        int numberOfPeople = BookingHelper.getNumberOfPeople();
        
        // Generate booking ID and get current date/time
        String bookingID = Utils.generateBookingID();
        String dateTime = Utils.getDateTime();
        
        // Show booking summary and get confirmation
        boolean confirmed = BookingHelper.confirmBooking(planet, hotel, room, stayDuration, numberOfPeople, bookingName);
        
        if (confirmed) {
            // Update room availability
            room.setAvailable(false);
            FileHelper.updateRoomAvailability(planet, hotel, room);
            
            // Create and save booking
            Booking newBooking = new Booking(planet, hotel, room, stayDuration, numberOfPeople, bookingName, bookingID, dateTime);
            FileHelper.saveBooking(newBooking);
            
            // Show receipt
            BookingHelper.showReceipt(newBooking);
        } else {
            System.out.println("\nBooking cancelled.");
        }
        
        Utils.pressAnyKeyToContinue();
        System.out.println("Returning to main menu...");
        Utils.threadSleep();
    }
    
    // Method to view transaction history
    private static void viewTransactionHistory() {
        // Validate user credentials
        if (Utils.validate()) {
            ArrayList<String> transactions = FileHelper.readTransactionHistory();
            BookingHelper.displayTransactionHistory(transactions);
            Utils.pressAnyKeyToContinue();
        } else {
            System.out.println("Invalid credentials!");
            Utils.threadSleep(1500);
        }
    }
}