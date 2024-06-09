package com.superngb.bankingsystem.repository;

import com.superngb.bankingsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    Optional<User> findByEmailList(String email);

    Optional<User> findByPhoneList(String phone);

    boolean existsByEmailList(String email);

    boolean existsByPhoneList(String phone);

    @Query("select u from User u " +
            "where (:phone is null or (:phone member of u.phoneList)) " +
            "and (:email is null or (:email member of u.emailList)) " +
            "and ((cast(:dateOfBirth as date) is null ) or (u.dateOfBirth > :dateOfBirth)) " +
            "and (:fullName is null or (concat(u.lastName, ' ', u.firstName, ' ', u.patronymic) like concat(:fullName, '%')))")
    Page<User> filter(@Param("phone") String phone, @Param("email") String email, @Param("dateOfBirth") Date dateOfBirth, @Param("fullName") String fullName, Pageable pageable);
}
