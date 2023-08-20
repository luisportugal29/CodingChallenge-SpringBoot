package com.gendra.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.gendra.challenge.dtos.LocationDto;
import com.gendra.challenge.dtos.SuggestionQueryDto;
import com.gendra.challenge.dtos.SuggestionsDto;
import com.gendra.challenge.entities.Location;
import com.gendra.challenge.repositories.LocationRepository;
import com.gendra.challenge.services.LocationService;

@SpringBootTest
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSuggestions() {

        SuggestionQueryDto queryDto = new SuggestionQueryDto("London", Optional.of(43.70011), Optional.of(-79.4163));

        Location location = new Location();
        location.setName("London, ON, Canada");
        location.setLatitude(42.98339);
        location.setLongitude(-81.23304);

        when(locationRepository.findByNameStartsWithIgnoreCaseOrderByName(any())).thenReturn(List.of(location));

       
        SuggestionsDto suggestionsDto = locationService.getSuggestions(queryDto);

       
        assertNotNull(suggestionsDto);
        assertEquals(1, suggestionsDto.getSuggestions().size());

        LocationDto locationDto = suggestionsDto.getSuggestions().get(0);
        assertEquals("London, ON, Canada", locationDto.getName());
      

        verify(locationRepository, times(1)).findByNameStartsWithIgnoreCaseOrderByName(any());
    }
    
}
