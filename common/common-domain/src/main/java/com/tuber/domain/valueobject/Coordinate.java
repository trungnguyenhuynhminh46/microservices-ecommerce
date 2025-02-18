package com.tuber.domain.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

public class Coordinate {
    private final Double latitude;
    private final Double longitude;

    public Coordinate(Double latitude, Double longitude) {
        if (latitude == null) {
            throw new IllegalArgumentException("Latitude cannot be null");
        }
        if (longitude == null) {
            throw new IllegalArgumentException("Longitude cannot be null");
        }
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public BigDecimal distantFrom(Coordinate point) {
        double earthRadius = 6371.01;
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(point.latitude);
        double lon2 = Math.toRadians(point.longitude);
        double distance = earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
        return new BigDecimal(distance);
    }
}
