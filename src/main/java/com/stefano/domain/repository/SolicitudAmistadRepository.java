package com.stefano.domain.repository;

import com.stefano.domain.models.SolicitudAmistad;
import com.stefano.domain.models.enums.EstadoSolicitudAmistad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SolicitudAmistadRepository extends MongoRepository<SolicitudAmistad, String> {
    Page<SolicitudAmistad> findAllByReceptorIdAndEstado(String receptorId,Pageable pageable, EstadoSolicitudAmistad estado);
    Page<SolicitudAmistad> findAllByEmisorIdAndEstado(String emisorId,Pageable pageable, EstadoSolicitudAmistad estado);
    Boolean existsByEmisorIdAndReceptorIdAndEstado(String emisorId, String receptorId, EstadoSolicitudAmistad estado);
}
