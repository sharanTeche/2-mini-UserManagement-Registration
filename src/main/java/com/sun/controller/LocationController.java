package com.sun.controller;

import com.sun.entity.City;
import com.sun.entity.Country;
import com.sun.entity.State;
import com.sun.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class LocationController {

    @Autowired
    LocationService locationService;



    @PostMapping("/country")
    public void createCountry(@RequestBody Country country) {
        locationService.createCountry(country);
    }

    @PostMapping("/state/{countryId}")
    public void createCountry(@RequestBody State state, @PathVariable("countryId") Integer countryId) {
        locationService.createState(state,countryId);
    }

    @PostMapping("/city/{stateId}")
    public void createCountry(@RequestBody City city,@PathVariable("stateId") Integer stateId) {
        locationService.createCity(city,stateId);
    }


}
