package com.tuber.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class Address {
    private final UUID id;
    private final String postalCode;
    private final String street;
    private final String city;

    public void validate(UUID id, String postalCode, String street, String city) {
        if(id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if(postalCode == null || postalCode.isEmpty()) {
            throw new IllegalArgumentException("Postal code cannot be null or empty");
        }
        if(street == null || street.isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if(city == null || city.isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
    }

    public Address(UUID id, String postalCode, String street, String city) {
        validate(id, postalCode, street, city);
        this.id = id;
        this.postalCode = postalCode;
        this.street = street;
        this.city = city;
    }

    public UUID getId() {
        return id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(postalCode, address.postalCode) && Objects.equals(street, address.street) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postalCode, street, city);
    }
}
