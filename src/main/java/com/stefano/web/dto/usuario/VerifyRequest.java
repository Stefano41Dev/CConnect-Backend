package com.stefano.web.dto.usuario;

public record VerifyRequest(
    String email,
    String codeVerification
) {
}
