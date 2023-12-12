package com.travelsphere.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Weather extends BaseEntity{

    @Id
    private String cityName;

    private String countryName;

    private String weather;

    private String currentTemperature;

    private String minTemperature;

    private String maxTemperature;

    private Double humidity;

    private String feelsLike;
}
