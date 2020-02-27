package com.ex.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SportResult {

    private String sportName;
    private long cost;

    public SportResult() {
    }

}
