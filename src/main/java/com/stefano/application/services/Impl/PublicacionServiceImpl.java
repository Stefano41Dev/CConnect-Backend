package com.stefano.application.services.Impl;

import com.stefano.application.exception.ErrorNegocio;
import com.stefano.application.mapper.PublicacionMapper;
import com.stefano.application.services.CloudinaryService;
import com.stefano.application.services.PublicacionService;
import com.stefano.domain.models.Publicacion;
import com.stefano.domain.models.Usuario;
import com.stefano.domain.repository.PublicacionRepository;
import com.stefano.domain.repository.UsuarioRepository;
import com.stefano.web.dto.publicacion.PublicacionDtoRequest;
import com.stefano.web.dto.publicacion.PublicacionDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicacionServiceImpl implements PublicacionService {
    private final PublicacionMapper publicacionMapper;
    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;

    private final CloudinaryService cloudinaryService;
    @Override
    public PublicacionDtoResponse crearPublicacion(PublicacionDtoRequest publicacionDtoRequest, List<MultipartFile> imagenes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ErrorNegocio("Usuario no encontrado", HttpStatus.NOT_FOUND));
        Publicacion publicacion = publicacionMapper.toEntity(publicacionDtoRequest);

        publicacion.setUserid(usuario.getId());
        publicacion.setUsername(username);
        publicacion.setFechaPublicacion(LocalDateTime.now());
        publicacion.setComentarios(List.of());

        if(!imagenes.isEmpty()) {
            for (MultipartFile imagen : imagenes) {
                try {
                    String imagenUrl = cloudinaryService.uploadImage(imagen);
                    publicacion.getImagenesUrl().add(imagenUrl);
                } catch (IOException e) {
                    throw new ErrorNegocio(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            }
        }

        publicacion = publicacionRepository.save(publicacion);

        return publicacionMapper.toDto(publicacion);
    }

    @Override
    public Page<PublicacionDtoResponse> listarPublicacionesUser(String username, Pageable pageable) {
        Page<Publicacion> publicaciones = publicacionRepository.findAllByUsername(username, pageable);
        return publicaciones.map(publicacionMapper::toDto);
    }

    @Override
    public Page<PublicacionDtoResponse> listarPublicaciones(Pageable pageable) {
        Page<Publicacion> publicaciones = publicacionRepository.findAll(pageable);
        return  publicaciones.map(publicacionMapper::toDto);
    }

}
