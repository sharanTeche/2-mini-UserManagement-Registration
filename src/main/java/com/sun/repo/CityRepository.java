package com.sun.repo;

import com.sun.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {

    List<City> findByStateStateId(Integer stateId);
}
