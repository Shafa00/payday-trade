package com.task.paydaytrade.configuration;

import com.task.paydaytrade.security.AuthenticationFilter;
import com.task.paydaytrade.security.AuthorizationFilter;
import com.task.paydaytrade.security.JwtService;
import com.task.paydaytrade.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.task.paydaytrade.utility.UrlConstant.SIGNUP_URL;
import static com.task.paydaytrade.utility.UrlConstant.STOCKS_LIST;

@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthorizationFilter authorizationFilter;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGNUP_URL).permitAll()
                .antMatchers(HttpMethod.GET, "/activate-user/**").permitAll()
                .antMatchers(HttpMethod.GET, STOCKS_LIST).permitAll()

                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()

                .anyRequest().authenticated();

        http
                .addFilter(new AuthenticationFilter(authenticationManager(), userService, jwtService));

        http
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .headers().frameOptions().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
