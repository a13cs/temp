package com.ex.demo.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString
@Entity
public class Place {

//    @Id private String city;
//    @Id private String region;
//    @Id private String country;
    @EmbeddedId
    private LocationId id = new LocationId();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Sport> sports;

    public Place() { }

    public LocationId getId() {
        return id;
    }

    public String getCity() {
        return id.getCity();
    }

    public void setCity(String city) {
        this.id.setCity(city);
    }

    public String getRegion() {
        return id.getRegion();
    }

    public void setRegion(String region) {
        this.id.setRegion(region);
    }

    public String getCountry() {
        return id.getCountry();
    }

    public void setCountry(String country) {
        this.id.setCountry(country);
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }
}

