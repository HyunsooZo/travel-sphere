package com.travelsphere.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Cities {

    //overall
    ALL("All", "All"),

    //vietnam
    HO_CHI_MINH("Thanh pho Ho Chi Minh", "Vietnam"),
    HANOI("Ha Noi", "Vietnam"),
    TURAN("Turan", "Vietnam"),

    //thailand
    BANGKOK("Bangkok", "Thailand"),
    PHUKET("Phuket", "Thailand"),
    CHIANG_MAI("Chiang Mai", "Thailand"),

    //malaysia
    KUALA_LUMPUR("Kuala Lumpur", "Malaysia"),
    GEORGETOWN("George Town", "Malaysia"),
    KOTA_KINABALU("Kota Kinabalu", "Malaysia");

    private final String cityName;
    private final String countryName;
}
