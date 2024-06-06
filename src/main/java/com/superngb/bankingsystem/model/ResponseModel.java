package com.superngb.bankingsystem.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel<T> {

    private Integer code;
    private T body;
}
