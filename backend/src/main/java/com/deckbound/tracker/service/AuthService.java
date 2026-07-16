package com.deckbound.tracker.service;

import com.deckbound.tracker.dto.request.LoginRequest;
import com.deckbound.tracker.dto.request.RegisterRequest;
import com.deckbound.tracker.dto.response.AuthResponse;
import com.deckbound.tracker.exception.BusinessException;
import com.deckbound.tracker.exception.EmailAlreadyExistsException;
import com.deckbound.tracker.repository.UserRepository;
import com.deckbound.tracker.security.JwtService;
import lombok.RequiredArgsConstructor;
import com.deckbound.tracker.model.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException();
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setDisplayName(request.displayName());

        User saved = userRepository.save(user);

        String token = jwtService.generateToken(saved);

        return new AuthResponse(
                token,
                saved.getId().toString(),
                saved.getEmail(),
                saved.getDisplayName()
        );

    }

    @Transactional
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Email or password incorrect"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException("Email or password incorrect");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(
                token,
                user.getId().toString(),
                user.getEmail(),
                user.getDisplayName()
        );
    }

}
