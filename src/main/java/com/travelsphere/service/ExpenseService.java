package com.travelsphere.service;

import com.travelsphere.domain.Expense;
import com.travelsphere.domain.User;
import com.travelsphere.dto.ExpenseCreateRequestDto;
import com.travelsphere.dto.ExpenseDto;
import com.travelsphere.enums.Currencies;
import com.travelsphere.exception.CustomException;
import com.travelsphere.exception.ErrorCode;
import com.travelsphere.repository.ExpenseRepository;
import com.travelsphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExchangeRateService exchangeRateService;

    /**
     * 지출을 등록한다. (KRW로 환산하여 저장)
     * @param userId 사용자
     * @param expenseCreateRequestDto 지출 등록 요청 DTO
     */
    @Transactional
    public void createExpense(Long userId,
                              ExpenseCreateRequestDto expenseCreateRequestDto) {


        Double amount = expenseCreateRequestDto.getAmount();

        String currency = expenseCreateRequestDto.getCurrency().getCurrencyName();

        Double amountOfKrw = amount;

        User user = userRepository.findById(userId)
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

    /**
     * 사용자의 지출 내역을 조회한다.
     * @param userId 사용자
     * @param pageRequest 페이지 정보
     * @return 지출 내역
     */
    @Transactional(readOnly = true)
    public Page<ExpenseDto> getExpenses(Long userId, PageRequest pageRequest) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_INFO_NOT_FOUND)
        );
        return expenseRepository.findAllByUserId(user, pageRequest);
    }

}
