package com.ex.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class LocationDTO {

    private String city;
    private String region;
    private String country;

    public LocationDTO() {
    }

    public LocationDTO(String city, String region, String country) {
        this.city = city;
        this.region = region;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationDTO that = (LocationDTO) o;
        return city.equals(that.city) &&
                region.equals(that.region) &&
                country.equals(that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, region, country);
    }

    @Override
    public String toString() {
        return String.join(",", city, region, country);
    }
}
