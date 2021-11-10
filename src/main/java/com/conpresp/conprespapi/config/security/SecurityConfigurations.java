package com.conpresp.conprespapi.config.security;

import com.conpresp.conprespapi.repository.UserRepository;
import com.conpresp.conprespapi.service.AuthenticationService;
import com.conpresp.conprespapi.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Autowired
    public SecurityConfigurations(AuthenticationService authenticationService,TokenService tokenService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/users").hasRole("MODERATOR")
                .antMatchers(HttpMethod.POST, "/users").hasRole("ADMINISTRATOR")
                .antMatchers("/auth").permitAll()
                .anyRequest().authenticated();

        http.httpBasic().disable();

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new TokenFilterAuthentication(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
    }
}
