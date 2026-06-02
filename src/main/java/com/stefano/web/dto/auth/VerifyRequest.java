package com.stefano.web.dto.auth;

public record VerifyRequest(
    String email,
    String codeVerification
) {
}
