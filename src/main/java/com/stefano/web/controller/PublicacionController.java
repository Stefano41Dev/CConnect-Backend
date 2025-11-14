package com.stefano.web.controller;

import com.stefano.application.services.PublicacionService;
import com.stefano.web.dto.publicacion.PublicacionDtoRequest;
import com.stefano.web.dto.publicacion.PublicacionDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PublicacionController {
    private final PublicacionService publicacionService;

    @PostMapping(value = "/generar-publicacion", consumes = {"multipart/form-data"})
    public ResponseEntity<PublicacionDtoResponse> crearPublicacion(
            @RequestPart PublicacionDtoRequest publicacionDtoRequest,
            @RequestPart List<MultipartFile> multipartFile

    ){
        PublicacionDtoResponse dtoResponse = publicacionService.crearPublicacion(publicacionDtoRequest, multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    @GetMapping(value = "/listar-publicaciones")
    public ResponseEntity<Page<PublicacionDtoResponse>> listarPublicaciones(
            @PageableDefault(size = 10, sort = "fechaPublicacion") Pageable pageable
    ){
        Page<PublicacionDtoResponse> publicaciones = publicacionService.listarPublicaciones(pageable);
        return ResponseEntity.ok().body(publicaciones);
    }
    @GetMapping(value = "/listar-publicaciones/{username}")
    public ResponseEntity<Page<PublicacionDtoResponse>> listarPublicacionesUsuario(
            @PathVariable String username,
            @PageableDefault Pageable pageable
    ){
        Page<PublicacionDtoResponse> publicaciones = publicacionService.listarPublicacionesUser(username, pageable);
        return ResponseEntity.ok().body(publicaciones);
    }
}
