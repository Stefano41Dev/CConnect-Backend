package com.stefano.web.controller;

import com.stefano.application.services.UsuarioService;
import com.stefano.web.dto.usuario.UsuarioDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> buscarUsuarioPorId(
            @PathVariable String id
    ){
        return ResponseEntity.ok().body(usuarioService.buscarUsuarioPorId(id));
    }
}
