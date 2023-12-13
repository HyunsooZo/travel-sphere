package com.travelsphere.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpenseCategories {
    ACCOMMODATION("숙박비"),
    FOOD("식비"),
    TRANSPORTATION("교통비"),
    SHOPPING("쇼핑"),
    ACTIVITY("액티비티"),
    ETC("기타");

    private final String categoryName;
}
