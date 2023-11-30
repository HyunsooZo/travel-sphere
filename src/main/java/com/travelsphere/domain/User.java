package com.travelsphere.domain;

import com.travelsphere.enums.UserStatus.UserRole;
import com.travelsphere.enums.UserStatus.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String phone;

    @Column
    private UserRole userRole;

    @Column
    private UserStatus userStatus;
}
