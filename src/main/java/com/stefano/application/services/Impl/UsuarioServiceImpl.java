package com.stefano.application.services.Impl;


import com.stefano.application.exception.ErrorNegocio;
import com.stefano.application.mapper.UsuarioMapper;
import com.stefano.application.services.UsuarioService;
import com.stefano.domain.models.Usuario;
import com.stefano.domain.repository.UsuarioRepository;
import com.stefano.web.dto.usuario.UsuarioPerfilDtoResponse;
import com.stefano.web.dto.usuario.UsuarioDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository userRepository;
    private final UsuarioMapper userMapper;

    @Override
    public UsuarioDtoResponse buscarUsuarioPorId(String id) {
        Usuario usuario = userRepository.findById(id).orElseThrow(()-> new ErrorNegocio("No se encontro el usuario id: " + id, HttpStatus.NOT_FOUND));
        return userMapper.toDto(usuario);
    }

    @Override
    public Page<UsuarioPerfilDtoResponse> buscarUsuariosPorNombre(String nombre, Pageable pageable) {
        Page<Usuario> usuario = userRepository.findByUsernameContainingIgnoreCase(nombre,pageable);
        return usuario.map(user ->
            new UsuarioPerfilDtoResponse(user.getId(),user.getUsername())
        );
    }

    @Override
    public UsuarioDtoResponse perfilUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new ErrorNegocio("Usuario no encontrado", HttpStatus.NOT_FOUND));
        return userMapper.toDto(usuario);
    }

    @Override
    public Page<UsuarioPerfilDtoResponse> listarAmigosUsuarioPorId(String id) {
        Usuario usuario = userRepository.findById(id).orElseThrow(()-> new ErrorNegocio("No se encontro el usuario id: " + id, HttpStatus.NOT_FOUND));

        List<Usuario> amigos = userRepository.findAllById(usuario.getAmigosIds());

        List<UsuarioPerfilDtoResponse> amigosDto = amigos.stream()
                .map(amigo -> new UsuarioPerfilDtoResponse(
                        amigo.getId(),
                        amigo.getUsername()
                ))
                .toList();

        return new PageImpl<>(amigosDto);
    }
}
