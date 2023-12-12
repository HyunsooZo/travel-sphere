package com.travelsphere.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Countries {

    //overall
    ALL("ALL","All"),

    VIETNAM("Vietnam","VND"),
    THAILAND("Thailand","THB"),
    MALAYSIA("Malaysia","MYR");

    private final String countryName;
    private final String currency;
}
