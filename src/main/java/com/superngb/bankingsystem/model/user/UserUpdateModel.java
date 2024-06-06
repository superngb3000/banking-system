package com.superngb.bankingsystem.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateModel {
    @NotNull
    private Long id;
    @Pattern(regexp = "(^$|[0-9]{10})")
    private List<String> phone;
    @Email
    private List<String> email;
}
