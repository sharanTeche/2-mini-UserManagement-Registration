package com.sun.service;

import com.sun.entity.City;
import com.sun.entity.Country;
import com.sun.entity.State;


public interface LocationService {

    public void createCountry(Country country);
    public void createState(State state, Integer countryId);
    public void createCity(City city, Integer stateId);
}
