package com.task.paydaytrade.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.paydaytrade.exception.InvalidModelException;
import com.task.paydaytrade.model.ResponseModel;
import com.task.paydaytrade.model.user.UserLoginModel;
import com.task.paydaytrade.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.task.paydaytrade.utility.Constant.*;
import static com.task.paydaytrade.utility.MessageConstant.*;
import static com.task.paydaytrade.utility.UrlConstant.LOGIN_URL;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserService userService;
    private final JwtService jwtService;
    private final ObjectMapper om = new ObjectMapper();

    public AuthenticationFilter(AuthenticationManager authManager,
                                UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
        super.setAuthenticationManager(authManager);
        super.setFilterProcessesUrl(LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            var login = om.readValue(request.getInputStream(), UserLoginModel.class);

            return getAuthenticationManager()
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(login.email(), login.password()));
        } catch (IOException e) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        var email = ((UserDetails) authResult.getPrincipal()).getUsername();

        var userAuthModel = userService.findAuthModelByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_EMAIL_MSG, email)));

        var token = jwtService.generateToken(userAuthModel.getId());

        generateResponse(response, HttpStatus.OK, String.format(JWT_TOKEN_FORMAT, JWT_PREFIX, token));
        logger.info(String.format(JWT_TOKEN_GENERATED_MSG, userAuthModel.getEmail()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        generateResponse(response, HttpStatus.BAD_REQUEST, INVALID_CREDENTIALS_MSG);
        logger.info(INVALID_CREDENTIALS_MSG);
    }

    private void generateResponse(HttpServletResponse res, HttpStatus status, Object body) throws IOException {
        res.setStatus(status.value());
        res.setContentType(CONTENT_TYPE_JSON);

        om.writeValue(
                res.getOutputStream(),
                ResponseModel.builder()
                        .status(status)
                        .body(body)
                        .build());
    }
}
