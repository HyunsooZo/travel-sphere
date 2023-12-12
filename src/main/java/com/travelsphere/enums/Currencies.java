package com.travelsphere.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currencies {
    KRW("KRW"),
    VND("VND"),
    THB("THB"),
    MYR("MYR");

    private final String currencyName;

}
