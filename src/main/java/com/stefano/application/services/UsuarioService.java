package com.stefano.application.services;


import com.stefano.web.dto.usuario.UsuarioPerfilDtoResponse;
import com.stefano.web.dto.usuario.UsuarioDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {
    UsuarioDtoResponse buscarUsuarioPorId(String id);
    Page<UsuarioPerfilDtoResponse> buscarUsuariosPorNombre(String nombre, Pageable pageable);
    UsuarioDtoResponse perfilUsuario();
    Page<UsuarioPerfilDtoResponse> listarAmigosUsuarioPorId(String id);
}
