package com.travelsphere.controller;

import com.travelsphere.config.JwtProvider;
import com.travelsphere.dto.ExpenseCreateRequestDto;
import com.travelsphere.dto.ExpenseDto;
import com.travelsphere.dto.ExpenseInquiryResponseDto;
import com.travelsphere.dto.ExpenseModificationRequestDto;
import com.travelsphere.service.ExpenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;

@Api(tags = "Expense API", description = "지출 관련 API")
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
@RestController
public class ExpenseController {
    private final ExpenseService expenseService;
    private final JwtProvider jwtProvider;

    /**
     * 지출 등록
     *
     * @param token jwt 토큰
     * @param expenseCreateRequestDto 지출 등록 요청 DTO
     * @return ResponseEntity<Void>
     */
    @PostMapping
    @ApiOperation(value = "지출 등록", notes = "지출을 등록합니다.")
    public ResponseEntity<Void> createExpense(
            @RequestHeader(AUTHORIZATION) String token,
            @Valid @RequestBody ExpenseCreateRequestDto expenseCreateRequestDto) {

        Long userId = jwtProvider.getIdFromToken(token);
        expenseService.createExpense(userId, expenseCreateRequestDto);

        return ResponseEntity.status(CREATED).build();
    }

    /**
     * 지출 조회 (페이징)
     * @param token jwt 토큰
     * @param page 페이지 번호
     * @param size 페이지 사이즈
     * @return ResponseEntity<ExpenseInquiryResponseDto>
     */
    @GetMapping
    @ApiOperation(value = "지출 조회", notes = "지출을 조회합니다.")
    public ResponseEntity<ExpenseInquiryResponseDto> getExpenses(
            @RequestHeader(AUTHORIZATION) String token,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Long userId = jwtProvider.getIdFromToken(token);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<ExpenseDto> expenses = expenseService.getExpenses(userId,pageRequest);

        return ResponseEntity.status(OK).body(ExpenseInquiryResponseDto.from(expenses));
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "지출 수정", notes = "지출을 수정합니다.")
    public ResponseEntity<Void> updateExpense(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable Long id,
            @Valid @RequestBody ExpenseModificationRequestDto expenseModificationRequestDto) {

        Long userId = jwtProvider.getIdFromToken(token);

        expenseService.updateExpense(userId, id, expenseModificationRequestDto);

        return ResponseEntity.status(NO_CONTENT).build();
    }
}
