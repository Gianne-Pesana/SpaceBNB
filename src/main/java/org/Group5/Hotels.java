package org.Group5;

import java.util.ArrayList;

public class Hotels {
    // Hotel properties
    private String hotelName;           // Name of the hotel
    private int rating;                 // Hotel rating (1-5 stars)
    private ArrayList<Rooms> rooms;     // List of rooms in the hotel

    // Constructor to initialize hotel properties
    public Hotels(String hotelName, int rating, ArrayList<Rooms> rooms) {
        this.hotelName = hotelName;
        this.rating = rating;
        this.rooms = rooms;
    }

    // Getter for hotel name
    public String getHotelName() {
        return hotelName;
    }

    // Setter for hotel name
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    // Getter for hotel rating
    public int getRating() {
        return rating;
    }

    // Setter for hotel rating
    public void setRating(int rating) {
        this.rating = rating;
    }

    // Getter for list of rooms
    public ArrayList<Rooms> getRooms() {
        return rooms;
    }

    // Setter for list of rooms
    public void setRooms(ArrayList<Rooms> rooms) {
        this.rooms = rooms;
    }

    // Convert rating number to star symbols
    public String renderRating() {
        String starRating = "";
        for (int i = 0; i < rating; i++) {
            starRating += "⭐";
        }
        return starRating;
    }
}
