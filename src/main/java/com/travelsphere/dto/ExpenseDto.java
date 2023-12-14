package com.travelsphere.dto;

import com.travelsphere.domain.Expense;
import com.travelsphere.enums.ExpenseCategories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseDto {
    private Long id;
    private Long userId;
    private ExpenseCategories category;
    private String currency;
    private Double amount;
    private Double amountOfKrw;
    private Date date;
    private String city;
    private String country;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ExpenseDto from(Expense expense) {
        return ExpenseDto.builder()
                .id(expense.getId())
                .userId(expense.getUser().getId())
                .category(expense.getCategory())
                .currency(expense.getCurrency())
                .amount(expense.getAmount())
                .amountOfKrw(expense.getAmountOfKrw())
                .date(expense.getDate())
                .city(expense.getCity())
                .country(expense.getCountry())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .build();
    }
}
