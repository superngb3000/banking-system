package com.superngb.bankingsystem.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestModel {
    private String login;
    private String password;
}
