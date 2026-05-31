package com.stefano.web.dto.comentario;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record ComentarioDtoResponse (
        String id,
        String username,
        String contenido,
        LocalDateTime fechaPublicacion
){
}
