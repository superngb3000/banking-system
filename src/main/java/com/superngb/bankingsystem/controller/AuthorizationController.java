package com.superngb.bankingsystem.controller;

import com.superngb.bankingsystem.domain.authorization.AuthorizationInputBoundary;
import com.superngb.bankingsystem.model.LoginRequestModel;
import com.superngb.bankingsystem.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthorizationController {

    private final AuthorizationInputBoundary authorizationInputBoundary;

    public AuthorizationController(AuthorizationInputBoundary authorizationInputBoundary) {
        this.authorizationInputBoundary = authorizationInputBoundary;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestModel model) {
        ResponseModel<?> responseModel = authorizationInputBoundary.login(model);
        return new ResponseEntity<>(responseModel.getBody(), HttpStatus.valueOf(responseModel.getCode()));
    }
}
