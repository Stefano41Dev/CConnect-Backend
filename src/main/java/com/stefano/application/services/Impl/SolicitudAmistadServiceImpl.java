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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SolicitudAmistadServiceImpl implements SolicitudAmistadService {
    private final UsuarioRepository usuarioRepository;
    private final SolicitudAmistadRepository solicitudAmistadRepository;
    private final SolicitudMapper solicitudMapper;

    @Override
    @Transactional
    public SolicitudAmistadDtoResponse mandarSolicitudAmistad(MandarSolicitudAmistadDtoRequest request) {

        SolicitudAmistad solicitudAmistad = solicitudMapper.toEntity(request);
        Usuario usuarioActual = getAuthentication();

        if (Objects.equals(usuarioActual.getId(), solicitudAmistad.getReceptorId())) {
            throw new ErrorNegocio("No puedes enviarte una solicitud a ti mismo", HttpStatus.BAD_REQUEST);
        }

        boolean existeSolicitud = solicitudAmistadRepository
                .existsByEmisorIdAndReceptorIdAndEstado(
                        usuarioActual.getId(),
                        solicitudAmistad.getReceptorId(),
                        EstadoSolicitudAmistad.PENDIENTE
                );

        if (existeSolicitud) {
            throw new ErrorNegocio("Ya existe una solicitud pendiente", HttpStatus.CONFLICT);
        }
        solicitudAmistad.setEmisorId(usuarioActual.getId());
        solicitudAmistad.setFechaEnvio(LocalDateTime.now());
        solicitudAmistad = solicitudAmistadRepository.save(solicitudAmistad);

        return solicitudMapper.toDto(solicitudAmistad);
    }

    @Override
    @Transactional
    public void aceptarSolicitudAmistad(CambiarEstadoSolicitudDtoRequest request) {

        Usuario usuarioActual = getAuthentication();

        SolicitudAmistad solicitudAmistad = solicitudAmistadRepository.findById(request.idSolicitudAmistad())
                .orElseThrow(() -> new ErrorNegocio("Solicitud de amistad no encontrada", HttpStatus.NOT_FOUND));

        if (!Objects.equals(usuarioActual.getId(), solicitudAmistad.getReceptorId())) {
            throw new ErrorNegocio("No tienes permiso para aceptar esta solicitud", HttpStatus.FORBIDDEN);
        }
        if (!EstadoSolicitudAmistad.PENDIENTE.equals(solicitudAmistad.getEstado())) {
            throw new ErrorNegocio("Solo se pueden aceptar solicitudes pendientes", HttpStatus.CONFLICT);
        }
        if (usuarioActual.getAmigosIds().contains(solicitudAmistad.getEmisorId())) {
            throw new ErrorNegocio("Ya son amigos", HttpStatus.CONFLICT);
        }

        Usuario usuarioEmisor = usuarioRepository.findById(solicitudAmistad.getEmisorId())
                .orElseThrow(() -> new ErrorNegocio("Usuario emisor no encontrado", HttpStatus.NOT_FOUND));
        Usuario usuarioReceptor = usuarioRepository.findById(solicitudAmistad.getReceptorId())
                .orElseThrow(() -> new ErrorNegocio("Usuario receptor no encontrado", HttpStatus.NOT_FOUND));

        usuarioReceptor.getAmigosIds().add(usuarioEmisor.getId());
        usuarioEmisor.getAmigosIds().add(usuarioReceptor.getId());

        solicitudAmistad.setEstado(EstadoSolicitudAmistad.ACEPTADO);
        solicitudAmistadRepository.save(solicitudAmistad);
        usuarioRepository.save(usuarioReceptor);
        usuarioRepository.save(usuarioEmisor);
    }

    @Override
    @Transactional
    public void cancelarSolicitudAmistad(CambiarEstadoSolicitudDtoRequest request) {
        Usuario usuarioActual = getAuthentication();

        SolicitudAmistad solicitudAmistad = solicitudAmistadRepository.findById(request.idSolicitudAmistad())
                .orElseThrow(()-> new ErrorNegocio("La solicitud de amistad no encontrada", HttpStatus.NOT_FOUND));

        if (!Objects.equals(usuarioActual.getId(), solicitudAmistad.getEmisorId())) {
            throw new ErrorNegocio("No tienes permiso para cancelar esta solicitud", HttpStatus.FORBIDDEN);
        }

        if (!EstadoSolicitudAmistad.PENDIENTE.equals(solicitudAmistad.getEstado())) {
            throw new ErrorNegocio("Solo se pueden cancelar solicitudes pendientes", HttpStatus.CONFLICT);
        }

        solicitudAmistadRepository.deleteById(solicitudAmistad.getId());
    }

    @Override
    @Transactional
    public void rechazarSolicitudAmistad(CambiarEstadoSolicitudDtoRequest request) {
        Usuario usuarioActual = getAuthentication();

        SolicitudAmistad solicitudAmistad = solicitudAmistadRepository.findById(request.idSolicitudAmistad())
                .orElseThrow(()-> new ErrorNegocio("La solicitud de amistad no encontrada", HttpStatus.NOT_FOUND));

        if (!Objects.equals(usuarioActual.getId(), solicitudAmistad.getReceptorId())) {
            throw new ErrorNegocio("No tienes permiso para rechazar esta solicitud", HttpStatus.FORBIDDEN);
        }

        if (!EstadoSolicitudAmistad.PENDIENTE.equals(solicitudAmistad.getEstado())) {
            throw new ErrorNegocio("Solo se pueden rechazar solicitudes pendientes", HttpStatus.CONFLICT);
        }

        solicitudAmistad.setEstado(EstadoSolicitudAmistad.RECHAZADO);
        solicitudAmistadRepository.save(solicitudAmistad);
    }

    @Override
    public Page<SolicitudAmistadDtoResponse> listaSolicitudesAmistadPendientesRecibidas(String userId, Pageable pageable, String estado) {
        getAuthentication();
        EstadoSolicitudAmistad estadoEnum = convertirEstadoEnum(estado);
        Page<SolicitudAmistad> solicitudes = solicitudAmistadRepository.findAllByReceptorIdAndEstado(userId,pageable, estadoEnum);
        return solicitudes.map(solicitudMapper::toDto);
    }

    @Override
    public Page<SolicitudAmistadDtoResponse> listaSolicitudesAmistadPendientesEmitidas(String userId, Pageable pageable, String estado) {
        getAuthentication();
        EstadoSolicitudAmistad estadoEnum = convertirEstadoEnum(estado);
        Page<SolicitudAmistad> solicitudes = solicitudAmistadRepository.findAllByEmisorIdAndEstado(userId,pageable,estadoEnum);
        return solicitudes.map(solicitudMapper::toDto);
    }

    public EstadoSolicitudAmistad convertirEstadoEnum(String estado){
       EstadoSolicitudAmistad nuevoEstado;
        try{
             nuevoEstado = EstadoSolicitudAmistad.valueOf(estado);
        } catch (IllegalArgumentException e) {
            throw new ErrorNegocio("El estado de solicitud no coincide", HttpStatus.CONFLICT);
        }
        return nuevoEstado;
    }
    private Usuario getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !(authentication.getPrincipal() instanceof Usuario usuario)){
            throw new ErrorNegocio("Usuario no autenticado", HttpStatus.UNAUTHORIZED);
        }
        return usuario;
    }
}