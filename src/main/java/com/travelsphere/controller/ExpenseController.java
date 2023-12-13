package com.travelsphere.controller;

import com.travelsphere.dto.ExpenseCreateRequestDto;
import com.travelsphere.service.ExpenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Api(tags = "Expense API", description = "지출 관련 API")
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
@RestController
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    @ApiOperation(value = "지출 등록", notes = "지출을 등록합니다.")
    public ResponseEntity<Void> createExpense(
            @Valid @RequestBody ExpenseCreateRequestDto expenseCreateRequestDto) {

        expenseService.createExpense(expenseCreateRequestDto);

        return ResponseEntity.status(CREATED).build();
    }
}
