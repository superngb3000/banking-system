package com.superngb.bankingsystem.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequestModel {
    private String dateOfBirth;
    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;
    private String fullName;
    @Email
    private String email;
}
