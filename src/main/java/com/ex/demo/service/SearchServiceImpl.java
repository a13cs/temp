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
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
        Period period   = start.until(end);

        LOGGER.info("findByNameInAndStartDateGreaterThan:");
        sportsRepository.findByNameInAndStartDateLessThan(sportNames, Date.valueOf(start)).stream().map(Objects::toString).forEach(LOGGER::info);
        LOGGER.info("mapSportsToModel:");
        SearchMapper.mapSportsToModel(sportsRepository.findByNameInAndStartDateLessThan(sportNames, Date.valueOf(start)), period.getDays()).stream().map(Objects::toString).forEach(LOGGER::info);


        List<Sport> sports = sportsRepository.queryBySportAndPeriod(sportNames, Date.valueOf(start), Date.valueOf(end));
        SearchMapper.mapSportsToModel(sports, period.getDays()).stream().map(Objects::toString).forEach(LOGGER::info);
        return SearchMapper.mapSportsToModel(sports, period.getDays());
    }
}
