package com.travelsphere.controller;

import com.travelsphere.service.ExpenseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Expense API", description = "지출 관련 API")
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
@RestController
public class ExpenseController {
    private final ExpenseService expenseService;
}
