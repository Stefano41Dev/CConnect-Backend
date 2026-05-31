package com.stefano.application.services;

import com.stefano.web.dto.comentario.ComentarioDtoRequest;
import com.stefano.web.dto.comentario.ComentarioDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComentarioService {
    ComentarioDtoResponse agregarComentario(ComentarioDtoRequest comentarioDtoRequest);
    Page<ComentarioDtoResponse> listarComentariosPublicacion(String idPublicacion, Pageable pageable);
}
