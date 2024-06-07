package com.superngb.bankingsystem.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateEmailListModel {
    @NotNull
    private Long id;
    @NotEmpty
    private List<@Email String> emailList;
}