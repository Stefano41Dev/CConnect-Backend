package com.stefano.application.services;

import com.stefano.web.dto.comentario.ComentarioDtoRequest;
import com.stefano.web.dto.comentario.ComentarioDtoResponse;

public interface ComentarioService {
    ComentarioDtoResponse agregarComentario(ComentarioDtoRequest comentarioDtoRequest);
}
