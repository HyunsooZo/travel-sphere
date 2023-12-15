package com.travelsphere.domain;

import com.travelsphere.enums.ExpenseCategories;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Statistics extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String country;

    @Setter
    private String city;

    @Setter
    private Long numberOfExpenses;

    @Setter
    @Enumerated(EnumType.STRING)
    private ExpenseCategories category;

    @Setter
    private Double averageAmountInKrw;
}
