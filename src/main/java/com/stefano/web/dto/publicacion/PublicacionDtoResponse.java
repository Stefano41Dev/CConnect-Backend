package com.stefano.web.dto.publicacion;

import com.stefano.web.dto.comentario.ComentarioDtoResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Builder
@Data
public class PublicacionDtoResponse {
        private String userid;
        private String usernameAutor;
        private String contenido;
        private LocalDateTime fechaPublicacion;
        private Long totalComentarios;
        private List<String> imagenesUrl;
}
