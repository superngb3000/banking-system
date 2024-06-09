package com.superngb.bankingsystem.config;

import com.superngb.bankingsystem.domain.admin.AdminInteractor;
import com.superngb.bankingsystem.domain.authorization.AuthorizationInteractor;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    private final JWTFilter filter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthorizationInteractor authorizationInteractor;
    private final AdminInteractor adminInteractor;

    private final AuthenticationConfiguration configuration;

    public WebSecurityConfig(JWTFilter filter,
                             BCryptPasswordEncoder bCryptPasswordEncoder,
                             AuthorizationInteractor authorizationInteractor,
                             AdminInteractor adminInteractor,
                             AuthenticationConfiguration configuration) {
        this.filter = filter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authorizationInteractor = authorizationInteractor;
        this.adminInteractor = adminInteractor;
        this.configuration = configuration;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "PATCH")
                .allowCredentials(true);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        AbstractAuthenticationProcessingFilter filter = new UsernamePasswordAuthenticationFilter(authenticationManager());
//        filter.setFilterProcessesUrl("/login");
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/admin/**").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers("/user/**").authenticated();
                    authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.GET,"/search/**").authenticated();
                })
                .userDetailsService(authorizationInteractor)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")))
                .authenticationManager(authenticationManager())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilter(filter);
//        http.authenticationManager(authenticationManager());

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Autowired
    void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(adminInteractor).passwordEncoder(bCryptPasswordEncoder);
        builder.userDetailsService(authorizationInteractor).passwordEncoder(bCryptPasswordEncoder);
    }
}
