package com.task.paydaytrade.security;

import com.task.paydaytrade.model.user.UserAuthModel;
import com.task.paydaytrade.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.task.paydaytrade.utility.MessageConstant.USER_NOT_FOUND_BY_EMAIL_MSG;
import static com.task.paydaytrade.utility.MessageConstant.USER_NOT_FOUND_MSG;

@Configuration
@AllArgsConstructor
@Log4j2
public class MUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public static UserDetails map(UserAuthModel userAuthModel) {
        return new MUserDetails(userAuthModel.getId(), userAuthModel.getEmail(), userAuthModel.getPassword(),
                userAuthModel.getIsActive(), userAuthModel.getRoles());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userService.findAuthModelByEmail(email)
                .map(MUserDetailsService::map)
                .orElseThrow(() -> {
                    String message = String.format(USER_NOT_FOUND_BY_EMAIL_MSG, email);
                    log.warn(message);
                    return new UsernameNotFoundException(message);
                });

    }

    public UserDetails loadById(long userId) {
        return userService.findById(userId)
                .map(MUserDetailsService::map)
                .orElseThrow(() -> {
                    String message = String.format(USER_NOT_FOUND_MSG, userId);
                    log.warn(message);
                    return new UsernameNotFoundException(message);
                });
    }

}