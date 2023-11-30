package com.travelsphere.controller;

import com.travelsphere.dto.UserDto;
import com.travelsphere.dto.UserSignUpRequestDto;
import com.travelsphere.service.EmailService;
import com.travelsphere.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.travelsphere.enums.EmailComponents.EMAIL_CONTENT;
import static com.travelsphere.enums.EmailComponents.EMAIL_SUBJECT;
import static org.springframework.http.HttpStatus.CREATED;

@Api(tags = "User API", description = "사용자 관련 API")
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @PostMapping
    @ApiOperation(value = "사용자 회원가입", notes = "사용자 회원가입 진행")
    public ResponseEntity<Void> signUp(
            @Valid @RequestBody UserSignUpRequestDto userSignUpRequestDto) {

        UserDto userDto = userService.signUp(userSignUpRequestDto);

        emailService.sendEmail(
                userDto.getEmail(),
                EMAIL_SUBJECT,
                String.format(EMAIL_CONTENT, userDto.getId()));

        return ResponseEntity.status(CREATED).build();
    }
}
