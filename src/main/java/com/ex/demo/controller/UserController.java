package com.ex.demo.controller;

import com.ex.demo.model.SearchRequest;
import com.ex.demo.model.SearchResponse;
import com.ex.demo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class UserController {

    @Autowired
    SearchService service;

    @ResponseBody
    @PostMapping("/search")
    public List<SearchResponse> getLocations(@RequestBody SearchRequest request) {
        return service.getLocations(request);
    }

}
