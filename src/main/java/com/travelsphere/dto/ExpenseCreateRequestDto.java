package com.travelsphere.dto;

import com.travelsphere.enums.Cities;
import com.travelsphere.enums.Currencies;
import com.travelsphere.enums.ExpenseCategories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseCreateRequestDto {

    @NotNull(message = "사용자 ID를 입력해주세요.")
    private Long userId;

    @NotNull(message = "카테고리를 입력해주세요.")
    private ExpenseCategories category;

    @NotNull(message = "통화를 입력해주세요.")
    private Currencies currency;

    @Min(value = 1, message = "금액은 0보다 커야합니다.")
    private Double amount;

    @NotNull(message = "날짜를 입력해주세요.")
    private Date date;

    @NotNull(message = "도시를 입력해주세요.")
    private Cities city;

}
