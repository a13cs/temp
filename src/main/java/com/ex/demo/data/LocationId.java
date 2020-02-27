package com.ex.demo.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Embeddable
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
