package com.gendra.challenge.dtos;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SuggestionQueryDto {

    private String q;

    private Optional<Double> latitude;

    private Optional<Double> longitude;
    
}
