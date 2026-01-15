package com.stefano.web.controller;

import com.stefano.application.services.Impl.ComentarioServiceImpl;
import com.stefano.web.dto.comentario.ComentarioDtoRequest;
import com.stefano.web.dto.comentario.ComentarioDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class ComentarioController {

    private final ComentarioServiceImpl comentarioService;

    @PostMapping("/agregar")
    public ResponseEntity<ComentarioDtoResponse> agregarComentario(
            @RequestBody ComentarioDtoRequest comentarioDtoRequest
    ){
        ComentarioDtoResponse response = comentarioService.agregarComentario(comentarioDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
