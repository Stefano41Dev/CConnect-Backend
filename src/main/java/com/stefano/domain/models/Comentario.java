package com.stefano.domain.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "comentarios")
@Data
@Builder
public class Comentario {
    @Id
    private String id;
    private String publicacionId;
    private String userId;
    private String username;
    private String contenido;
    private LocalDateTime fechaPublicacion;
}
