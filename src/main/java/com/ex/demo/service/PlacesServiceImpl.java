package com.ex.demo.service;

import com.ex.demo.data.Place;
import com.ex.demo.model.LocationDTO;
import com.ex.demo.model.PlaceDTO;
import com.ex.demo.repository.PlacesRepository;
import com.ex.demo.service.mapper.PlacesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlacesServiceImpl implements PlacesService {

    @Autowired
    public PlacesRepository placesRepository;


    @Override
    public List<PlaceDTO> getPlaces() {
        List<Place> entities = placesRepository.findAll();
        return entities.stream().map(PlacesMapper::mapEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public void addPlace(PlaceDTO placeDTO) {
        if (Objects.isNull(placeDTO)) return;
        placesRepository.saveAndFlush(PlacesMapper.mapDTOtoEntity(placeDTO));
    }

    @Override
    public void addPlaces(List<PlaceDTO> places) {
        List<Place> entities = places.stream().map(PlacesMapper::mapDTOtoEntity).collect(Collectors.toList());
        placesRepository.saveAll(entities);
    }

    @Override
    public PlaceDTO getPlace(LocationDTO location) {
        List<Place> places = placesRepository
                .findByCityAndRegionAndCountry(location.getCity(), location.getRegion(), location.getCountry());
        return places.stream().map(PlacesMapper::mapEntityToDTO).findFirst().orElseGet(PlaceDTO::new);
    }

    @Override
    public boolean removePlace(LocationDTO location) {
        Optional<Place> place = placesRepository
                .findByCityAndRegionAndCountry(location.getCity(), location.getRegion(), location.getCountry())
                .stream()
                .findFirst();

        place.ifPresent(this.placesRepository::delete);
        return place.isPresent();
    }

    @Override
    public PlaceDTO updatePlace(PlaceDTO placeDTO) {
        Optional<Place> place = placesRepository
                .findByCityAndRegionAndCountry(
                        placeDTO.getLocation().getCity(),
                        placeDTO.getLocation().getRegion(),
                        placeDTO.getLocation().getCountry())
                .stream()
                .findFirst();

        place.ifPresent(p -> placesRepository.saveAndFlush(p));
        return PlacesMapper.mapEntityToDTO(place.orElseGet(Place::new));
    }
}
