package com.task.paydaytrade.service;

import com.task.paydaytrade.entity.User;
import com.task.paydaytrade.model.user.SignupRqModel;
import com.task.paydaytrade.model.user.SignupRsModel;
import com.task.paydaytrade.model.user.UserAuthModel;

import javax.mail.MessagingException;
import java.util.Optional;

public interface UserService {
    SignupRsModel signup(SignupRqModel signUpRqModel) throws MessagingException;

    User findByEmail(String email);

    void activateUser(String email);

    Optional<UserAuthModel> findAuthModelByEmail(String email);

    Optional<UserAuthModel> findById(long id);

}
