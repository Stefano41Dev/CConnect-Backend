package com.stefano.application.mapper;

import com.stefano.domain.models.Usuario;
import com.stefano.web.dto.usuario.UsuarioDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioDtoResponse toDto (Usuario usuario){
        return UsuarioDtoResponse.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .amigosIds(usuario.getAmigosIds())
                .build();
    }
}
