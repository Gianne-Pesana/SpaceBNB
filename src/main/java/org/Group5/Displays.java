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

    public static String showMainMenu() {
        String choice;
        while (true) {
            Utils.clearConsole();
            System.out.println(Displays.titleHeader);
            System.out.println("Select operation:");
            System.out.println("[1] Book hotel room");
            System.out.println("[2] View receipts (Password: 1234)");
            System.out.println("[3] Exit");
            System.out.println();
            System.out.print("Enter operation choice: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine();

            if (choice.equals("1") || choice.equals("2") || choice.equals("3")) break;
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


            System.out.printf("| %-7s | %-8s | ₱%-11.2f | %-49s |\n",
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

        // if there are no more rooms
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
}