package com.stefano.application.services;


import com.stefano.web.dto.usuario.UsuarioPerfilDtoResponse;
import com.stefano.web.dto.usuario.UsuarioDtoResponse;
import org.springframework.data.domain.Page;

public interface UsuarioService {
    UsuarioDtoResponse buscarUsuarioPorId(String id);
    UsuarioDtoResponse perfilUsuario();
    Page<UsuarioPerfilDtoResponse> listarAmigosUsuarioPorId(String id);
}
