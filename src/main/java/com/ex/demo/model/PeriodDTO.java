package com.ex.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class PeriodDTO {

    private String start;
    private String end;

    public PeriodDTO() {
    }

    public PeriodDTO(String start, String end) {
        this.start = start;
        this.end = end;
    }
}
