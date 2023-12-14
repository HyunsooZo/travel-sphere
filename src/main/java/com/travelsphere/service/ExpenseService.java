package com.travelsphere.service;

import com.travelsphere.domain.Expense;
import com.travelsphere.domain.User;
import com.travelsphere.dto.ExpenseCreateRequestDto;
import com.travelsphere.dto.ExpenseDto;
import com.travelsphere.dto.ExpenseModificationRequestDto;
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

    /**
     * 지출을 수정한다.
     * @param userId 사용자
     * @param id 지출 id
     * @param expenseModificationRequestDto 지출 수정 요청 DTO
     */
    @Transactional
    public void updateExpense(Long userId,
                              Long id,
                              ExpenseModificationRequestDto expenseModificationRequestDto) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_INFO_NOT_FOUND)
        );

        Expense expense = expenseRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.EXPENSE_NOT_FOUND)
        );

        if (!expense.getUser().equals(user)) {
            throw new CustomException(ErrorCode.NOT_MY_EXPENSE);
        }

        if (expenseModificationRequestDto.getCategory() != null) {
            expense.setCategory(expenseModificationRequestDto.getCategory());
        }

        if(expenseModificationRequestDto.getCurrency() != null) {
            expense.setCurrency(expenseModificationRequestDto.getCurrency().getCurrencyName());
            expense.setAmountOfKrw(exchangeRateService.convertCurrency(
                    Currencies.valueOf(expenseModificationRequestDto.getCurrency().getCurrencyName()),
                    Currencies.KRW,
                    expense.getAmount()
            ));
        }

        if(expenseModificationRequestDto.getAmount() != null) {
            expense.setAmount(expenseModificationRequestDto.getAmount());
            expense.setAmountOfKrw(exchangeRateService.convertCurrency(
                    Currencies.valueOf(expense.getCurrency()),
                    Currencies.KRW,
                    expenseModificationRequestDto.getAmount()
            ));
        }

        if(expenseModificationRequestDto.getDate() != null) {
            expense.setDate(expenseModificationRequestDto.getDate());
        }

        if(expenseModificationRequestDto.getCity() != null) {
            expense.setCity(expenseModificationRequestDto.getCity().getCityName());
            expense.setCountry(expenseModificationRequestDto.getCity().getCountryName());
        }
    }
}
