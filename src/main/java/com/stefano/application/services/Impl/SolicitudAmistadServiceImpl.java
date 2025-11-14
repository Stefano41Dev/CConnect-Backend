package com.stefano.application.services.Impl;

import com.stefano.application.exception.ErrorNegocio;
import com.stefano.application.mapper.SolicitudMapper;
import com.stefano.application.services.SolicitudAmistadService;
import com.stefano.domain.models.SolicitudAmistad;
import com.stefano.domain.models.Usuario;
import com.stefano.domain.models.enums.EstadoSolicitudAmistad;
import com.stefano.domain.repository.SolicitudAmistadRepository;
import com.stefano.domain.repository.UsuarioRepository;
import com.stefano.web.dto.solicitud.CambiarEstadoSolicitudDtoRequest;
import com.stefano.web.dto.solicitud.MandarSolicitudAmistadDtoRequest;
import com.stefano.web.dto.solicitud.SolicitudAmistadDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SolicitudAmistadServiceImpl implements SolicitudAmistadService {
    private final UsuarioRepository usuarioRepository;
    private final SolicitudAmistadRepository solicitudAmistadRepository;
    private final SolicitudMapper solicitudMapper;
    @Override
    @Transactional
    public SolicitudAmistadDtoResponse mandarSolicitudAmistad(MandarSolicitudAmistadDtoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SolicitudAmistad solicitudAmistad = solicitudMapper.mandarSolicitudToEntity(request);
        if(authentication!=null && authentication.getPrincipal() instanceof Usuario usuarioActual){
            solicitudAmistad.setEmisorId(usuarioActual.getId());

        }else{
            throw new ErrorNegocio("Usuario no autenticado", HttpStatus.BAD_REQUEST);
        }
        solicitudAmistad.setFechaEnvio(LocalDateTime.now());
        solicitudAmistad = solicitudAmistadRepository.save(solicitudAmistad);
        return solicitudMapper.toDto(solicitudAmistad);
    }

    @Override
    public void cambiarEstadoSolicitudAmistad(CambiarEstadoSolicitudDtoRequest request) {
        SolicitudAmistad solicitudAmistadBuscada = solicitudAmistadRepository.findById(request.idSolicitudAmistad())
                .orElseThrow(()->new ErrorNegocio("No se encontro la solicitud", HttpStatus.NOT_FOUND));

        EstadoSolicitudAmistad nuevoEstado;
        try{
            nuevoEstado =  EstadoSolicitudAmistad.valueOf(request.estadoSolicitud().toUpperCase());
        }catch(ErrorNegocio ex){
            throw new ErrorNegocio("El estado enviado no coincide con los estados de la solicitud", HttpStatus.CONFLICT);
        }


        if(nuevoEstado.equals(EstadoSolicitudAmistad.ACEPTADO)){
            if(solicitudAmistadBuscada.getEstado().equals(EstadoSolicitudAmistad.PENDIENTE)){
                Usuario usuarioEmisor = usuarioRepository.findById(solicitudAmistadBuscada.getEmisorId())
                        .orElseThrow(()-> new ErrorNegocio("No se encontro el usuario", HttpStatus.NOT_FOUND));
                Usuario usuarioReceptor = usuarioRepository.findById(solicitudAmistadBuscada.getReceptorId())
                        .orElseThrow(()-> new ErrorNegocio("No se encontro el usuario", HttpStatus.NOT_FOUND));
                usuarioReceptor.getAmigosIds().add(usuarioEmisor.getId());
                usuarioReceptor.getAmigosIds().add(usuarioReceptor.getId());
            }else{
                throw new ErrorNegocio("Solo se puede aceptar solicitudes con el estado pendiente", HttpStatus.CONFLICT);
            }
        }

        solicitudAmistadRepository.deleteById(solicitudAmistadBuscada.getId());

    }
}
