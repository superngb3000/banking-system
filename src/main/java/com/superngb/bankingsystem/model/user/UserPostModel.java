package com.superngb.bankingsystem.model.user;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserPostModel {
    @NotBlank
    private String lastName;
    @NotBlank
    private String firstName;
    @NotBlank
    private String patronymic;
    @Past
    private Date dateOfBirth;
    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;
    @Email
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotNull
    private BigDecimal initialBalance;
}
