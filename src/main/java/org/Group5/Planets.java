package org.Group5;

public class Planets {
    // Planet properties
    private String planetName;  // Name of the planet
    private String climate;     // Climate of the planet
    private String distance;    // Distance from Earth

    // Constructor to initialize planet properties
    public Planets(String planetName, String climate, String distance) {
        this.planetName = planetName;
        this.climate = climate;
        this.distance = distance;
    }

    // Getter for planet name
    public String getPlanetName() {
        return planetName;
    }

    // Setter for planet name
    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    // Getter for planet climate
    public String getClimate() {
        return climate;
    }

    // Setter for planet climate
    public void setClimate(String climate) {
        this.climate = climate;
    }

    // Getter for distance from Earth
    public String getDistance() {
        return distance;
    }

    // Setter for distance from Earth
    public void setDistance(String distance) {
        this.distance = distance;
    }
}
