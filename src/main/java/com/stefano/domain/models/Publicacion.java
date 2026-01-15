package com.stefano.domain.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.BindParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "publicacion")
@Data
@Builder
public class Publicacion {
    @Id
    private String id;
    private String userid;
    private String username;
    private String contenido;
    private LocalDateTime fechaPublicacion;
    private Long totalComentarios;
    @Builder.Default
    private List<String> imagenesUrl = new ArrayList<>();
}
