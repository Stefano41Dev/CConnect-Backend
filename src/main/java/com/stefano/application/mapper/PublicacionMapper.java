package com.stefano.application.mapper;

import com.stefano.domain.models.Publicacion;
import com.stefano.web.dto.publicacion.PublicacionDtoRequest;
import com.stefano.web.dto.publicacion.PublicacionDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PublicacionMapper {

    public Publicacion toEntity (PublicacionDtoRequest publicacionDtoRequest) {
        return Publicacion.builder()
                .contenido(publicacionDtoRequest.contenido())
                .build();
    }
    public PublicacionDtoResponse toDto (Publicacion publicacion) {
        return PublicacionDtoResponse.builder()
                .userid(publicacion.getUserid())
                .usernameAutor(publicacion.getUsername())
                .contenido(publicacion.getContenido())
                .fechaPublicacion(publicacion.getFechaPublicacion())
                .totalComentarios(publicacion.getTotalComentarios())
                .imagenesUrl(publicacion.getImagenesUrl())
                .build();

    }
}
