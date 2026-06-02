package com.stefano.web.controller;

import com.stefano.application.services.UsuarioService;
import com.stefano.web.dto.usuario.UsuarioPerfilDtoResponse;
import com.stefano.web.dto.usuario.UsuarioDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    @GetMapping("/me")
    public ResponseEntity<UsuarioDtoResponse> perfilUsuario(){
        return ResponseEntity.ok().body(usuarioService.perfilUsuario());
    }

    @GetMapping("/{idUsuario}/friends")
    public ResponseEntity<Page<UsuarioPerfilDtoResponse>> listaAmigosUsuarioPorId(
            @PathVariable String idUsuario
    ){
        return ResponseEntity.ok().body(usuarioService.listarAmigosUsuarioPorId(idUsuario));

    }
}
