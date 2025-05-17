package com.sun.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer cityId;
    String cityName;


    @ManyToOne
    @JoinColumn(name = "stateId")
    State state;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
