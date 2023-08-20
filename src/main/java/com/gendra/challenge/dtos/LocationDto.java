package com.gendra.challenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    
    private String name;

    private Double latitude;

    private Double longitude;

    private Double score;
}
