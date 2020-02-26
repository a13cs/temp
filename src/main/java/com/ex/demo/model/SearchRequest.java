package com.ex.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequest {

    private List<String> sportNames;

    private String periodStart;
    private String periodEnd;

}
