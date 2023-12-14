package com.travelsphere.domain;

import com.travelsphere.enums.ExpenseCategories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Expense extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private ExpenseCategories category;

    private String currency;

    private Double amount;

    private Double amountOfKrw;

    private Date date;

    private String city;

    private String country;
}
