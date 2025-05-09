package org.Group5;

    public abstract class Rooms {
    // Basic room properties
    protected String roomNumber;    // Unique identifier for the room
    protected String roomType;      // Type of room (Economy, Standard, Luxury)
    protected double pricePerNight; // Price per night in pesos
    protected boolean isAvailable;  // Whether the room is available for booking

    // Constructor to initialize room properties
    public Rooms(String roomNumber, String roomType, double pricePerNight, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
    }

    // Getter for room number
    public String getRoomNumber() {
        return roomNumber;
    }

    // Getter for room type
    public String getRoomType() {
        return roomType;
    }

    // Getter for price per night
    public double getPricePerNight() {
        return pricePerNight;
    }

    // Check if room is available
    public boolean isAvailable() {
        return isAvailable;
    }

    // Set room availability
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // Abstract method to get room-specific details
    // This will be implemented by each room type (Economy, Standard, Luxury)
    public abstract String getRoomDetails();
}