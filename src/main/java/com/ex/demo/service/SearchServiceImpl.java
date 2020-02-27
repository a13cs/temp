package com.ex.demo.service;

import com.ex.demo.data.Sport;
import com.ex.demo.model.SearchRequest;
import com.ex.demo.model.SearchResponse;
import com.ex.demo.repository.SportsRepository;
import com.ex.demo.service.mapper.SearchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class SearchServiceImpl implements SearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    SportsRepository sportsRepository;

    @Override
    public List<SearchResponse> getLocations(SearchRequest request) {
        List<String> sportNames = request.getSportNames();
        LocalDate start = LocalDate.parse(request.getPeriodStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end   = LocalDate.parse(request.getPeriodEnd(),   DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<Sport> sports = sportsRepository.getBySportAndPeriod(sportNames, Date.valueOf(start), Date.valueOf(end));
        sports.stream().map(Objects::toString).forEach(LOGGER::info);

        return SearchMapper.mapSportsToModel(sports, ChronoUnit.DAYS.between(start,end));
    }
}
