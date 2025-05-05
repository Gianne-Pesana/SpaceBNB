package org.Group5;

public class LuxuryRoom extends Rooms {
    // Luxury room specific properties
    private String specialAmenities;    // Special amenities (private-pool, in-room spa, jacuzzi)
    private boolean hasButlerService;   // Whether the room has butler service

    // Constructor to initialize luxury room properties
    public LuxuryRoom(String roomNumber, double pricePerNight, boolean isAvailable, String specialAmenities, boolean hasButlerService) {
        super(roomNumber, "Luxury", pricePerNight, isAvailable);
        this.specialAmenities = specialAmenities;
        this.hasButlerService = hasButlerService;
    }

    // Getter for special amenities
    public String getSpecialAmenities() {
        return specialAmenities;
    }

    // public void setSpecialAmenities(String specialAmenities) { ... } // Keep if needed

    // Check if room has butler service
    public boolean isHasButlerService() {
        return hasButlerService;
    }

    // public void setHasButlerService(boolean hasButlerService) { ... } // Keep if needed

    // Get detailed description of the luxury room
    @Override
    public String getRoomDetails() {
        return String.format("Room %s (%s) - ₱%.2f/night - Amenities: %s, Butler Service: %s",
                roomNumber, roomType, pricePerNight, specialAmenities, hasButlerService ? "Yes" : "No");
    }
}