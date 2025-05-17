package com.sun.service.impl;

import com.sun.entity.City;
import com.sun.entity.Country;
import com.sun.entity.State;
import com.sun.repo.CityRepository;
import com.sun.repo.CountryRepository;
import com.sun.repo.StateRepository;
import com.sun.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    public void createCountry(Country country) {
        countryRepository.save(country);
    }

    @Override
    public void createState(State state, Integer countryId ) {
    Country country =  countryRepository.findById(countryId).orElseThrow();
    state.setCountry(country);
     stateRepository.save(state);
    }

    @Override
    public void createCity(City city,Integer stateId) {
       State state =  stateRepository.findById(stateId).orElseThrow();
       city.setState(state);
       cityRepository.save(city);
    }


}
