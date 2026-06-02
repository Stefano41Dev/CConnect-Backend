package com.stefano.application.services;


import com.stefano.web.dto.usuario.UsuarioDtoResponse;

public interface UsuarioService {
    UsuarioDtoResponse buscarUsuarioPorId(String id);
}
