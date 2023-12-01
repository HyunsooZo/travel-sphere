package com.travelsphere.controller.view;

import com.travelsphere.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Api(tags = "Email Verification API", description = "이메일 인증과 관련된 API")
@RequestMapping("/api/v1/users")
@Controller
public class UserViewController {
    private final UserService userService;

    @GetMapping("/{id}/verification")
    @ApiOperation(value = "사용자 회원인증", notes = "사용자 상태를 '인증' 상태로 변경합니다.")
    public String verifyUser(@PathVariable Long id, Model model) {

        String message = userService.verifyEmail(id);

        model.addAttribute("message", message);

        return "verification/verification-result";
    }
}
