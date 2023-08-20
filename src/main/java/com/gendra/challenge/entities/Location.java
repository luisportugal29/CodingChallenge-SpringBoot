package com.gendra.challenge.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "geoname")
@Data
public class Location {
    
    @Id
    private Integer geonameid;

    private String name;

    private Double latitude;

    private Double longitude;
    
}
