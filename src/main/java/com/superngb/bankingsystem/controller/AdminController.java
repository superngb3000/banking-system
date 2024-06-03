package com.superngb.bankingsystem.controller;

import com.superngb.bankingsystem.domain.user.UserInputBoundary;
import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.user.UserPostModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserInputBoundary userInputBoundary;

    public AdminController(UserInputBoundary userInputBoundary) {
        this.userInputBoundary = userInputBoundary;
    }

    @PostMapping("/users")
    public ResponseEntity<?> postUser(@RequestBody @Valid UserPostModel model){
        ResponseModel<?> responseModel = userInputBoundary.createUser(model);
        return new ResponseEntity<>(responseModel.getBody(), HttpStatus.valueOf(responseModel.getCode()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        ResponseModel<?> responseModel = userInputBoundary.deleteUser(id);
        return new ResponseEntity<>(responseModel.getBody(), HttpStatus.valueOf(responseModel.getCode()));
    }
}
