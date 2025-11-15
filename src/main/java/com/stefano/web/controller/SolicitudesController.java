package com.stefano.web.controller;

import com.stefano.application.services.SolicitudAmistadService;
import com.stefano.web.dto.solicitud.CambiarEstadoSolicitudDtoRequest;
import com.stefano.web.dto.solicitud.EstadoSolicitudDtoRequest;
import com.stefano.web.dto.solicitud.MandarSolicitudAmistadDtoRequest;
import com.stefano.web.dto.solicitud.SolicitudAmistadDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitudes")
@RequiredArgsConstructor
public class SolicitudesController {
    private final SolicitudAmistadService solicitudAmistadService;
    @PostMapping("/mandar-solicitud")
    public ResponseEntity<SolicitudAmistadDtoResponse> mandarSolicitudAmistad(
            @RequestBody MandarSolicitudAmistadDtoRequest request
    ){
       SolicitudAmistadDtoResponse dtoResponse =  solicitudAmistadService.mandarSolicitudAmistad(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    @PostMapping("/aceptar-amistad")
    public ResponseEntity<?> aceptarSolicitudAmistad (
            @RequestBody CambiarEstadoSolicitudDtoRequest request
    ){
      solicitudAmistadService.aceptarSolicitudAmistad(request);
       return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @PostMapping("/rechazar-amistad")
    public ResponseEntity<?> rechazarSolicitudAmistad (
            @RequestBody CambiarEstadoSolicitudDtoRequest request
    ){
        solicitudAmistadService.rechazarSolicitudAmistad(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @PostMapping("/cancelar-amistad")
    public ResponseEntity<?> cancelarSolicitudAmistad (
            @RequestBody CambiarEstadoSolicitudDtoRequest request
    ){
        solicitudAmistadService.cancelarSolicitudAmistad(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @GetMapping("/solicitudes-recibidas/{userId}")
    public ResponseEntity<Page<SolicitudAmistadDtoResponse>> solicitudesRecibidas(
            @PathVariable String userId,
            @PageableDefault Pageable pageable,
            @RequestBody EstadoSolicitudDtoRequest estado
            ){
        Page<SolicitudAmistadDtoResponse> solicitudes = solicitudAmistadService.listaSolicitudesAmistadPendientesRecibidas(userId, pageable, estado.estadoSolicitud());
        return ResponseEntity.status(HttpStatus.OK).body(solicitudes);
    }
    @GetMapping("/solicitudes-emitidas/{userId}")
    public ResponseEntity<Page<SolicitudAmistadDtoResponse>> solicitudesEmitidas(
            @PathVariable String userId,
            @PageableDefault Pageable pageable,
            @RequestBody EstadoSolicitudDtoRequest estado
    ){
        Page<SolicitudAmistadDtoResponse> solicitudes = solicitudAmistadService.listaSolicitudesAmistadPendientesEmitidas(userId, pageable, estado.estadoSolicitud());
        return ResponseEntity.status(HttpStatus.OK).body(solicitudes);
    }
}
