package com.sun.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer stateId;
    String stateName;


    @ManyToOne
    @JoinColumn(name = "countryId")
    Country country;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
