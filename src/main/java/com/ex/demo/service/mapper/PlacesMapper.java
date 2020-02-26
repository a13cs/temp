package com.ex.demo.service.mapper;

import com.ex.demo.data.Place;
import com.ex.demo.data.Sport;
import com.ex.demo.model.LocationDTO;
import com.ex.demo.model.PeriodDTO;
import com.ex.demo.model.PlaceDTO;
import com.ex.demo.model.SportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class PlacesMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlacesMapper.class);
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    private PlacesMapper() {}

    public static Place mapDTOtoEntity(PlaceDTO placeDTO) {
        final Place place = new Place();
        Set<Sport> sports = placeDTO.getSports()
                .stream()
                .filter(Objects::nonNull)
                .map(s -> PlacesMapper.mapSportDTOtoEntity(s, place))
                .collect(Collectors.toSet());
        place.setSports(new ArrayList<>(sports));

        place.setCity(placeDTO.getLocation().getCity());
        place.setRegion(placeDTO.getLocation().getRegion());
        place.setCountry(placeDTO.getLocation().getCountry());

        return place;
    }

    public static PlaceDTO mapEntityToDTO(Place place) {
        return PlaceDTO.builder()
                .location(new LocationDTO(place.getCity(), place.getRegion(), place.getCountry()))
                .sports(place.getSports().stream().
                        filter(Objects::nonNull)
                        .map(PlacesMapper::mapSportEntityToDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    private static Sport mapSportDTOtoEntity(SportDTO sportDTO, Place place) {
        Sport sport = new Sport();
        sport.setName(sportDTO.getName());
        sport.setCost(sportDTO.getCost());
        String startDate = sportDTO.getPeriod().getStart();
        String endDate = sportDTO.getPeriod().getEnd();
        try{
            sport.setStartDate(SDF.parse(startDate));
            sport.setEndDate(SDF.parse(endDate));
        } catch (ParseException pex) {
            LOGGER.warn(String.format("Could not parse %s as startDate and %s as endDate. %s",startDate, endDate, pex.getMessage()));
//            throw new RuntimeException()
        }
//        sport.setPlace(place);
        return sport;
    }

    private static SportDTO mapSportEntityToDTO(Sport sport) {
        PeriodDTO periodDTO = new PeriodDTO(SDF.format(sport.getStartDate()), SDF.format(sport.getEndDate()));
        return SportDTO.builder()
                .name(sport.getName())
                .cost(sport.getCost())
                .period(periodDTO)
                .build();
    }
}
