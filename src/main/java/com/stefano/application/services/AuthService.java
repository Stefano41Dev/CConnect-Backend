package com.stefano.application.services;

import com.stefano.web.dto.auth.*;

public interface AuthService {
    MessageResponse register(RegisterRequest registerRequest);
    AuthResponse login(AuthRequest authRequest);
    MessageResponse verify(VerifyRequest verifyRequest);
}
