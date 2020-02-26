package com.ex.demo.controller;

import com.ex.demo.model.LocationDTO;
import com.ex.demo.model.PlaceDTO;
import com.ex.demo.service.PlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlacesController {

    @Autowired
    public PlacesService placesService;

    @GetMapping("/")
    public List<PlaceDTO> getPlaces() {
        return placesService.getPlaces();
    }

    @GetMapping("/one")
    public PlaceDTO getPlace(LocationDTO location) {
        return placesService.getPlace(location);
    }

    @PostMapping
    public void addPlace(@RequestBody PlaceDTO placeDTO) {
        placesService.addPlace(placeDTO);
    }

    @PostMapping(value = "/places")
    public void addPlaces(@RequestBody List<PlaceDTO> places) {
        placesService.addPlaces(places);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public boolean removePlace(@RequestBody LocationDTO location) {
        return placesService.removePlace(location);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public PlaceDTO updatePlace(@RequestBody PlaceDTO place) {
        return placesService.updatePlace(place);
    }

}
