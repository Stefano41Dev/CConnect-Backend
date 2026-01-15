package com.stefano.web.dto.comentario;

import lombok.Builder;

@Builder
public record ComentarioDtoRequest(
        String idPublicacion,
        String contenido

) {
}
