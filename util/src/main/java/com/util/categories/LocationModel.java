package com.util.categories;


/**
 * The type Location model.
 */
public final class LocationModel {
    /**
     * The Latitude.
     */
    public Double latitude;
    /**
     * The Longitude.
     */
    public Double longitude;
    /**
     * The Address.
     */
    public String address;
    /**
     * The City.
     */
    public String city;
    /**
     * The Country.
     */
    public String country;

    @Override
    public String toString() {
        return address + ", " + city + ", " + country;
    }
}
