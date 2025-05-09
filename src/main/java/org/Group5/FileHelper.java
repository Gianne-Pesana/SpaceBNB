package org.Group5;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHelper {
    private static final String DATA_PATH = "data\\";
    private static final String TRANSACTIONS_PATH = "transactions\\transactions.txt";
    private static final double VAT_RATE = 0.12; // 12% VAT rate

    // Read planet data from file
    public static ArrayList<Planets> readPlanetData()  {
        ArrayList<Planets> planets = new ArrayList<>();
        
        try (Scanner inFile = new Scanner(new FileReader(DATA_PATH + "planetData.txt"))) {
            while (inFile.hasNextLine()) {
                String line = inFile.nextLine().trim();
                
                // Skip empty lines and comments
                if (line.isEmpty()) {
                    continue;
                }
                
                // Split line into parts
                String[] parts = line.split("\\|");
                
                // Validate data format
                if (parts.length != 3) {
                    System.out.println("Warning: Invalid planet data format: " + line + ". Planet skipped");
                    continue;
                }
                
                // Create and add planet object
                String planetName = parts[0].trim();
                String climate = parts[1].trim();
                String distance = parts[2].trim();
                planets.add(new Planets(planetName, climate, distance));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: planetData.txt not found at " + DATA_PATH);
        } catch (Exception e) {
            System.err.println("Error reading planet data: " + e.getMessage());
        }
        
        return planets;
    }

    // Read hotel data for a specific planet
    public static ArrayList<Hotels> readHotelData(String planetName) {
        ArrayList<Hotels> hotelList = new ArrayList<>();
        String planetDirectory = DATA_PATH + planetName.replace(" ", "-") + "\\";
        String hotelDataFilePath = planetDirectory + "hotelData.txt";
        
        try (Scanner inFile = new Scanner(new FileReader(hotelDataFilePath))) {
            while (inFile.hasNextLine()) {
                String line = inFile.nextLine().trim();
                
                // Skip empty lines and comments
                if (line.isEmpty()) {
                    continue;
                }
                
                // Split line into parts
                String[] parts = line.split(",");
                
                // Validate data format
                if (parts.length != 2) {
                    System.out.println("Warning: Invalid hotel data format: " + line + ". Skipping hotel.");
                    continue;
                }
                
                // Parse hotel data
                String hotelName = parts[0].trim();
                int rating = Integer.parseInt(parts[1].trim());
                
                // Get hotel directory path
                String hotelDirectory = planetDirectory + hotelName.replace(" ", "-") + "\\";
                
                // Load rooms for this hotel
                ArrayList<Rooms> roomsForHotel = readRoomData(hotelDirectory, planetName, hotelName);
                
                // Create and add hotel object
                hotelList.add(new Hotels(hotelName, rating, roomsForHotel));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: hotelData.txt not found in directory: " + planetDirectory);
        }
        
        return hotelList;
    }

    // Read room data for a specific hotel
    public static ArrayList<Rooms> readRoomData(String hotelDirectory, String planetName, String hotelName) {
        ArrayList<Rooms> roomsList = new ArrayList<>();
        String roomsFilePath = hotelDirectory + "rooms.txt";
        
        try (Scanner inFile = new Scanner(new FileReader(roomsFilePath))) {
            while (inFile.hasNextLine()) {
                String line = inFile.nextLine().trim();
                
                if (line.isEmpty()) {
                    continue;
                }
                
                // Split line into parts
                String[] parts = line.split(",");
                
                // Validate data format
                if (parts.length < 4) {
                    System.out.println("Warning: Invalid room data format: " + line + ". Skipping room.");
                    continue;
                }
                
                // Parse basic room data
                String roomNumber = parts[0].trim();
                String roomType = parts[1].trim();
                double pricePerNight;
                boolean isAvailable;

                pricePerNight = Double.parseDouble(parts[2].trim());
                isAvailable = Boolean.parseBoolean(parts[3].trim());
               
                
                // Create appropriate room type based on roomType
                Rooms room = null;
                switch (roomType.toLowerCase()) {
                    case "economy":
                        if (parts.length >= 6) {
                            room = new EconomyRoom(roomNumber, pricePerNight, isAvailable,
                                    parts[4].trim(), parts[5].trim());
                        }
                        break;
                    case "standard":
                        if (parts.length >= 6) {
                            room = new StandardRoom(roomNumber, pricePerNight, isAvailable,
                                    parts[4].trim(), Boolean.parseBoolean(parts[5].trim()));
                        }
                        break;
                    case "luxury":
                        if (parts.length >= 6) {
                            room = new LuxuryRoom(roomNumber, pricePerNight, isAvailable,
                                    parts[4].trim(), Boolean.parseBoolean(parts[5].trim()));
                        }
                        break;
                }
                
                if (room != null) {
                    roomsList.add(room);
                } else {
                    System.out.println("Warning: Could not create room object for room: " + roomNumber);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: rooms.txt not found in directory: " + hotelDirectory);
        }
        
        return roomsList;
    }

    // Save booking to transactions file
    public static void saveBooking(Booking booking) {
        try (FileWriter writer = new FileWriter(TRANSACTIONS_PATH, true)) {
            // Calculate totals including VAT
            double subtotal = booking.getRoom().getPricePerNight() * booking.getStayDuration();
            double vatAmount = subtotal * VAT_RATE;
            double totalPrice = subtotal + vatAmount;

            // Format: bookingID|dateTime|planetName|hotelName|roomNumber|roomType|stayDuration|numberOfPeople|bookingName|totalPrice
            String bookingData = String.format("%s|%s|%s|%s|%s|%s|%d|%d|%s|%.2f\n",
                    booking.getBookingID(),
                    booking.getDateTime(),
                    booking.getPlanet().getPlanetName(),
                    booking.getHotel().getHotelName(),
                    booking.getRoom().getRoomNumber(),
                    booking.getRoom().getRoomType(),
                    booking.getStayDuration(),
                    booking.getNumberOfPeople(),
                    booking.getBookingName(),
                    totalPrice);
            
            writer.write(bookingData);
        } catch (IOException e) {
            System.err.println("Error saving booking: " + e.getMessage());
        }
    }

    // Update room availability in file
    public static void updateRoomAvailability(Planets planet, Hotels hotel, Rooms bookedRoom) {
        String planetDirectory = DATA_PATH + planet.getPlanetName().replace(" ", "-") + "\\";
        String hotelDirectory = planetDirectory + hotel.getHotelName().replace(" ", "-") + "\\";
        String roomsFilePath = hotelDirectory + "rooms.txt";
        
        // Read all lines from the file
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner inFile = new Scanner(new FileReader(roomsFilePath))) {
            while (inFile.hasNextLine()) {
                lines.add(inFile.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error on getting room lines");
        }
        
        // Update the file with modified room availability
        try (FileWriter writer = new FileWriter(roomsFilePath)) {
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    writer.write(line + "\n");
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].trim().equals(bookedRoom.getRoomNumber())) {
                    // Update availability for the booked room
                    parts[3] = "false";
                    writer.write(String.join(",", parts) + "\n");
                } else {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error updating room availability: " + e.getMessage());
        }
    }

    // Read transaction history
    public static ArrayList<String> readTransactionHistory() {
        ArrayList<String> transactions = new ArrayList<>();
        
        try (Scanner inFile = new Scanner(new FileReader(TRANSACTIONS_PATH))) {
            while (inFile.hasNextLine()) {
                String line = inFile.nextLine().trim();
                if (!line.isEmpty()) {
                    transactions.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: transactions.txt not found at " + TRANSACTIONS_PATH);
        } 
        return transactions;
    }
} 