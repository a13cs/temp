package com.ex.demo.repository;

import com.ex.demo.data.LocationId;
import com.ex.demo.data.Sport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SportsRepositoryTest {

    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    SportsRepository sportsRepository;

    @BeforeEach
    void init() throws  ParseException {
        Sport sport1 = Sport.builder()
                .name("s1")
                .cost(2)
                .startDate(SDF.parse("2020-02-01"))
                .endDate(SDF.parse("2020-03-15"))
                .locationId(LocationId.builder()
                        .city("Brasov")
                        .region("Brasov")
                        .country("Romania")
                        .build())
                .build();
        Sport sport2 = Sport.builder()
                .name("s2")
                .cost(5)
                .startDate(SDF.parse("2020-02-01"))
                .endDate(SDF.parse("2020-03-01"))
                .locationId(LocationId.builder()
                        .city("Bucuresti")
                        .region("Bucuresti")
                        .country("Romania")
                        .build())
                .build();
        sportsRepository.saveAndFlush(sport1);
        sportsRepository.saveAndFlush(sport2);
    }

    @Test
    void getBySportAndPeriod() throws ParseException {
        List<Sport> results = sportsRepository.getBySportAndPeriod(
                Arrays.asList("s1", "s2"),
                SDF.parse("2020-02-10"),
                SDF.parse("2020-03-03"));
        assertThat(results).extracting(Sport::getName).containsOnly("s1");
    }
}