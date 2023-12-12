package com.travelsphere.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ExchangeRate extends BaseEntity{

    @Id
    private String currency;

    private String countryName;

    private Double toBuy;

    private Double toSell;

    private Double basePrice;

    private Integer currentUnit;

    private String time;
}
