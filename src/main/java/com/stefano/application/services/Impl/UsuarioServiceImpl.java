package com.stefano.application.services.Impl;


import com.stefano.application.exception.ErrorNegocio;
import com.stefano.application.mapper.UsuarioMapper;
import com.stefano.application.services.UsuarioService;
import com.stefano.domain.models.Usuario;
import com.stefano.domain.repository.UsuarioRepository;
import com.stefano.web.dto.usuario.UsuarioDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository userRepository;
    private final UsuarioMapper userMapper;

    @Override
    public UsuarioDtoResponse buscarUsuarioPorId(String id) {
        Usuario user = userRepository.findById(id).orElseThrow(()-> new ErrorNegocio("No se encontro el usuario id: " + id, HttpStatus.NOT_FOUND));
        return userMapper.toDto(user);
    }
}
