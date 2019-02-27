package com.wishlist.security.config;

import com.wishlist.security.details.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/js/**", "/css/**", "/fonts/**", "/img/**").permitAll()
                .antMatchers("/signUp/**").permitAll()
                .antMatchers("/users/**").authenticated()
                .antMatchers("/wishes/**").authenticated()
                .antMatchers("/requests/**").authenticated()
                .antMatchers("/friends/**").authenticated()
                .and()
                .formLogin()
                .usernameParameter("mail")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .loginPage("/login")
                .and().csrf()
                ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
