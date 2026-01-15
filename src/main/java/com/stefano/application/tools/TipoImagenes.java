package com.stefano.application.tools;

import com.stefano.application.exception.ErrorNegocio;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Component
public class TipoImagenes {
    private final List<String> IMAGE_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/jpg",
            "image/webp"
    );

    public void validarImagen(MultipartFile file) {
        if (!IMAGE_TYPES.contains(file.getContentType())) {
            throw new ErrorNegocio("Solo se permiten imágenes", HttpStatus.CONFLICT);
        }
    }
}
