package com.project.pixelstore.controllers;

import com.project.pixelstore.dtos.UserDTO;
import com.project.pixelstore.dtos.UserLoginDTO;
import com.project.pixelstore.mappers.UserMapper;
import com.project.pixelstore.models.User;
import com.project.pixelstore.responses.ApiResponse;
import com.project.pixelstore.services.UserService;
import com.project.pixelstore.components.LocalizationUtils;
import com.project.pixelstore.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final LocalizationUtils localizationUtils;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ApiResponse<?> createUser(
            @Valid @RequestBody UserDTO userDTO
            , BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).toList();
                return ApiResponse.badRequest(errors);
            }
            User user = userService.createUser(userDTO);
            return ApiResponse.builder()
                    .data(userMapper.register(user))
                    .status(HttpStatus.OK.value())
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY))
                    .build();
        } catch (Exception e) {
            return ApiResponse.badRequest(List.of(
                    localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_FAILED, e.getMessage())
            ));
        }
    }

    @PostMapping("/login")
    public ApiResponse<?> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
    ) {
        try {
            String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
            return ApiResponse.ok(token);
        } catch (Exception e) {
            return ApiResponse.badRequest(List.of(
                    localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED, e.getMessage())
            ));
        }
    }
}
