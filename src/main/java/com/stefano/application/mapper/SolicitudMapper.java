package com.stefano.application.mapper;

import com.stefano.domain.models.SolicitudAmistad;
import com.stefano.domain.models.enums.EstadoSolicitudAmistad;
import com.stefano.web.dto.solicitud.MandarSolicitudAmistadDtoRequest;
import com.stefano.web.dto.solicitud.SolicitudAmistadDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class SolicitudMapper {

    public SolicitudAmistad toEntity (MandarSolicitudAmistadDtoRequest request){
        return SolicitudAmistad.builder()
                .receptorId(request.receptorId())
                .estado(EstadoSolicitudAmistad.PENDIENTE)
                .build();
    }

    public SolicitudAmistadDtoResponse toDto (SolicitudAmistad solicitudAmistad){
        return SolicitudAmistadDtoResponse.builder()
                .id(solicitudAmistad.getId())
                .emisorId(solicitudAmistad.getEmisorId())
                .receptorId(solicitudAmistad.getReceptorId())
                .estado(solicitudAmistad.getEstado().toString())
                .fechaEnvio(solicitudAmistad.getFechaEnvio())
                .build();
    }
}
