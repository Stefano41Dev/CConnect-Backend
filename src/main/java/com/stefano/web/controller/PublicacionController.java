package com.stefano.web.controller;

import com.stefano.application.services.PublicacionService;
import com.stefano.web.dto.publicacion.PublicacionDtoRequest;
import com.stefano.web.dto.publicacion.PublicacionDtoResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PublicacionController {
    private final PublicacionService publicacionService;

    @PostMapping(
            value = "/generar-publicacion",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<PublicacionDtoResponse> crearPublicacion(
            @RequestPart("publicacionDtoRequest")
            @Parameter(
                    description = "Datos de la publicación (JSON)",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PublicacionDtoRequest.class)
                    )
            )
            PublicacionDtoRequest publicacionDtoRequest,
            @RequestPart(value = "multipartFile",required = false)
            @Parameter(description = "Imagen a subir (Opcional)")
            List<MultipartFile> multipartFile
    ) {

        PublicacionDtoResponse dtoResponse = publicacionService.crearPublicacion(publicacionDtoRequest, multipartFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }


    @GetMapping(value = "/listar-publicaciones")
    public ResponseEntity<Page<PublicacionDtoResponse>> listarPublicaciones(
            @ParameterObject
            @PageableDefault(page = 0, size = 10, sort = "fechaPublicacion") Pageable pageable
    ){
        Page<PublicacionDtoResponse> publicaciones = publicacionService.listarPublicaciones(pageable);
        return ResponseEntity.ok().body(publicaciones);
    }
    @GetMapping(value = "/listar-publicaciones/{username}")
    public ResponseEntity<Page<PublicacionDtoResponse>> listarPublicacionesUsuario(
            @PathVariable String username,
            @ParameterObject
            @PageableDefault(page = 0, size = 10, sort = "fechaPublicacion") Pageable pageable
    ){
        Page<PublicacionDtoResponse> publicaciones = publicacionService.listarPublicacionesUser(username, pageable);
        return ResponseEntity.ok().body(publicaciones);
    }
}
