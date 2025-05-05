package org.Group5;

// Add imports for Hotel and Rooms
import org.Group5.Hotels;
import org.Group5.Rooms;
import org.Group5.Planets; // Assuming Planets is already imported

public class Booking {
    private Planets planet;
    private Hotels hotel; // Add Hotel attribute
    private Rooms room; // Add Rooms attribute
    private int stayDuration; // Changed to int for calculations
    private int numberOfPeople; // Changed to int for calculations
    private String bookingName; // Add bookingName attribute
    private String bookingID;
    private String dateTime;

    // Updated constructor to include Hotel, Rooms, bookingName, stayDuration (as int), numberOfPeople (as int)
    public Booking(Planets planet, Hotels hotel, Rooms room, int stayDuration, int numberOfPeople, String bookingName, String bookingID, String dateTime) {
        this.planet = planet;
        this.hotel = hotel;
        this.room = room;
        this.stayDuration = stayDuration;
        this.numberOfPeople = numberOfPeople;
        this.bookingName = bookingName;
        this.bookingID = bookingID;
        this.dateTime = dateTime;
    }

    // Add getters for all attributes
    public Planets getPlanet() {
        return planet;
    }

    public Hotels getHotel() { // Getter for Hotel
        return hotel;
    }

    public Rooms getRoom() { // Getter for Room
        return room;
    }

    public int getStayDuration() { // Getter for stayDuration (int)
        return stayDuration;
    }

    public int getNumberOfPeople() { // Getter for numberOfPeople (int)
        return numberOfPeople;
    }

    public String getBookingName() {
        return bookingName;
    }

    public String getBookingID() {
        return bookingID;
    }

    public String getDateTime() {
        return dateTime;
    }

}