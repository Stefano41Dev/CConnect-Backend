package com.stefano.application.services.Impl;

import com.stefano.application.exception.ErrorNegocio;
import com.stefano.application.mapper.ComentarioMapper;
import com.stefano.application.services.ComentarioService;
import com.stefano.domain.models.Comentario;
import com.stefano.domain.models.Publicacion;
import com.stefano.domain.models.Usuario;
import com.stefano.domain.repository.ComentarioRepository;
import com.stefano.domain.repository.PublicacionRepository;
import com.stefano.web.dto.comentario.ComentarioDtoRequest;
import com.stefano.web.dto.comentario.ComentarioDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;
    private final PublicacionRepository publicacionRepository;
    @Override
    public ComentarioDtoResponse agregarComentario(ComentarioDtoRequest comentarioDtoRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Comentario comentario = comentarioMapper.toEntity(comentarioDtoRequest);
        comentario.setUsername(usuario.getUsername());
        comentario.setUserId(usuario.getId());
        comentario.setFechaPublicacion(LocalDateTime.now());

        Comentario comentarioGuardado = comentarioRepository.save(comentario);

        Publicacion publicacion = publicacionRepository.findById(comentarioGuardado.getPublicacionId())
                .orElseThrow(()-> new ErrorNegocio("No se encontro la publicacion", HttpStatus.NOT_FOUND));
        publicacion.setTotalComentarios(publicacion.getTotalComentarios() + 1); //ToDo: Parece que el puede fallar si dos usuarios lo usan al mismo tiempo CORREGIR
        publicacionRepository.save(publicacion);
        return comentarioMapper.toDto(comentarioGuardado);
    }
}
