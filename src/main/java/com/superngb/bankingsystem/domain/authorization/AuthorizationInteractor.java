package com.superngb.bankingsystem.domain.authorization;

import com.superngb.bankingsystem.config.JWTUtil;
import com.superngb.bankingsystem.entity.User;
import com.superngb.bankingsystem.model.LoginRequestModel;
import com.superngb.bankingsystem.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationInteractor implements AuthorizationInputBoundary {

    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthorizationUserDataAccess authorizationUserDataAccess;

    public AuthorizationInteractor(JWTUtil jwtUtil,
                                   BCryptPasswordEncoder bCryptPasswordEncoder,
                                   AuthorizationUserDataAccess authorizationUserDataAccess) {
        this.jwtUtil = jwtUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authorizationUserDataAccess = authorizationUserDataAccess;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authorizationUserDataAccess.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public ResponseModel<?> login(LoginRequestModel loginRequestModel) {
        User userByLogin = authorizationUserDataAccess.findByLogin(loginRequestModel.getLogin());

        if (userByLogin == null) {
            return ResponseModel.builder().code(HttpStatus.NOT_FOUND.value()).body("Неправильный ввод данных").build();
        }
        System.out.println(userByLogin.getLogin());
        if (bCryptPasswordEncoder.matches(loginRequestModel.getPassword(), userByLogin.getPassword())) {
            String token = jwtUtil.generateToken(loginRequestModel.getLogin());
            return ResponseModel.builder().code(HttpStatus.OK.value()).body(token).build();
        } else {
            return ResponseModel.builder().code(HttpStatus.NOT_FOUND.value()).body("Неправильный ввод данных").build();
        }
    }
}
