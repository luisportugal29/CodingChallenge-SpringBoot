package com.gendra.challenge.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gendra.challenge.dtos.SuggestionQueryDto;
import com.gendra.challenge.dtos.SuggestionsDto;
import com.gendra.challenge.services.LocationService;

@RestController
@RequestMapping("/suggestions")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public SuggestionsDto getSuggestions (@ModelAttribute SuggestionQueryDto query) {
        return this.locationService.getSuggestions(query);
    }

    
}
