package com.superngb.bankingsystem.repository;

import com.superngb.bankingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    Optional<User> findByEmailList(String email);

    Optional<User> findByPhoneList(String phone);

    boolean existsByEmailList(String email);

    boolean existsByPhoneList(String phone);
}
