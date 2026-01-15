package com.stefano.application.mapper;

import com.stefano.domain.models.Comentario;
import com.stefano.web.dto.comentario.ComentarioDtoRequest;
import com.stefano.web.dto.comentario.ComentarioDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class ComentarioMapper {
    public ComentarioDtoResponse toDto(Comentario comentario) {
        return ComentarioDtoResponse.builder()
                .username(comentario.getUsername())
                .contenido(comentario.getContenido())
                .fechaPublicacion(comentario.getFechaPublicacion())
                .build();
    }

    public Comentario toEntity(ComentarioDtoRequest request){
        return Comentario.builder()
                .contenido(request.contenido())
                .publicacionId(request.idPublicacion())
                .build();
    }
}
