package com.sun.repo;

import com.sun.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Integer> {

   List<State> findByCountryCountryId(Integer countryId);
}
