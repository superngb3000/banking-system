package com.superngb.bankingsystem.controller;

import com.superngb.bankingsystem.domain.account.AccountInputBoundary;
import com.superngb.bankingsystem.domain.user.UserInputBoundary;
import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.account.TransferRequestModel;
import com.superngb.bankingsystem.model.user.UserUpdateModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AccountInputBoundary accountInputBoundary;
    private final UserInputBoundary userInputBoundary;

    public UserController(AccountInputBoundary accountInputBoundary, UserInputBoundary userInputBoundary) {
        this.accountInputBoundary = accountInputBoundary;
        this.userInputBoundary = userInputBoundary;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequestModel model) {
        ResponseModel<?> responseModel = accountInputBoundary.transferMoney(model);
        return new ResponseEntity<>(responseModel.getBody(), HttpStatus.valueOf(responseModel.getCode()));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateModel model) {
        ResponseModel<?> responseModel = userInputBoundary.updateUser(model);
        return new ResponseEntity<>(responseModel.getBody(), HttpStatus.valueOf(responseModel.getCode()));
    }
}
