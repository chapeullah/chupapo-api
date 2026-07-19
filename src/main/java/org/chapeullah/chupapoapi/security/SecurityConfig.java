package org.chapeullah.chupapoapi.security;

import org.chapeullah.chupapoapi.iam.account.model.Account;
import org.chapeullah.chupapoapi.iam.account.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 32;
    private static final int PARALLELISM = 1;
    private static final int MEMORY_KB = 64 * 1024;
    private static final int ITERATIONS = 3;

    /**
     * Defines the password encoder to be used for encoding passwords.
     *
     * @return a {@link PasswordEncoder} using Argon2 hashing algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(
                SALT_LENGTH,
                HASH_LENGTH,
                PARALLELISM,
                MEMORY_KB,
                ITERATIONS);
    }

    @Bean
    public UserDetailsService userDetailsService(
            AccountRepository accountRepository) {
        return username -> {
            Account account = accountRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
            List<GrantedAuthority> authorities = account
                    .getRole()
                    .getPermissions()
                    .stream()
                    .<GrantedAuthority>map(permission -> new SimpleGrantedAuthority(permission.name()))
                    .toList();
            return User.withUsername(account.getUsername())
                    .password(account.getPasswordHash())
                    .disabled(!account.isEnabled())
                    .authorities(authorities)
                    .build();
        };
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/auth/csrf").permitAll()
                        .anyRequest().authenticated());
        http.formLogin(
                form -> form
                        .loginProcessingUrl("/api/auth/login")
                        .successHandler((request, response, authentication) ->
                                response.setStatus(HttpStatus.NO_CONTENT.value()))
                        .failureHandler((request, response, exception) ->
                                response.sendError(HttpStatus.UNAUTHORIZED.value()))
                        .permitAll());
        http.exceptionHandling(
                exceptions -> exceptions
                        .authenticationEntryPoint((request, response, exception) ->
                                response.sendError(HttpStatus.UNAUTHORIZED.value()))
                        .accessDeniedHandler((request, response, exception) ->
                                response.sendError(HttpStatus.FORBIDDEN.value())));
        http.logout(
                logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(
                                (request, response, authentication) ->
                                        response.setStatus(HttpStatus.NO_CONTENT.value())));
        http.requestCache(RequestCacheConfigurer::disable);
        return http.build();
    }
}