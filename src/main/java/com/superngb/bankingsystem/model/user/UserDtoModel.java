package com.superngb.bankingsystem.model.user;

import com.superngb.bankingsystem.entuty.User;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoModel {

    private Long id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private Date dateOfBirth;
    private List<String> phone;
    private List<String> email;
    private String login;
    private Long accountId;

    public static UserDtoModel mapper(User object) {
        return new UserDtoModel(
                object.getId(),
                object.getLastName(),
                object.getFirstName(),
                object.getPatronymic(),
                object.getDateOfBirth(),
                object.getPhone(),
                object.getEmail(),
                object.getLogin(),
                object.getAccount().getId()
        );
    }

    public static List<UserDtoModel> mapper(List<User> objectList) {
        return objectList.stream()
                .map(UserDtoModel::mapper)
                .toList();
    }
}
