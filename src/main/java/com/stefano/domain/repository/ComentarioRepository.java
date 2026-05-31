package com.stefano.domain.repository;

import com.stefano.domain.models.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComentarioRepository extends MongoRepository<Comentario, String> {
    Page<Comentario> findAllByPublicacionId(String publicacionId, Pageable pageable);
}
