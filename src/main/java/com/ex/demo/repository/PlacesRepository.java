package com.ex.demo.repository;

import com.ex.demo.data.LocationId;
import com.ex.demo.data.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlacesRepository extends JpaRepository<Place, Long> {

    List<Place> findById(LocationId id);

}
