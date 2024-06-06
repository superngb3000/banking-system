package com.superngb.bankingsystem.entuty;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "dateOfBirth", columnDefinition = "DATE")
    private Date dateOfBirth;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "phone")
    private List<String> phone;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "email")
    private List<String> email;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "account")
    private Account account;
}
