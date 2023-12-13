package com.travelsphere.service;

import com.travelsphere.domain.Expense;
import com.travelsphere.domain.User;
import com.travelsphere.dto.ExpenseCreateRequestDto;
import com.travelsphere.enums.Currencies;
import com.travelsphere.exception.CustomException;
import com.travelsphere.exception.ErrorCode;
import com.travelsphere.repository.ExpenseRepository;
import com.travelsphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExchangeRateService exchangeRateService;

    @Transactional
    public void createExpense(ExpenseCreateRequestDto expenseCreateRequestDto) {

        Double amount = expenseCreateRequestDto.getAmount();

        String currency = expenseCreateRequestDto.getCurrency().getCurrencyName();

        Double amountOfKrw = amount;

        User user = userRepository.findById(expenseCreateRequestDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_INFO_NOT_FOUND));

        if (!currency.equals("KRW"))
            amountOfKrw = exchangeRateService.convertCurrency(
                    expenseCreateRequestDto.getCurrency(),
                    Currencies.KRW,
                    amount
            );

        Expense expense = Expense.builder()
                .user(user)
                .category(expenseCreateRequestDto.getCategory())
                .currency(currency)
                .amount(amount)
                .amountOfKrw(amountOfKrw)
                .date(expenseCreateRequestDto.getDate())
                .city(expenseCreateRequestDto.getCity().getCityName())
                .country(expenseCreateRequestDto.getCity().getCountryName())
                .build();

        expenseRepository.save(expense);
    }
}
