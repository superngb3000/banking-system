package com.superngb.bankingsystem.model.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;


@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePhoneListModel {
    @NotNull
    private Long id;
    @NotEmpty
    private List<@Pattern(regexp = "^[0-9]{10}$") String> phoneList;
}
