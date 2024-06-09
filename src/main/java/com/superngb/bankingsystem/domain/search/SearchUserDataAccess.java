package com.superngb.bankingsystem.domain.search;

import com.superngb.bankingsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface SearchUserDataAccess {

    Page<User> filter(String phone, String email, Date dateOfBirth, String fullName, Pageable pageable);
}
