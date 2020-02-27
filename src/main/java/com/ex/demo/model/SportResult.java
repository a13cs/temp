package com.ex.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SportResult {

    private String sportName;
    private Integer cost;

    public SportResult() {
    }

    public SportResult(String sportName, Integer cost) {
        this.sportName = sportName;
        this.cost = cost;
    }
}
