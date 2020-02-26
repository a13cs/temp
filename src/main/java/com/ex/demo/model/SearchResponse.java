package com.ex.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SearchResponse {

    private String locationName;
    private List<SportResult> sportResults;

    public SearchResponse() {
    }

    public SearchResponse(String locationName, List<SportResult> sportResults) {
        this.locationName = locationName;
        this.sportResults = sportResults;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<SportResult> getSportResults() {
        return sportResults;
    }

    public void setSportResults(List<SportResult> sportResults) {
        this.sportResults = sportResults;
    }
}
