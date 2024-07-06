package com.practica.api_libros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<DatosAutor> autorList,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") Long descargas
) {
}
