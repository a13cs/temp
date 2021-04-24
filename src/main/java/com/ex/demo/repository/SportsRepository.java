package com.ex.demo.repository;

import com.ex.demo.data.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SportsRepository extends JpaRepository<Sport, Long> {

    @Query("select s from Sport s where name in :sports and startDate<= :start and endDate>= :end")
    List<Sport> getBySportAndPeriod(@Param("sports") List<String> sportNames,
                                    @Param("start")  Date startDate,
                                    @Param("end")    Date endDate);

    List<Sport> findByNameInAndStartDateLessThan(List<String> sportNames, Date start);

}
