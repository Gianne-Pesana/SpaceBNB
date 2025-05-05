// In StandardRoom.java
package org.Group5;

public class StandardRoom extends Rooms {
    // Standard room specific properties
    private String bedSize;      // Size of bed (King, Queen, Twin)
    private boolean hasBalcony;  // Whether the room has a balcony

    // Constructor to initialize standard room properties
    public StandardRoom(String roomNumber, double pricePerNight, boolean isAvailable, String bedSize, boolean hasBalcony) {
        super(roomNumber, "Standard", pricePerNight, isAvailable);
        this.bedSize = bedSize;
        this.hasBalcony = hasBalcony;
    }

    // Getter for bed size
    public String getBedSize() {
        return bedSize;
    }

    // public void setBedSize(String bedSize) { ... } // Keep if needed

    // Check if room has balcony
    public boolean isHasBalcony() {
        return hasBalcony;
    }

    // public void setHasBalcony(boolean hasBalcony) { ... } // Keep if needed

    // Get detailed description of the standard room
    @Override
    public String getRoomDetails() {
        return String.format("Room %s (%s) - ₱%.2f/night - Bed Size: %s, Balcony: %s",
                roomNumber, roomType, pricePerNight, bedSize, hasBalcony ? "Yes" : "No");
    }
}