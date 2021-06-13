package com.kamronbek.auth.service;

import com.kamronbek.auth.model.ConfirmationToken;
import com.kamronbek.auth.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Transactional
    public void confirmToken(String token) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
        confirmationToken.ifPresentOrElse(
                confToken -> {
                    if (confToken.getConfirmedAt() != null) {
                        throw new IllegalStateException("Email is already confirmed");
                    }

                    if (confToken.getExpiresAt().isBefore(LocalDateTime.now())) {
                        throw new IllegalStateException("Token Expired");
                    }

                    confToken.setConfirmedAt(LocalDateTime.now());
                    confToken.getUser().setEnabled(true);
                }, () -> {
                    throw new IllegalStateException("Token Not Found");
                });
    }
}
