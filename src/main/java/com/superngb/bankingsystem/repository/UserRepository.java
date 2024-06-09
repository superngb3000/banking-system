package com.superngb.bankingsystem.repository;

import com.superngb.bankingsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    Optional<User> findByEmailList(String email);

    Optional<User> findByPhoneList(String phone);

    boolean existsByEmailList(String email);

    boolean existsByPhoneList(String phone);

    @Query(value =
            "select u.*, array_agg(up.phone_list) as phones,array_agg(ue.email_list) as emails\n" +
            "from users u\n" +
            "         inner join user_phone up on u.id = up.user_id\n" +
            "         inner join user_email ue on u.id = ue.user_id\n" +
            "where (:phone is null or (:phone = up.phone_list))\n" +
            "  and (:email is null or (:email = ue.email_list))\n" +
            "  and (cast(:dateOfBirth as date) is null or (u.date_of_birth > cast(:dateOfBirth as date)))\n" +
            "  and (:fullName is null or (concat(u.last_name, ' ', u.first_name, ' ', u.patronymic) like concat(:fullName, '%')))\n" +
            "group by u.id",
            nativeQuery = true)
    Page<User> filter(@Param("phone") String phone, @Param("email") String email, @Param("dateOfBirth") String dateOfBirth, @Param("fullName") String fullName, Pageable pageable);
}
