package com.stefano.web.dto.usuario;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record UsuarioDtoResponse (
         String id,
         String username,
         String email,
         LocalDate fechaNacimiento,
         List<String> amigosIds
){
}
