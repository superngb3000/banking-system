package com.superngb.bankingsystem.model.user;

import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateModel {
    @NotNull
    private Long id;
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phone;
    @Email
    private String email;
}
