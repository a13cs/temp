package com.ex.demo.data;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LocationId implements Serializable {

    private String city;
    private String region;
    private String country;

    public LocationId() {
    }

    public LocationId(String city, String region, String country) {
        this.city = city;
        this.region = region;
        this.country = country;
    }
}
