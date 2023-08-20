package com.gendra.challenge.services;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import info.debatty.java.stringsimilarity.Levenshtein;
import com.gendra.challenge.dtos.LocationDto;
import com.gendra.challenge.dtos.SuggestionQueryDto;
import com.gendra.challenge.dtos.SuggestionsDto;
import com.gendra.challenge.repositories.LocationRepository;


@Service
public class LocationService {
    
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private  ModelMapper modelMapper;

    private static final int MAX_DISTANCE = 1000;
    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final double TERM_WEIGHT = 1;

    public SuggestionsDto getSuggestions(SuggestionQueryDto queryDto) {

        final double queryLatitude = queryDto.getLatitude().orElse(0.0);
        final double queryLongitude = queryDto.getLongitude().orElse(0.0);

        String name = queryDto.getQ();

        List<LocationDto> locations =  this.locationRepository
        .findByNameStartsWithIgnoreCaseOrderByName(name)
        .stream()
        .map(entity -> {
            LocationDto location = modelMapper.map(entity, LocationDto.class);

            double score = (queryLatitude != 0 && queryLongitude != 0) ? 
            calculateScoreDistance(queryLatitude, queryLongitude, location.getLatitude(), location.getLongitude()) :
            calculateLevenshteinScore( location.getName(), name);   

            location.setScore(score);

            return location;

        })
        .sorted(Comparator.comparingDouble(LocationDto::getScore).reversed())
        .collect(Collectors.toList());

        return new SuggestionsDto(locations);
    }


    private double calculateScoreDistance(double lat1, double lon1, double lat2, double lon2) {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance =  EARTH_RADIUS_KM * c;

        double normalizedDistance = 1.0 - (distance / MAX_DISTANCE );

        return normalizedDistance;
    }

    private double calculateLevenshteinScore(String query, String suggestionName) {

        Levenshtein levenshtein = new Levenshtein();

        double distance = levenshtein.distance(query, suggestionName);

        double normalizedDistance = 1.0 - (double) distance / Math.max(query.length(), suggestionName.length());

        return normalizedDistance * TERM_WEIGHT;
    }

   
}
