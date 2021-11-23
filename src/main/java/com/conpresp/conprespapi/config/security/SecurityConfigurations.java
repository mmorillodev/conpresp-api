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
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    private static final String ROLE_MODERATOR = "MODERATOR";
    private static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";

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
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority(ROLE_MODERATOR, ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.POST, "/users").hasAuthority(ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.DELETE, "/users").hasAuthority(ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.PUT, "/users/**").hasAuthority(ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.POST, "/property").hasAnyAuthority(ROLE_ADMINISTRATOR, ROLE_MODERATOR)
                .antMatchers("/auth").permitAll()
                .anyRequest().authenticated();

        http.httpBasic().disable();

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new TokenFilterAuthentication(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
    }
}
