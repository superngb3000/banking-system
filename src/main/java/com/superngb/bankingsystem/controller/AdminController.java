package com.superngb.bankingsystem.controller;

import com.superngb.bankingsystem.domain.admin.AdminUserInputBoundary;
import com.superngb.bankingsystem.model.ResponseModel;
import com.superngb.bankingsystem.model.user.UserPostModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminUserInputBoundary adminUserInputBoundary;

    public AdminController(AdminUserInputBoundary adminUserInputBoundary) {
        this.adminUserInputBoundary = adminUserInputBoundary;
    }

    @PostMapping("/users")
    public ResponseEntity<?> postUser(@RequestBody @Valid UserPostModel model) {
        ResponseModel<?> responseModel = adminUserInputBoundary.createUser(model);
        return new ResponseEntity<>(responseModel.getBody(), HttpStatus.valueOf(responseModel.getCode()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        ResponseModel<?> responseModel = adminUserInputBoundary.deleteUser(id);
        return new ResponseEntity<>(responseModel.getBody(), HttpStatus.valueOf(responseModel.getCode()));
    }
}
