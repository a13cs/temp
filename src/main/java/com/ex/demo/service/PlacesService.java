package com.ex.demo.service;


import com.ex.demo.model.LocationDTO;
import com.ex.demo.model.PlaceDTO;

import java.util.List;

public interface PlacesService {

    void addPlace(PlaceDTO placeDTO);

    void addPlaces(List<PlaceDTO> places);

    PlaceDTO getPlace(LocationDTO location);

    List<PlaceDTO> getPlaces();

    PlaceDTO updatePlace(PlaceDTO place);

    boolean removePlace(LocationDTO location);
}
