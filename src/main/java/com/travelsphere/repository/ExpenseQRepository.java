package com.travelsphere.repository;

import com.travelsphere.domain.User;
import com.travelsphere.dto.ExpenseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseQRepository {
    Page<ExpenseDto> findAllByUserId(User user, Pageable pageable);
}
