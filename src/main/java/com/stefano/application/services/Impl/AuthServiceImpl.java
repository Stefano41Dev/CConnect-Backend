package com.stefano.application.services.Impl;

import com.stefano.application.exception.ErrorNegocio;
import com.stefano.application.security.JwtService;
import com.stefano.application.services.AuthService;
import com.stefano.application.services.MailService;
import com.stefano.domain.models.Usuario;
import com.stefano.domain.repository.UsuarioRepository;
import com.stefano.web.dto.usuario.AuthRequest;
import com.stefano.web.dto.usuario.AuthResponse;
import com.stefano.web.dto.usuario.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new  BCryptPasswordEncoder();
    private final MailService mailService;
    @Override
    public AuthResponse register(RegisterRequest request) {

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new ErrorNegocio("El nombre de usuario ya está en uso.",HttpStatus.CONFLICT);
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ErrorNegocio("El email ya está en uso.",HttpStatus.CONFLICT);
        }

        Usuario user = new Usuario();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFechaNacimiento(request.getFechaNacimiento());
        user.setPasswordHash(bCryptPasswordEncoder.encode(request.getPassword()));

        String codeVerification = codeRandom();

        user.setCodeVerification(codeVerification);
        user.setCodeExpirations(LocalDateTime.now().plusMinutes(10));

        usuarioRepository.save(user);

        String link = "http://localhost:8080/auth/verify?email="
                + user.getEmail() + "&codigo=" + codeVerification;
        mailService.enviarCorreo(request.getEmail(),"CCONECT - Verificacion de cuenta", "Dale Click al siguiente enlace para verificar la cuenta \b " + link);

        //Todo: Necesitamos crear un endpoint para verificar y que este metodo devuelva un responde con success y message
        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        Usuario user = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ErrorNegocio("Usuario no encontrado.", HttpStatus.NOT_FOUND));

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ErrorNegocio("Contraseña incorrecta.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
    public String codeRandom() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }
}
