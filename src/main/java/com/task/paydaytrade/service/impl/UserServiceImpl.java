package com.task.paydaytrade.service.impl;

import com.task.paydaytrade.entity.Account;
import com.task.paydaytrade.entity.Role;
import com.task.paydaytrade.entity.User;
import com.task.paydaytrade.exception.DataNotFoundException;
import com.task.paydaytrade.exception.DuplicateUserException;
import com.task.paydaytrade.model.user.SignupRqModel;
import com.task.paydaytrade.model.user.SignupRsModel;
import com.task.paydaytrade.model.user.UserAuthModel;
import com.task.paydaytrade.repository.RoleRepository;
import com.task.paydaytrade.repository.UserRepository;
import com.task.paydaytrade.service.UserService;
import com.task.paydaytrade.utility.MailSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.task.paydaytrade.utility.Constant.CONFIRMATION_BODY;
import static com.task.paydaytrade.utility.Constant.CONFIRMATION_SUBJECT;
import static com.task.paydaytrade.utility.MessageConstant.DUPLICATE_USER_MSG;
import static com.task.paydaytrade.utility.MessageConstant.ROLE_NOT_FOUND_MSG;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final BCryptPasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Override
    public SignupRsModel signup(SignupRqModel signupRqModel) throws MessagingException {
        checkEmailUniqueness(signupRqModel.email());

        var user = createUser(signupRqModel);

        mailSenderService.sendEmail(signupRqModel.email(), CONFIRMATION_SUBJECT,
                format(CONFIRMATION_BODY, "http://localhost:8080/activate-user/" + signupRqModel.email()));

        return new SignupRsModel(user.getExternalId(), user.getName(), user.getEmail());
    }

    @Override
    public void activateUser(String email) {
        User user = findByEmail(email);
        user.setIsActive(true);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException(
                format("User not found by given email: %s", email)));
    }

    @Override
    public Optional<UserAuthModel> findAuthModelByEmail(String email) {
        return userRepository.findByEmailAndIsActiveTrue(email).map(UserAuthModel::new);
    }

    @Override
    public Optional<UserAuthModel> findById(long id) {
        return userRepository.findById(id).map(UserAuthModel::new);
    }

    private User createUser(SignupRqModel signupRqModel) {
        var user = User.builder()
                .externalId(UUID.randomUUID().toString())
                .name(signupRqModel.name())
                .email(signupRqModel.email())
                .password(encoder.encode(signupRqModel.password()))
                .roles(Collections.singletonList(getRole()))
                .isActive(Boolean.FALSE)
                .account(Account.builder()
                        .externalId(UUID.randomUUID().toString())
                        .accountNumber(UUID.randomUUID().toString().replace("-", ""))
                        .amount(BigDecimal.ZERO)
                        .build())
                .build();
        userRepository.save(user);

        return user;
    }

    private void checkEmailUniqueness(String email) {
        if (userRepository.findByEmail(email).isPresent())
            throw new DuplicateUserException(DUPLICATE_USER_MSG);
    }

    private Role getRole() {
        return roleRepository.findByName("ROLE_USER").orElseThrow(
                () -> new DataNotFoundException(ROLE_NOT_FOUND_MSG));
    }
}
