package com.ex.demo.service;

import com.ex.demo.model.SearchRequest;
import com.ex.demo.model.SearchResponse;

import java.util.List;

public interface SearchService {

    List<SearchResponse> getLocations(SearchRequest request);

}
