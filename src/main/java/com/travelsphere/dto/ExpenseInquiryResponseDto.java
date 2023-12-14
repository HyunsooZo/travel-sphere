package com.travelsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseInquiryResponseDto {
    private Page<ExpenseDto> expenses;

    public static ExpenseInquiryResponseDto from(Page<ExpenseDto> expenses) {
        return ExpenseInquiryResponseDto.builder()
                .expenses(expenses)
                .build();
    }
}
