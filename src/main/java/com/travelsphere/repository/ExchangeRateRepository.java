package com.travelsphere.repository;

import com.travelsphere.domain.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, String> {
    List<ExchangeRate> findByCurrency(String currency);
}
