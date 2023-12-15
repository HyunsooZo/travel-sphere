package com.travelsphere.repository;

import com.travelsphere.domain.Statistics;
import com.travelsphere.enums.ExpenseCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Optional<Statistics> findByCityAndCategory(String cityName, ExpenseCategories category);

    List<Statistics> findByCity(String cityName);
}
