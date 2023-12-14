package com.travelsphere.dto;

import com.travelsphere.enums.Cities;
import com.travelsphere.enums.Currencies;
import com.travelsphere.enums.ExpenseCategories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseModificationRequestDto {
    private ExpenseCategories category;

    private Currencies currency;

    private Double amount;

    private Date date;

    private Cities city;

}
