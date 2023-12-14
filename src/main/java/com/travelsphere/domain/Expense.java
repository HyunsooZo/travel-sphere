package com.travelsphere.domain;

import com.travelsphere.enums.ExpenseCategories;
import lombok.*;

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

    @Setter
    @Enumerated(EnumType.STRING)
    private ExpenseCategories category;

    @Setter
    private String currency;

    @Setter
    private Double amount;

    @Setter
    private Double amountOfKrw;

    @Setter
    private Date date;

    @Setter
    private String city;

    @Setter
    private String country;
}
