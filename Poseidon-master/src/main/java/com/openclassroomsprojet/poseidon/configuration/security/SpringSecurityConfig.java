package com.openclassroomsprojet.poseidon.configuration.security;

import com.openclassroomsprojet.poseidon.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;

/**
 * This class allows you to set up and configure spring security
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(myUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(authenticationProvider())
                .jdbcAuthentication()
                .dataSource(dataSource);
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/", "/login", "/error/**", "/css/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login().defaultSuccessUrl("/bidList/list")
                .and()
                .formLogin()
                .defaultSuccessUrl("/bidList/list")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/app-logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe().key("uniqueAndSecret")
                .and().exceptionHandling()
                .accessDeniedPage("/app/error");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}