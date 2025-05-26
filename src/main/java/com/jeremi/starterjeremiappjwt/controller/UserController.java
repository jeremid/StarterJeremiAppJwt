package com.jeremi.starterjeremiappjwt.controller;

import com.jeremi.starterjeremiappjwt.auth.model.AuthenticationRequest;
import com.jeremi.starterjeremiappjwt.auth.model.ResetPasswordRequest;
import com.jeremi.starterjeremiappjwt.dto.UserDto;
import com.jeremi.starterjeremiappjwt.service.UserService;
import com.jeremi.starterjeremiappjwt.util.mapper.UserMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@ControllerAdvice
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;


    //POST
    @PostMapping("/forgot-password-code")
    public ResponseEntity<?> sendForgotPasswordCode(@RequestBody AuthenticationRequest request) {
        userService.confirmResetPassword(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ResetPasswordRequest request) {
        UserDto userDto = userMapper.toUserDto(userService.resetPassword(request));
        return ResponseEntity.ok(userDto);
    }

}
