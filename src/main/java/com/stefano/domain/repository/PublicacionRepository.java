package com.stefano.domain.repository;

import com.stefano.domain.models.Publicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PublicacionRepository extends MongoRepository<Publicacion, String> {

    Page<Publicacion> findAll(Pageable pageable);
    Page<Publicacion> findAllByUsername(String username, Pageable pageable);
}
