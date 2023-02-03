package com.task.paydaytrade.controller;

import com.task.paydaytrade.model.ResponseModel;
import com.task.paydaytrade.model.user.SignupRqModel;
import com.task.paydaytrade.model.user.SignupRsModel;
import com.task.paydaytrade.model.user.UserLoginModel;
import com.task.paydaytrade.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

import static com.task.paydaytrade.utility.MessageConstant.RESPONSE_MSG;
import static com.task.paydaytrade.utility.UrlConstant.ACTIVATE_USER_URL;
import static com.task.paydaytrade.utility.UrlConstant.SIGNUP_URL;
import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RestController
@Validated
@Log4j2
public class UserController {
    private final UserService userService;

    @PostMapping(SIGNUP_URL)
    public ResponseEntity<ResponseModel<SignupRsModel>> signup(@Valid @RequestBody SignupRqModel signupRqModel)
            throws MessagingException {
        var response = ResponseEntity.ok(ResponseModel.of(
                userService.signup(signupRqModel), HttpStatus.CREATED));

        log.info(RESPONSE_MSG, SIGNUP_URL, response);
        return response;
    }

    @Hidden
    @GetMapping(ACTIVATE_USER_URL)
    public ResponseEntity<ResponseModel<String>> activateUser(
            @Valid @PathVariable(name = "email") String email) {
        userService.activateUser(email);
        var response = ResponseEntity.ok(ResponseModel.of("User activated", OK));

        log.info(RESPONSE_MSG, ACTIVATE_USER_URL, response);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginForDocumentation(@RequestBody UserLoginModel userLoginModel) {
        return ResponseEntity.ok("");
    }

}
