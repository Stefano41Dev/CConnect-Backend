package com.stefano.application.services;

import com.stefano.domain.models.SolicitudAmistad;
import com.stefano.web.dto.solicitud.CambiarEstadoSolicitudDtoRequest;
import com.stefano.web.dto.solicitud.MandarSolicitudAmistadDtoRequest;
import com.stefano.web.dto.solicitud.SolicitudAmistadDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SolicitudAmistadService {
    SolicitudAmistadDtoResponse mandarSolicitudAmistad(MandarSolicitudAmistadDtoRequest request);
    void aceptarSolicitudAmistad(CambiarEstadoSolicitudDtoRequest request);
    void cancelarSolicitudAmistad(CambiarEstadoSolicitudDtoRequest request);
    void rechazarSolicitudAmistad(CambiarEstadoSolicitudDtoRequest request);
    Page<SolicitudAmistadDtoResponse> listaSolicitudesAmistadPendientesRecibidas(String userId,Pageable pageable, String estado);
    Page<SolicitudAmistadDtoResponse> listaSolicitudesAmistadPendientesEmitidas(String userId,Pageable pageable, String estado);
}
