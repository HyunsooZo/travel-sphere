package com.travelsphere.service;


import com.travelsphere.exception.CustomException;
import com.travelsphere.exception.ErrorCode;
import com.travelsphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.travelsphere.domain.User userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_INFO_NOT_FOUND));

        List<GrantedAuthority> authorities =
                new ArrayList<>(userEntity.getUserRole().getAuthorities());

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(authorities)
                .build();
    }
}
