package com.stefano.application.services;

import com.stefano.web.dto.publicacion.PublicacionDtoRequest;
import com.stefano.web.dto.publicacion.PublicacionDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PublicacionService {
    PublicacionDtoResponse crearPublicacion(PublicacionDtoRequest publicacionDtoRequest, List<MultipartFile> imagenes, Authentication authentications);
    Page<PublicacionDtoResponse> listarPublicacionesUser(String username, Pageable pageable);
    Page<PublicacionDtoResponse> listarPublicaciones(Pageable pageable);

}
