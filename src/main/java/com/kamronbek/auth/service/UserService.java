package com.kamronbek.auth.service;

import com.kamronbek.auth.model.ConfirmationToken;
import com.kamronbek.auth.model.RegistrationRequest;
import com.kamronbek.auth.model.User;
import com.kamronbek.auth.model.UserRole;
import com.kamronbek.auth.repository.UserRepository;
import com.kamronbek.auth.util.EmailBuilder;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with email " + email + " not found"));
    }



    public String signUp(RegistrationRequest request) {
        boolean isUserPresent = userRepository.findByEmail(request.getEmail()).isPresent();
        if(isUserPresent) {
            throw new IllegalStateException("Email " + request.getEmail() + " is already taken");
        }
        String password = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getFirstName(), request.getLastName(), request.getEmail(), password, UserRole.USER);
        userRepository.save(user);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                null,
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        String activationLink = "http://localhost:8080/api/v1/users/confirm/?token=" + token;
        emailService.sendEmail(request.getEmail(), EmailBuilder.buildEmail(activationLink));
        return token;
    }

    public void activateUser(String token) {
        confirmationTokenService.confirmToken(token);
    }
}
