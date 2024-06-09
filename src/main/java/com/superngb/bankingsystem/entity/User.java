package com.superngb.bankingsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

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

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "date_of_birth", columnDefinition = "DATE")
    private Date dateOfBirth;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "user_phone")
    private List<String> phoneList;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "user_email")
    private List<String> emailList;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "account")
    private Account account;
}
