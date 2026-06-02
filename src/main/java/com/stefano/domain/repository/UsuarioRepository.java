package com.stefano.domain.repository;

import com.stefano.domain.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
    Page<Usuario> findByUsernameContainingIgnoreCase(String username,Pageable pageable);
}
