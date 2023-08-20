package com.gendra.challenge.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gendra.challenge.entities.Location;

public interface LocationRepository extends CrudRepository<Location, Integer> {

    List<Location> findByNameStartsWithIgnoreCaseOrderByName(String name);
    
}
