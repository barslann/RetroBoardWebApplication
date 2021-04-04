package com.coderman.retroboard.security;

import com.coderman.retroboard.domain.User;
import com.coderman.retroboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userDetailService;

    // ignores Spring Security for the URL /h2-console/ and all of its sub URLs
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    //configures formLogin, log out and access to all URLs(/**) that have the user role (ROLE_USER) authorization.
    // This,in turn, means the user needs to be authenticated and  anonymous users will not be allowed to acces anything!
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .logout()
                .permitAll()
                .and()
                .authorizeRequests()
                    .antMatchers("/**")
                    .hasRole("USER");
    }

    // used to configure authenticationProvider with our implementation for UserDetailsService(USerService) and PasswordEncoder, in our case BCryptPasswordEncoder()
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailService);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // it is used to insert some users into the database at startup!
    @Bean
    public ApplicationRunner applicationRunner(){
        return args -> {
            userDetailService.create(new User(null,"bekir",passwordEncoder().encode("password"),"ROLE_USER"));
            userDetailService.create(new User(null,"beck",passwordEncoder().encode("password"),"ROLE_USER"));
        };
    }
}
