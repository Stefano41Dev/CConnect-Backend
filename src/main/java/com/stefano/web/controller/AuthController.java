package com.stefano.web.controller;

import com.stefano.application.services.AuthService;
import com.stefano.web.dto.usuario.*;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public MessageResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @GetMapping("/verify")
    public MessageResponse verify(
            @PathParam("email") String email,
            @PathParam("code") String code
    ){
        return authService.verify(new VerifyRequest(email,code));
    }
}
