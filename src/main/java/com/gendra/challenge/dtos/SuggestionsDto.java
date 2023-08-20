package com.gendra.challenge.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SuggestionsDto {

    private List<LocationDto> suggestions;    
    
}
