// In EconomyRoom.java
package org.Group5;

public class EconomyRoom extends Rooms {
    // Economy room specific properties
    private String bedType;   // Type of bed (Single bed, Bunk bed)
    private String roomSize;  // Size of room (Small, Medium, Large)

    // Constructor to initialize economy room properties
    public EconomyRoom(String roomNumber, double pricePerNight, boolean isAvailable, String bedType, String roomSize) {
        super(roomNumber, "Economy", pricePerNight, isAvailable);
        this.bedType = bedType;
        this.roomSize = roomSize;
    }

    // Getter for bed type
    public String getBedType() {
        return bedType;
    }

    // Getter for room size
    public String getRoomSize() {
        return roomSize;
    }

    // Get detailed description of the economy room
    @Override
    public String getRoomDetails() {
        return String.format("Room %s (%s) - ₱%.2f/night - Bed Type: %s, Size: %s",
                roomNumber, roomType, pricePerNight, bedType, roomSize);
    }
}