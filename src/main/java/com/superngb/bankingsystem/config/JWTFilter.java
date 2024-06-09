package com.superngb.bankingsystem.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.superngb.bankingsystem.domain.authorization.AuthorizationInputBoundary;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final AuthorizationInputBoundary authorizationInputBoundary;

    @Autowired
    private JWTUtil jwtUtil;

    public JWTFilter(AuthorizationInputBoundary authorizationInputBoundary) {
        this.authorizationInputBoundary = authorizationInputBoundary;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String jwt = authHeader.substring(7);
            if(jwt.isEmpty()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            }else {
                try{
                    String login = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    UserDetails userDetails = authorizationInputBoundary.loadUserByUsername(login);
                    System.out.println(login);
                    System.out.println(userDetails.getUsername());
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(login, userDetails.getPassword());
                    if(SecurityContextHolder.getContext().getAuthentication() == null){
                        System.out.println("lovushka");
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                    System.out.println(SecurityContextHolder.getContext().getAuthentication().toString());
                }catch(JWTVerificationException exc){
                    System.out.println("PPPPPPP");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
