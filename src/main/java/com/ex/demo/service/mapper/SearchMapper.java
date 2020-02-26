package com.ex.demo.service.mapper;

import com.ex.demo.data.Place;
import com.ex.demo.data.Sport;
import com.ex.demo.model.SearchResponse;
import com.ex.demo.model.SportResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class SearchMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlacesMapper.class);

    private SearchMapper() {}


    public static List<SearchResponse> mapSportsToModel(final List<Sport> sports, final Integer days) {
        final List<SearchResponse> responseList = new ArrayList<>();
        sports.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Sport::getPlace))
                .forEach((place,sportList) -> {
                    SearchResponse response = new SearchResponse();
                    if(Objects.isNull(place)) return;
                    response.setLocationName(String.join(",", place.getCity(), place.getRegion(), place.getCountry()));
                    response.setSportResults(sportList
                            .stream()
                            .map(s -> SearchMapper.mapSportToSportResult(s, days))
                            .collect(Collectors.toList()));
                    responseList.add(response);
                });
        return responseList;
    }

    private static SportResult mapSportToSportResult(Sport sport, Integer days) {
        SportResult sportResult = new SportResult();
        sportResult.setSportName(sport.getName());
        int cost = days * sport.getCost();
        sportResult.setCost(cost);

        return sportResult;
    }

}
